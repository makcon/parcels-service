package test.maksim.parcels.ws.repository;

import test.maksim.parcels.dto.Shipment;

import java.util.Optional;

public interface ParcelsRepository {

    void register(Shipment shipment);

    Optional<Shipment> getByReference(String reference);
}
