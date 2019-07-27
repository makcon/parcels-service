package test.maksim.parcels.ws.rest;

import io.swagger.annotations.Api;
import test.maksim.parcels.constants.Endpoints;
import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import test.maksim.parcels.ws.service.RegisterService;
import test.maksim.parcels.ws.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api
public class ParcelsController {

    /**
     * TODO: futures improvements:
     * - add objects validation (e.q. 'reference', 'status' fields must be present)
     * - add errors handling (e.q. repository is not responding)
     */

    private final RegisterService registerService;
    private final TrackingService trackingService;
    private final AsyncListenableTaskExecutor serviceExecutor;

    @PostMapping(Endpoints.REGISTER)
    public void register(@RequestBody Shipment shipment) {
        log.info("Received register request: {}", shipment);
        serviceExecutor.submitListenable(() -> registerService.register(shipment));
    }

    @PutMapping(Endpoints.PUSH)
    public void push(@RequestBody Tracking tracking) {
        log.info("Received tracking request: {}", tracking);
        serviceExecutor.submitListenable(() -> trackingService.push(tracking));
    }
}
