package com.gonitro.harrypotter.service;

import com.gonitro.harrypotter.dto.Elixir;
import com.gonitro.harrypotter.dto.ElixirMatch;
import com.gonitro.harrypotter.dto.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConsoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleService.class);

    private final Scanner scanner;
    private final IngredientService ingredientService;
    private final ElixirMatchingService matchingService;

    public ConsoleService(IngredientService ingredientService,
                          ElixirMatchingService matchingService) {
        this.ingredientService = ingredientService;
        this.matchingService = matchingService;
        this.scanner = new Scanner(System.in);
    }

    public void runInteractiveMode() {
        printWelcome();

        boolean running = true;
        while (running) {
            printMenu();
            int option = readUserChoice();
            switch (option) {
                case 1 -> listAllIngredients();
                case 2 -> matchElixirs();
                case 3 -> running = false;
                default -> System.out.println("Invalid option. Try again...");
            }
        }
        System.exit(0);
    }

    private void listAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        ingredients.forEach(ingredient ->
                System.out.println("- " + ingredient.name()));
    }

    private int readUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private void printMenu() {
        System.out.println("1. List all ingredients");
        System.out.println("2. Match elixirs (provide ingredients)");
        System.out.println("3. Quit");
        System.out.print("Choose an option (1-3): ");
    }

    private void matchElixirs() {
        System.out.println("Enter ingredients (comma-separated): ");
        System.out.println("🧪 Ingredients: ");
        String input = scanner.nextLine().trim();

        Set<String> usersIngredients = Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());

        if (usersIngredients.isEmpty()) {
            System.out.println("No ingredients provided.");
            return;
        }

        System.out.println("🔍 Searching for matching elixirs...");
        List<ElixirMatch> matches = matchingService.findMatchingElixirs(usersIngredients);

        if (matches == null || matches.isEmpty()) {
            String userIngredients = String.join(", ", usersIngredients);
            System.out.println("😞 No elixirs can be made with your ingredients " + userIngredients + ".");
        } else {
            System.out.println("✨ Found " + matches.size() + " matching elixirs:");
            for (int i = 0; i < matches.size(); i++) {
                ElixirMatch match = matches.get(i);
                printElixirMatch(match, i + 1);
            }
        }
    }

    private void printElixirMatch(ElixirMatch elixirMatch, int rank) {

        Elixir elixir = elixirMatch.elixir();

        String match = String.format("%d. %s (%.1f%% match)", rank, elixir.name(), elixirMatch.matchPercentage());
        System.out.println(match);

        if (elixir.effect() != null) {
            System.out.printf("   Effect: %s%n", elixir.effect());
        }

        if (!elixirMatch.availableIngredients().isEmpty()) {
            String availableIngredients = String.join(", ", elixirMatch.availableIngredients());
            System.out.println(String.format("   ✅ Available: %s", availableIngredients));
        }

        if (!elixirMatch.missingIngredients().isEmpty()) {
            String missingIngredients = String.join(", ", elixirMatch.missingIngredients());
            System.out.println(String.format("   ❌ Missing: %s", missingIngredients));
        }

        if (elixir.difficulty() != null) {
            System.out.println(String.format("   Difficulty: %s", elixir.difficulty()));
        }

        System.out.println();
    }

    private void printWelcome() {
        System.out.println("============================================");
        System.out.println("🪄 Welcome to Harry Potter Wizard World! 🪄");
        System.out.println("============================================");
    }
}
