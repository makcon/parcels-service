package test.maksim.parcels.ws.repository.impl;

import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.ws.repository.ParcelsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Slf4j
public class InMemoryParcelsRepository implements ParcelsRepository {

    private static final List<Shipment> SHIPMENTS_STORAGE = new ArrayList<>();

    @Override
    public void register(Shipment shipment) {
        log.debug("Adding shipment: {}", shipment);
        SHIPMENTS_STORAGE.add(shipment);
    }

    @Override
    public Optional<Shipment> getByReference(String reference) {
        return SHIPMENTS_STORAGE.stream()
                .filter(it -> Objects.equals(reference, it.getReference()))
                .findFirst();
    }
}
