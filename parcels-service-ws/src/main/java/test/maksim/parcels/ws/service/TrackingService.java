package test.maksim.parcels.ws.service;

import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import test.maksim.parcels.ws.domain.TrackingEvent;
import test.maksim.parcels.ws.handler.TrackingStatusHandlerFactory;
import test.maksim.parcels.ws.repository.ParcelsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingService {

    private final ParcelsRepository repository;
    private final ApplicationEventPublisher eventPublisher;
    private final TrackingStatusHandlerFactory statusHandlerFactory;

    public void push(Tracking tracking) {
        repository.getByReference(tracking.getReference())
                .ifPresentOrElse(
                        it -> push(it, tracking),
                        () -> emitEvent(tracking, TrackingEvent.Status.NOT_FOUND)
                );
    }

    private void emitEvent(Tracking tracking,
                           TrackingEvent.Status status) {
        var response = new TrackingEvent(tracking.getReference(), status);
        log.debug("Emitting event: {}", response);
        eventPublisher.publishEvent(response);
    }

    private void push(Shipment shipment,
                      Tracking tracking) {
        log.info("Parcel is found, trying to get status");
        statusHandlerFactory.getHandler(tracking)
                .handle(shipment, tracking)
                .ifPresentOrElse(
                        it -> emitEvent(tracking, it),
                        () -> handleEmptyStatus(tracking)
                );
    }

    private void handleEmptyStatus(Tracking tracking) {
        log.warn("Status is not found for: {}", tracking);
    }
}
