package com.gonitro.harrypotter.config;

import com.gonitro.harrypotter.client.WizardWorldApiProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(WizardWorldApiProperties wizardWorldApiProperties,
                                 RestClient.Builder builder) {
        return builder
                .baseUrl(wizardWorldApiProperties.getUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
