package com.gonitro.harrypotter.dto;

import java.util.List;

public record Elixir(
        String id,
        String name,
        String effect,
        String sideEffects,
        String characteristics,
        String time,
        String difficulty,
        List<Ingredient> ingredients
) {
}
