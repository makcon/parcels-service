package test.maksim.parcels.ws.handler;

import test.maksim.parcels.dto.Parcel;
import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import org.junit.Test;
import test.maksim.parcels.ws.domain.TrackingEvent;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DeliveredTrackingStatusHandlerTest {

    private final DeliveredTrackingStatusHandler handler = new DeliveredTrackingStatusHandler();

    @Test
    public void handle_parcelsNull_shouldReturnINCOMPLETE() {
        var tracking = Tracking.builder()
                .parcels(null)
                .weight(1)
                .build();

        Optional<TrackingEvent.Status> status = handler.handle(createShipment(List.of()), tracking);

        assertThat(status.isPresent(), is(true));
        assertThat(status.get(), equalTo(TrackingEvent.Status.INCOMPLETE));
    }

    @Test
    public void handle_weightNull_shouldReturnINCOMPLETE() {
        var tracking = Tracking.builder()
                .parcels(1)
                .weight(null)
                .build();

        Optional<TrackingEvent.Status> status = handler.handle(createShipment(List.of()), tracking);

        assertThat(status.isPresent(), is(true));
        assertThat(status.get(), equalTo(TrackingEvent.Status.INCOMPLETE));
    }

    @Test
    public void handle_parcelNumberNotEqualTrackingParcelNumber_shouldReturnINCOMPLETE() {
        var tracking = Tracking.builder()
                .parcels(2)
                .weight(2)
                .build();

        Optional<TrackingEvent.Status> status = handler.handle(createShipment(List.of(createParcel(2))), tracking);

        assertThat(status.isPresent(), is(false));
    }

    @Test
    public void handle_totalWeightMoreTracking_shouldReturnNOT_NEEDED() {
        var tracking = Tracking.builder()
                .parcels(1)
                .weight(2)
                .build();

        Optional<TrackingEvent.Status> status = handler.handle(createShipment(List.of(createParcel(3))), tracking);

        assertThat(status.isPresent(), is(true));
        assertThat(status.get(), equalTo(TrackingEvent.Status.NOT_NEEDED));
    }

    @Test
    public void handle_totalWeightEqualsTracking_shouldReturnNOT_NEEDED() {
        var tracking = Tracking.builder()
                .parcels(1)
                .weight(2)
                .build();

        Optional<TrackingEvent.Status> status = handler.handle(createShipment(List.of(createParcel(2))), tracking);

        assertThat(status.isPresent(), is(true));
        assertThat(status.get(), equalTo(TrackingEvent.Status.NOT_NEEDED));
    }

    @Test
    public void handle_totalWeightLessTracking_shouldReturnNOT_NEEDED() {
        var tracking = Tracking.builder()
                .parcels(1)
                .weight(2)
                .build();

        Optional<TrackingEvent.Status> status = handler.handle(createShipment(List.of(createParcel(1))), tracking);

        assertThat(status.isPresent(), is(true));
        assertThat(status.get(), equalTo(TrackingEvent.Status.CONCILIATION_REQUEST));
    }

    // Util methods

    private Shipment createShipment(List<Parcel> parcels) {
        return new Shipment("ref", parcels);
    }

    private Parcel createParcel(int weight) {
        return Parcel.builder()
                .weight(weight)
                .build();
    }
}