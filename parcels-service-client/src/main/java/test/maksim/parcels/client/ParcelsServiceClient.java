package test.maksim.parcels.client;

import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public interface ParcelsServiceClient {

    CompletableFuture<HttpResponse<Void>> register(Shipment shipment);

    CompletableFuture<HttpResponse<Void>> push(Tracking tracking);
}
