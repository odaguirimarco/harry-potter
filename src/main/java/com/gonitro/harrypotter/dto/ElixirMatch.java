package com.gonitro.harrypotter.dto;

import java.util.List;

public record ElixirMatch(
        Elixir elixir,
        List<String> availableIngredients,
        List<String> missingIngredients,
        double matchPercentage
) {
}
