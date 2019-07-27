package test.maksim.parcels.ws.handler;

import test.maksim.parcels.dto.Tracking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static test.maksim.parcels.dto.Tracking.Status.DELIVERED;

@Component
@RequiredArgsConstructor
public class TrackingStatusHandlerFactory {

    private final DeliveredTrackingStatusHandler deliveredHandler;
    private final OtherTrackingStatusHandler otherHandler;

    public TrackingStatusHandler getHandler(Tracking tracking) {
        return tracking.getStatus() == DELIVERED ? deliveredHandler : otherHandler;
    }
}
