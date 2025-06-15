package com.gonitro.harrypotter.service;

import com.gonitro.harrypotter.dto.Elixir;
import com.gonitro.harrypotter.dto.ElixirMatch;
import com.gonitro.harrypotter.dto.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElixirMatchingServiceTest {

    private ElixirService elixirService;
    private ElixirMatchingService elixirMatchingService;

    @BeforeEach
    void setUp() {
        elixirService = mock(ElixirService.class);
        elixirMatchingService = new ElixirMatchingService(elixirService);
    }

    @Test
    void should_return_empty_list_when_ingredients_is_null() {
        List<ElixirMatch> matches = elixirMatchingService.findMatchingElixirs(null);
        assertThat(matches).isEmpty();
    }

    @Test
    void should_return_empty_list_when_ingredients_is_empty() {
        List<ElixirMatch> matches = elixirMatchingService.findMatchingElixirs(Collections.emptySet());
        assertThat(matches).isEmpty();
    }

    @Test
    void should_return_no_matches_when_elixirs_list_is_empty() {
        when(elixirService.getAllElixirs()).thenReturn(List.of());
        List<ElixirMatch> matches = elixirMatchingService.findMatchingElixirs(Set.of("unicorn hair"));
        assertThat(matches).isEmpty();
    }

    @Test
    void should_return_match_when_ingredient_fully_matches() {
        Ingredient dragonScale = new Ingredient(
                UUID.randomUUID().toString(),
                "Dragon Scale"
        );
        Elixir elixir = new Elixir(
                UUID.randomUUID().toString(),
                "Felix Felicis",
                "Makes the drinker lucky",
                "Overconfidence",
                "Golden liquid",
                "6 months",
                "Advanced",
                List.of(dragonScale)
        );
        when(elixirService.getAllElixirs()).thenReturn(List.of(elixir));

        List<ElixirMatch> matches = elixirMatchingService.findMatchingElixirs(Set.of("dragon scale"));

        assertThat(matches).hasSize(1);
        ElixirMatch match = matches.get(0);
        assertThat(match.availableIngredients()).containsExactly("dragon scale");
        assertThat(match.missingIngredients()).isEmpty();
        assertThat(match.matchPercentage()).isEqualTo(100.0);
    }

    @Test
    void should_return_partial_match() {
        Ingredient feather = new Ingredient(
                UUID.randomUUID().toString(),
                "Phoenix Feather"
        );
        Ingredient root = new Ingredient(
                UUID.randomUUID().toString(),
                "Mandrake Root");
        Elixir elixir = new Elixir(
                UUID.randomUUID().toString(),
                "Felix Felicis",
                "Makes the drinker lucky",
                "Overconfidence",
                "Golden liquid",
                "6 months",
                "Advanced",
                List.of(feather, root)
        );
        when(elixirService.getAllElixirs()).thenReturn(List.of(elixir));

        List<ElixirMatch> matches = elixirMatchingService.findMatchingElixirs(Set.of("Mandrake Root"));

        assertThat(matches).hasSize(1);
        ElixirMatch match = matches.get(0);
        assertThat(match.availableIngredients()).containsExactly("mandrake root");
        assertThat(match.missingIngredients()).containsExactly("phoenix feather");
        assertThat(match.matchPercentage()).isEqualTo(50.0);
    }

    @Test
    void should_not_return_zero_percent_matches() {
        Ingredient root = new Ingredient(
                UUID.randomUUID().toString(),
                "Gillyweed"
        );
        Elixir elixir = new Elixir(
                UUID.randomUUID().toString(),
                "Felix Felicis",
                "Makes the drinker lucky",
                "Overconfidence",
                "Golden liquid",
                "6 months",
                "Advanced",
                List.of(root)
        );
        when(elixirService.getAllElixirs()).thenReturn(List.of(elixir));

        List<ElixirMatch> matches = elixirMatchingService.findMatchingElixirs(Set.of("unicorn hair"));
        assertThat(matches).isEmpty();
    }

    @Test
    void should_normalize_ingredient_case() {
        Ingredient eye = new Ingredient(
                UUID.randomUUID().toString(),
                "Newt Eye"
        );
        Elixir elixir = new Elixir(
                UUID.randomUUID().toString(),
                "Felix Felicis",
                "Makes the drinker lucky",
                "Overconfidence",
                "Golden liquid",
                "6 months",
                "Advanced",
                List.of(eye)
        );
        when(elixirService.getAllElixirs()).thenReturn(List.of(elixir));

        List<ElixirMatch> matches = elixirMatchingService.findMatchingElixirs(Set.of("NeWt EyE"));
        assertThat(matches).hasSize(1);
        assertThat(matches.get(0).matchPercentage()).isEqualTo(100.0);
    }

}
