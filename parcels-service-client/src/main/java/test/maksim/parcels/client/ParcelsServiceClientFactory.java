package test.maksim.parcels.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import test.maksim.parcels.client.impl.ParcelsServiceClientImpl;

import java.net.http.HttpClient;

public class ParcelsServiceClientFactory {

    public ParcelsServiceClient defaultClient(String serviceUrl) {
        return ParcelsServiceClientImpl.builder()
                .gson(createGson())
                .httpClient(HttpClient.newHttpClient())
                .serviceUrl(serviceUrl)
                .build();
    }

    private Gson createGson() {
        return new GsonBuilder().create();
    }
}
