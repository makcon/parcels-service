package test.maksim.parcels.ws.handler;

import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import test.maksim.parcels.ws.domain.TrackingEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OtherTrackingStatusHandler implements TrackingStatusHandler {

    @Override
    public Optional<TrackingEvent.Status> handle(Shipment shipment,
                                                 Tracking tracking) {
        return Optional.of(TrackingEvent.Status.INCOMPLETE);
    }
}
