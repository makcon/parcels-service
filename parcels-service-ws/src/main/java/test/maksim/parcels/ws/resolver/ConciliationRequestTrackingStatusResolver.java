package test.maksim.parcels.ws.resolver;

import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import test.maksim.parcels.ws.domain.TrackingEvent;
import org.springframework.stereotype.Component;

import static test.maksim.parcels.dto.Tracking.Status.DELIVERED;

@Component
public class ConciliationRequestTrackingStatusResolver extends AbstractTrackingStatusResolver {

    @Override
    protected boolean matches(Shipment shipment,
                              Tracking tracking) {
        if (tracking.getStatus() != DELIVERED) {
            return false;
        }
        if (isParcelsSizeNotEqual(shipment, tracking)) {
            return false;
        }

        return getTotalWeight(shipment) < tracking.getWeight();
    }

    @Override
    protected TrackingEvent.Status getStatus() {
        return TrackingEvent.Status.CONCILIATION_REQUEST;
    }
}
