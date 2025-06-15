package com.gonitro.harrypotter.service;

import com.gonitro.harrypotter.client.WizardApiClient;
import com.gonitro.harrypotter.dto.Ingredient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final WizardApiClient wizardApiClient;

    public IngredientService(WizardApiClient wizardApiClient) {
        this.wizardApiClient = wizardApiClient;
    }

    @Cacheable("ALL_INGREDIENTS")
    public List<Ingredient> getAllIngredients() {
        return wizardApiClient.fetchIngredients();
    }
}
