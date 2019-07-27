package test.maksim.parcels.client;

import test.maksim.parcels.dto.Parcel;
import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;

import java.util.List;

import static test.maksim.parcels.dto.Tracking.Status.DELIVERED;
import static test.maksim.parcels.dto.Tracking.Status.WAITING_IN_HUB;

public class ParcelsServiceClientUsage {

    private static final String REFERENCE = "ABCD123456";

    public static void main(String[] args) {
        ParcelsServiceClientFactory factory = new ParcelsServiceClientFactory();
        ParcelsServiceClient client = factory.defaultClient("http://localhost:8085");

        client.register(createShipment()).join();

        client.push(createTrackingA());
        client.push(createTrackingB());
        client.push(createTrackingC());
        client.push(createTrackingD());
        client.push(createTrackingE());
        client.push(createTrackingF());
        client.push(createTrackingG());
        client.push(createTrackingH());
    }

    private static Tracking createTrackingA() {
        return Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(null)
                .status(WAITING_IN_HUB)
                .build();
    }

    private static Tracking createTrackingB() {
        return Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(2)
                .status(WAITING_IN_HUB)
                .build();
    }

    private static Tracking createTrackingC() {
        return Tracking.builder()
                .reference(REFERENCE)
                .parcels(1)
                .weight(15)
                .status(WAITING_IN_HUB)
                .build();
    }

    private static Tracking createTrackingD() {
        return Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(30)
                .status(WAITING_IN_HUB)
                .build();
    }

    private static Tracking createTrackingE() {
        return Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(2)
                .status(DELIVERED)
                .build();
    }

    private static Tracking createTrackingF() {
        return Tracking.builder()
                .reference(REFERENCE)
                .parcels(2)
                .weight(30)
                .status(DELIVERED)
                .build();
    }

    private static Tracking createTrackingG() {
        return Tracking.builder()
                .reference("EFGH123456")
                .parcels(2)
                .weight(30)
                .status(DELIVERED)
                .build();
    }

    private static Tracking createTrackingH() {
        return Tracking.builder()
                .reference(REFERENCE)
                .parcels(null)
                .weight(30)
                .status(DELIVERED)
                .build();
    }

    private static Shipment createShipment() {
        Parcel parcel1 = Parcel.builder()
                .weight(1)
                .width(10)
                .height(10)
                .length(10)
                .build();
        Parcel parcel2 = Parcel.builder()
                .weight(2)
                .width(20)
                .height(20)
                .length(20)
                .build();

        return new Shipment(REFERENCE, List.of(parcel1, parcel2));
    }
}
