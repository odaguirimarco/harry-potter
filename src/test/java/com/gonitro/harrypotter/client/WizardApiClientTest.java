package com.gonitro.harrypotter.client;

import com.gonitro.harrypotter.dto.Elixir;
import com.gonitro.harrypotter.dto.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WizardApiClientTest {

    @Mock
    private RestClient mockRestClient;
    @Mock
    private RestClient.RequestHeadersUriSpec mockRequestHeadersUriSpec;
    @Mock
    private RestClient.RequestHeadersSpec mockRequestHeadersSpec;
    @Mock
    private RestClient.ResponseSpec mockResponseSpec;
    @Mock
    private WizardWorldApiProperties mockProperties;

    private WizardApiClient wizardApiClient;

    private final Ingredient ingredient = new Ingredient("id", "name");
    private final List<Ingredient> ingredients = List.of(ingredient);

    private final Elixir elixir = new Elixir(
            "id", "name", "effect", "sideEffect", "color",
            "time", "difficulty", List.of(ingredient)
    );
    private final List<Elixir> elixirs = List.of(elixir);

    @BeforeEach
    void setUp() {
        wizardApiClient = new WizardApiClient(mockRestClient, mockProperties);
        when(mockRestClient.get()).thenReturn(mockRequestHeadersUriSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
    }

    @Test
    void fetchIngredients_shouldReturnListFromRestClient() {
        when(mockRestClient.get()).thenReturn(mockRequestHeadersUriSpec);
        doReturn(mockRequestHeadersSpec).when(mockRequestHeadersUriSpec).uri(any(Function.class));
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(ingredients);

        List<Ingredient> result = wizardApiClient.fetchIngredients();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("name", result.get(0).name());
        assertThat(result).hasSize(1).containsExactly(ingredient);

        verify(mockRestClient, times(1)).get();
        verify(mockRequestHeadersUriSpec, times(1)).uri(any(Function.class));
        verify(mockRequestHeadersSpec, times(1)).retrieve();
        verify(mockResponseSpec, times(1)).body(any(ParameterizedTypeReference.class));
    }

    @Test
    void fetchElixirs_shouldReturnListFromRestClient() {
        when(mockRestClient.get()).thenReturn(mockRequestHeadersUriSpec);
        doReturn(mockRequestHeadersSpec).when(mockRequestHeadersUriSpec).uri(any(Function.class));
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(elixirs);

        List<Elixir> result = wizardApiClient.fetchElixirs();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("name", result.get(0).name());
        assertThat(result).hasSize(1).containsExactly(elixir);

        verify(mockRestClient, times(1)).get();
        verify(mockRequestHeadersUriSpec, times(1)).uri(any(Function.class));
        verify(mockRequestHeadersSpec, times(1)).retrieve();
        verify(mockResponseSpec, times(1)).body(any(ParameterizedTypeReference.class));
    }

    @Test
    void fetchElixirs_whenClientThrowsException_shouldPropagateException() {
        when(mockRestClient.get()).thenReturn(mockRequestHeadersUriSpec);
        doReturn(mockRequestHeadersSpec).when(mockRequestHeadersUriSpec).uri(any(Function.class));
        when(mockResponseSpec.body(any(ParameterizedTypeReference.class)))
                .thenThrow(new RuntimeException("API Error"));

        assertThrows(RuntimeException.class, () -> wizardApiClient.fetchElixirs());
    }

    @Test
    void fetchIngredients_whenClientThrowsException_shouldPropagateException() {
        when(mockRestClient.get()).thenReturn(mockRequestHeadersUriSpec);
        doReturn(mockRequestHeadersSpec).when(mockRequestHeadersUriSpec).uri(any(Function.class));
        when(mockResponseSpec.body(any(ParameterizedTypeReference.class)))
                .thenThrow(new RuntimeException("API Error"));

        assertThrows(RuntimeException.class, () -> wizardApiClient.fetchIngredients());
    }
}