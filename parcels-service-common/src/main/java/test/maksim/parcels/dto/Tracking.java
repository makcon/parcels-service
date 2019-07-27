package test.maksim.parcels.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tracking {

    private final Status status;
    private final Integer parcels;
    private final Integer weight;
    private final String reference;

    public enum Status {

        WAITING_IN_HUB, DELIVERED
    }
}
