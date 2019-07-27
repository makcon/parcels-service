package test.maksim.parcels.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParcelsServiceClientConfig {

    private final ParcelsServiceClientFactory factory = new ParcelsServiceClientFactory();

    @Bean
    public ParcelsServiceClient contentClient(@Value("${parcels.service.base.url:http://localhost:8085}") String serviceUrl) {
        return factory.defaultClient(serviceUrl);
    }
}
