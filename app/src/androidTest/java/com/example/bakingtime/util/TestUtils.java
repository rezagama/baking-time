package com.example.bakingtime.util;

import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rezagama on 9/14/17.
 */

public class TestUtils {
    public static Recipe buildRecipeData() {
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = new Ingredient();
        ingredient.ingredient = "Potato";
        ingredient.quantity = 3;
        ingredient.measure = "kg";
        ingredients.add(ingredient);

        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        step.id = 1;
        step.shortDescription = "Chopin Potatoe";
        step.description = "Chop d Potatoe";
        steps.add(step);

        Recipe recipe = new Recipe();
        recipe.id = 1;
        recipe.name = "French Fries";
        recipe.servings = 8;
        recipe.ingredients = ingredients;
        recipe.steps = steps;

        return recipe;
    }
}
