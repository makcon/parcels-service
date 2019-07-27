package test.maksim.parcels.dto;

import lombok.Data;

import java.util.List;

@Data
public class Shipment {

    private final String reference;
    private final List<Parcel> parcels;
}
