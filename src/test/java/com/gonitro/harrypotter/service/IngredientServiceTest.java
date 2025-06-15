package com.gonitro.harrypotter.service;

import com.gonitro.harrypotter.client.WizardApiClient;
import com.gonitro.harrypotter.dto.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    private WizardApiClient mockWizardApiClient;
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        mockWizardApiClient = mock(WizardApiClient.class);
        ingredientService = new IngredientService(mockWizardApiClient);
    }

    @Test
    void getAllIngredients_returnsListFromApiClient() {
        Ingredient ingredient1 = new Ingredient(
                UUID.randomUUID().toString(),
                "Unicorn Hair"
        );
        Ingredient ingredient2 = new Ingredient(
                UUID.randomUUID().toString(),
                "Phoenix Feather"
        );
        List<Ingredient> mockIngredients = Arrays.asList(ingredient1, ingredient2);
        when(mockWizardApiClient.fetchIngredients()).thenReturn(mockIngredients);

        List<Ingredient> result = ingredientService.getAllIngredients();

        assertThat(result).hasSize(2).containsExactly(ingredient1, ingredient2);
        verify(mockWizardApiClient, times(1)).fetchIngredients();
    }

    @Test
    void getAllIngredients_whenClientReturnsEmptyList_shouldReturnEmptyList() {
        when(mockWizardApiClient.fetchIngredients()).thenReturn(List.of());

        List<Ingredient> result = ingredientService.getAllIngredients();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllIngredients_whenClientThrowsException_shouldPropagateException() {
        when(mockWizardApiClient.fetchIngredients()).thenThrow(new RuntimeException("API Error"));

        assertThrows(RuntimeException.class, () -> ingredientService.getAllIngredients());
    }
}
