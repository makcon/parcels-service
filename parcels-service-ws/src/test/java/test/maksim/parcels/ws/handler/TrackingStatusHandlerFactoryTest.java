package test.maksim.parcels.ws.handler;

import test.maksim.parcels.dto.Tracking;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static test.maksim.parcels.dto.Tracking.Status.DELIVERED;
import static test.maksim.parcels.dto.Tracking.Status.WAITING_IN_HUB;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TrackingStatusHandlerFactoryTest {

    @InjectMocks
    private TrackingStatusHandlerFactory factory;

    @Mock
    private DeliveredTrackingStatusHandler deliveredHandler;
    @Mock
    private OtherTrackingStatusHandler otherHandler;

    @Test
    public void getHandler_statusDELIVERED_shouldReturnDeliveredHandler() {
        TrackingStatusHandler handler = factory.getHandler(createTracking(DELIVERED));

        assertThat(handler, equalTo(deliveredHandler));
    }

    @Test
    public void getHandler_statusWAITING_IN_HUB_shouldReturnOtherHandler() {
        TrackingStatusHandler handler = factory.getHandler(createTracking(WAITING_IN_HUB));

        assertThat(handler, equalTo(otherHandler));
    }

    // Util methods

    private Tracking createTracking(Tracking.Status status) {
        return Tracking.builder()
                .status(status)
                .build();
    }
}