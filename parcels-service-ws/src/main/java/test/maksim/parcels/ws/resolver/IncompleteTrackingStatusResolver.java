package test.maksim.parcels.ws.resolver;

import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import test.maksim.parcels.ws.domain.TrackingEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static test.maksim.parcels.dto.Tracking.Status.DELIVERED;

@Component
@Order(1)
public class IncompleteTrackingStatusResolver extends AbstractTrackingStatusResolver {

    @Override
    protected boolean matches(Shipment shipment,
                              Tracking tracking) {
        if (tracking.getStatus() != DELIVERED) {
            return true;
        }

        return tracking.getParcels() == null || tracking.getWeight() == null;
    }

    @Override
    protected TrackingEvent.Status getStatus() {
        return TrackingEvent.Status.INCOMPLETE;
    }
}
