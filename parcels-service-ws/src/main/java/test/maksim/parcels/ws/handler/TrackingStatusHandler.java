package test.maksim.parcels.ws.handler;

import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import test.maksim.parcels.ws.domain.TrackingEvent;

import java.util.Optional;

@FunctionalInterface
public interface TrackingStatusHandler {

    Optional<TrackingEvent.Status> handle(Shipment shipment,
                                          Tracking tracking);
}
