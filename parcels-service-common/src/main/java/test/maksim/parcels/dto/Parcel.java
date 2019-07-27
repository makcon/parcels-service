package test.maksim.parcels.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Parcel {

    private final int weight;
    private final int width;
    private final int height;
    private final int length;
}
