package com.gonitro.harrypotter.service;

import com.gonitro.harrypotter.client.WizardApiClient;
import com.gonitro.harrypotter.dto.Elixir;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElixirService {

    private final WizardApiClient wizardApiClient;

    public ElixirService(WizardApiClient wizardApiClient) {
        this.wizardApiClient = wizardApiClient;
    }

    @Cacheable("ALL_ELIXIRS")
    public List<Elixir> getAllElixirs() {
        return wizardApiClient.fetchElixirs();
    }
}
