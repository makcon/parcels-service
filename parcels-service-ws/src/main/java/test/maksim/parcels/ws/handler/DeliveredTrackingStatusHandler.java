package test.maksim.parcels.ws.handler;

import test.maksim.parcels.dto.Parcel;
import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import test.maksim.parcels.ws.domain.TrackingEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeliveredTrackingStatusHandler implements TrackingStatusHandler {

    @Override
    public Optional<TrackingEvent.Status> handle(Shipment shipment,
                                                 Tracking tracking) {
        if (tracking.getParcels() == null || tracking.getWeight() == null) {
            return Optional.of(TrackingEvent.Status.INCOMPLETE);
        }
        if (shipment.getParcels().size() != tracking.getParcels()) {
            return Optional.empty();
        }

        var status = getTotalWeight(shipment) >= tracking.getWeight() ? TrackingEvent.Status.NOT_NEEDED : TrackingEvent.Status.CONCILIATION_REQUEST;

        return Optional.of(status);
    }

    private int getTotalWeight(Shipment shipment) {
        return shipment.getParcels().stream()
                .mapToInt(Parcel::getWeight)
                .sum();
    }
}
