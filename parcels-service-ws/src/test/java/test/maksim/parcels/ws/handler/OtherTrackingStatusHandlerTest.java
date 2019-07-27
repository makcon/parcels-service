package test.maksim.parcels.ws.handler;

import test.maksim.parcels.ws.domain.TrackingEvent;
import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class OtherTrackingStatusHandlerTest {

    private final OtherTrackingStatusHandler handler = new OtherTrackingStatusHandler();

    @Test
    public void handle_alwaysReturnsINCOMPLETE() {
        Optional<TrackingEvent.Status> status = handler.handle(mock(Shipment.class), mock(Tracking.class));

        assertThat(status.isPresent(), is(true));
        assertThat(status.get(), equalTo(TrackingEvent.Status.INCOMPLETE));
    }
}