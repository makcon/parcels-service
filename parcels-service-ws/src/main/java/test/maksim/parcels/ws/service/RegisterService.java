package test.maksim.parcels.ws.service;

import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.ws.repository.ParcelsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterService {

    private final ParcelsRepository repository;

    public void register(Shipment shipment) {
        log.debug("Saving new shipment: {}", shipment);
        repository.register(shipment);
        log.info("Shipment is saved successfully");
    }
}
