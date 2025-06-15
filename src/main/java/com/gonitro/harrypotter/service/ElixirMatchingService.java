package com.gonitro.harrypotter.service;

import com.gonitro.harrypotter.dto.Elixir;
import com.gonitro.harrypotter.dto.ElixirMatch;
import com.gonitro.harrypotter.dto.Ingredient;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ElixirMatchingService {

    private final ElixirService elixirService;

    public ElixirMatchingService(ElixirService elixirService) {
        this.elixirService = elixirService;
    }

    public List<ElixirMatch> findMatchingElixirs(Set<String> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            return List.of();
        }

        List<Elixir> elixirs = elixirService.getAllElixirs();

        return elixirs.parallelStream()
                .map(elixir -> calculateMatch(elixir, ingredients))
                .filter(match -> match.matchPercentage() > 0)
                .sorted(Comparator.comparingDouble(ElixirMatch::matchPercentage).reversed())
                .toList();
    }

    private ElixirMatch calculateMatch(Elixir elixir, Set<String> availableIngredients) {
        if (elixir.ingredients() == null || elixir.ingredients().isEmpty()) {
            return new ElixirMatch(elixir, List.of(), List.of(), 0.0);
        }

        Set<String> requiredIngredients = elixir.ingredients().stream()
                .map(Ingredient::name)
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        Set<String> normalizedAvailable = availableIngredients.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        List<String> available = requiredIngredients.stream()
                .filter(normalizedAvailable::contains)
                .toList();

        List<String> missing = requiredIngredients.stream()
                .filter(ingredient -> !normalizedAvailable.contains(ingredient))
                .toList();

        double matchPercentage = requiredIngredients.isEmpty() ? 0.0 :
                (double) available.size() / requiredIngredients.size() * 100.0;

        return new ElixirMatch(elixir, available, missing, matchPercentage);
    }
}
