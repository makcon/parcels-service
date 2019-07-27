package test.maksim.parcels.client.impl;

import com.google.gson.Gson;
import test.maksim.parcels.client.ParcelsServiceClient;
import test.maksim.parcels.constants.Endpoints;
import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import lombok.Builder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

@Builder
public class ParcelsServiceClientImpl implements ParcelsServiceClient {

    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";

    private final String serviceUrl;
    private final HttpClient httpClient;
    private final Gson gson;

    @Override
    public CompletableFuture<HttpResponse<Void>> register(Shipment shipment) {
        return execute(Endpoints.REGISTER, METHOD_POST, shipment);
    }

    @Override
    public CompletableFuture<HttpResponse<Void>> push(Tracking tracking) {
        return execute(Endpoints.PUSH, METHOD_PUT, tracking);
    }

    private CompletableFuture<HttpResponse<Void>> execute(String url,
                                                          String method,
                                                          Object payload) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceUrl + url))
                .header("Content-Type", "application/json")
                .method(method, HttpRequest.BodyPublishers.ofString(gson.toJson(payload)))
                .build();

        return httpClient.sendAsync(request, BodyHandlers.discarding());
    }
}
