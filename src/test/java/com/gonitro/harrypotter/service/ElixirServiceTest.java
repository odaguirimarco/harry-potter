package com.gonitro.harrypotter.service;

import com.gonitro.harrypotter.client.WizardApiClient;
import com.gonitro.harrypotter.dto.Elixir;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElixirServiceTest {

    private WizardApiClient mockWizardApiClient;
    private ElixirService elixirService;

    private final Elixir elixir1 = new Elixir(
            "felix-001",
            "Felix Felicis",
            "Makes the drinker lucky",
            "Overconfidence",
            "Golden liquid",
            "6 months",
            "Advanced",
            List.of()
    );
    private final Elixir elixir2 = new Elixir(
            "peace-001",
            "Draught of Peace",
            "Calms anxiety",
            "Drowsiness",
            "Turquoise smoke",
            "3 hours",
            "Moderate",
            List.of()
    );
    private final List<Elixir> sampleElixirs = Arrays.asList(elixir1, elixir2);

    @BeforeEach
    void setUp() {
        mockWizardApiClient = mock(WizardApiClient.class);
        elixirService = new ElixirService(mockWizardApiClient);
    }

    @Test
    void getAllElixirs_shouldReturnElixirsFromClient() {
        when(mockWizardApiClient.fetchElixirs()).thenReturn(sampleElixirs);

        List<Elixir> result = elixirService.getAllElixirs();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Felix Felicis", result.get(0).name());
        assertEquals("Draught of Peace", result.get(1).name());
        verify(mockWizardApiClient, times(1)).fetchElixirs();
    }

    @Test
    void getAllElixirs_whenClientReturnsEmptyList_shouldReturnEmptyList() {
        when(mockWizardApiClient.fetchElixirs()).thenReturn(List.of());

        List<Elixir> result = elixirService.getAllElixirs();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllElixirs_whenClientThrowsException_shouldPropagateException() {
        when(mockWizardApiClient.fetchElixirs()).thenThrow(new RuntimeException("API Error"));

        assertThrows(RuntimeException.class, () -> elixirService.getAllElixirs());
    }
}
