package test.maksim.parcels.ws.listener;

import test.maksim.parcels.ws.domain.TrackingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TrackingEventListener {

    @EventListener
    public void listen(TrackingEvent event) {
        log.info("Received event: {}", event);
    }
}
