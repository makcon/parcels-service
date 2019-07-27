package test.maksim.parcels.ws.domain;

import lombok.Data;

@Data
public class TrackingEvent {

    private final String reference;
    private final Status status;

    public enum Status {

        CONCILIATION_REQUEST,
        NOT_NEEDED,
        INCOMPLETE,
        NOT_FOUND
    }
}
