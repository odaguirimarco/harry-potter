package com.gonitro.harrypotter.client;

import com.gonitro.harrypotter.dto.Elixir;
import com.gonitro.harrypotter.dto.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class WizardApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WizardApiClient.class);

    private final RestClient restClient;
    private final WizardWorldApiProperties properties;

    public WizardApiClient(RestClient restClient,
                           WizardWorldApiProperties properties) {
        this.restClient = restClient;
        this.properties = properties;
    }

    public List<Ingredient> fetchIngredients() {
        LOGGER.info("Fetching ingredients from API...");
        return restClient.get().uri(uriBuilder -> uriBuilder
                .path(properties.getIngredientsPath())
                .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<Ingredient>>() {});
    }

    public List<Elixir> fetchElixirs() {
        LOGGER.info("Fetching elixirs from API...");
        return restClient.get().uri(uriBuilder -> uriBuilder
                        .path(properties.getElixirsPath())
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<Elixir>>() {});
    }
}
