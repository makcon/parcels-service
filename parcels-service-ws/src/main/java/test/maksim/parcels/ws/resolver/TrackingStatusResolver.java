package test.maksim.parcels.ws.resolver;

import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import test.maksim.parcels.ws.domain.TrackingEvent;

import java.util.Optional;

@FunctionalInterface
public interface TrackingStatusResolver {

    Optional<TrackingEvent.Status> resolve(Shipment shipment,
                                           Tracking tracking);
}
