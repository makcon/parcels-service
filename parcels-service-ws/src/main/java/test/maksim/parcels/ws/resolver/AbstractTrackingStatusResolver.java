package test.maksim.parcels.ws.resolver;

import test.maksim.parcels.dto.Parcel;
import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import test.maksim.parcels.ws.domain.TrackingEvent;

import java.util.Optional;

public abstract class AbstractTrackingStatusResolver implements TrackingStatusResolver {

    @Override
    public Optional<TrackingEvent.Status> resolve(Shipment shipment,
                                                  Tracking tracking) {
        return matches(shipment, tracking) ? Optional.of(getStatus()) : Optional.empty();
    }

    protected abstract boolean matches(Shipment shipment,
                                       Tracking tracking);

    protected abstract TrackingEvent.Status getStatus();

    protected boolean isParcelsSizeNotEqual(Shipment shipment,
                                            Tracking tracking) {
        return tracking.getParcels() != null &&
                shipment.getParcels().size() != tracking.getParcels();

    }

    protected int getTotalWeight(Shipment shipment) {
        return (int) shipment.getParcels().stream()
                .map(Parcel::getWeight)
                .count();
    }
}
