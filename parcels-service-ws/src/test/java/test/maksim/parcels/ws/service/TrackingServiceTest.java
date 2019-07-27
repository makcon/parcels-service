package test.maksim.parcels.ws.service;

import test.maksim.parcels.dto.Parcel;
import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import test.maksim.parcels.ws.domain.TrackingEvent;
import test.maksim.parcels.ws.handler.DeliveredTrackingStatusHandler;
import test.maksim.parcels.ws.handler.OtherTrackingStatusHandler;
import test.maksim.parcels.ws.handler.TrackingStatusHandlerFactory;
import test.maksim.parcels.ws.repository.ParcelsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrackingServiceTest {

    private static final String REFERENCE = "reference";

    @InjectMocks
    private TrackingService service;

    @Mock
    private ParcelsRepository repository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Spy
    private DeliveredTrackingStatusHandler deliveredHandler;
    @Spy
    private OtherTrackingStatusHandler otherHandler;

    @Before
    public void setUp() {
        service = new TrackingService(
                repository,
                eventPublisher,
                new TrackingStatusHandlerFactory(deliveredHandler, otherHandler)
        );
    }

    @Test
    public void push_shipmentNotFound_shouldEmitNOT_FOUND() {
        mockRepository(null);
        var reference = "unknown";
        var tracking = Tracking.builder()
                .reference(reference)
                .build();

        service.push(tracking);

        verifyEventEmitted(reference, TrackingEvent.Status.NOT_FOUND);
    }

    @Test
    public void push_DELIVERED_parcelNumberNotEqualTrackingParcelNumber_shouldNeverEmitEvent() {
        mockRepository(createShipment(List.of(createParcel(1))));
        var tracking = Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(4)
                .status(Tracking.Status.DELIVERED)
                .build();

        service.push(tracking);

        verifyEventNeverEmitted();
    }

    @Test
    public void push_DELIVERED_parcelNumberEqualTrackingParcelNumber_totalWeightLess_shouldEmitCONCILIATION_REQUEST() {
        mockRepository(createShipment(List.of(createParcel(1), createParcel(2))));
        var tracking = Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(4)
                .status(Tracking.Status.DELIVERED)
                .build();

        service.push(tracking);

        verifyEventEmitted(TrackingEvent.Status.CONCILIATION_REQUEST);
    }

    @Test
    public void push_DELIVERED_parcelNumberEqualTrackingParcelNumber_totalWeightMore_shouldEmitNOT_NEEDED() {
        mockRepository(createShipment(List.of(createParcel(3), createParcel(2))));
        var tracking = Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(3)
                .status(Tracking.Status.DELIVERED)
                .build();

        service.push(tracking);

        verifyEventEmitted(TrackingEvent.Status.NOT_NEEDED);
    }

    @Test
    public void push_DELIVERED_parcelNumberEqualTrackingParcelNumber_totalWeightEqual_shouldEmitNOT_NEEDED() {
        mockRepository(createShipment(List.of(createParcel(2), createParcel(2))));
        var tracking = Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(3)
                .status(Tracking.Status.DELIVERED)
                .build();

        service.push(tracking);

        verifyEventEmitted(TrackingEvent.Status.NOT_NEEDED);
    }

    @Test
    public void push_WAITING_IN_HUB_shouldEmitINCOMPLETE() {
        mockRepository(createShipment(List.of(createParcel(2), createParcel(2))));
        var tracking = Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(3)
                .status(Tracking.Status.WAITING_IN_HUB)
                .build();

        service.push(tracking);

        verifyEventEmitted(TrackingEvent.Status.INCOMPLETE);
    }

    @Test
    public void push_WAITING_IN_HUB_parcelsNull_shouldEmitINCOMPLETE() {
        mockRepository(createShipment(List.of(createParcel(2), createParcel(2))));
        var tracking = Tracking.builder()
                .reference(REFERENCE)
                .parcels(null)
                .weight(3)
                .status(Tracking.Status.WAITING_IN_HUB)
                .build();

        service.push(tracking);

        verifyEventEmitted(TrackingEvent.Status.INCOMPLETE);
    }

    @Test
    public void push_WAITING_IN_HUB_weightNull_shouldEmitINCOMPLETE() {
        mockRepository(createShipment(List.of(createParcel(2), createParcel(2))));
        var tracking = Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(null)
                .status(Tracking.Status.WAITING_IN_HUB)
                .build();

        service.push(tracking);

        verifyEventEmitted(TrackingEvent.Status.INCOMPLETE);
    }

    @Test
    public void push_DELIVERED_parcelsNull_shouldEmitINCOMPLETE() {
        mockRepository(createShipment(List.of(createParcel(2), createParcel(2))));
        var tracking = Tracking.builder()
                .reference(REFERENCE)
                .parcels(null)
                .weight(3)
                .status(Tracking.Status.DELIVERED)
                .build();

        service.push(tracking);

        verifyEventEmitted(TrackingEvent.Status.INCOMPLETE);
    }

    @Test
    public void push_DELIVERED_weightNull_shouldEmitINCOMPLETE() {
        mockRepository(createShipment(List.of(createParcel(2), createParcel(2))));
        var tracking = Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(null)
                .status(Tracking.Status.DELIVERED)
                .build();

        service.push(tracking);

        verifyEventEmitted(TrackingEvent.Status.INCOMPLETE);
    }

    // Util methods

    private Shipment createShipment(List<Parcel> parcels) {
        return new Shipment(REFERENCE, parcels);
    }

    private Parcel createParcel(int weight) {
        return Parcel.builder()
                .weight(weight)
                .build();
    }

    private void mockRepository(Shipment shipment) {
        when(repository.getByReference(anyString())).thenReturn(Optional.ofNullable(shipment));
    }

    private void verifyEventEmitted(TrackingEvent.Status status) {
        verifyEventEmitted(REFERENCE, status);
    }

    private void verifyEventEmitted(String reference,
                                    TrackingEvent.Status status) {
        verify(eventPublisher).publishEvent(new TrackingEvent(reference, status));
    }

    private void verifyEventNeverEmitted() {
        verify(eventPublisher, never()).publishEvent(any());
    }
}