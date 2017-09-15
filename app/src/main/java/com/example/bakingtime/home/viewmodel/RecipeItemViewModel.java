package com.example.bakingtime.home.viewmodel;

import android.content.res.Resources;
import android.databinding.BaseObservable;

import com.example.bakingtime.R;
import com.example.bakingtime.model.Recipe;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeItemViewModel extends BaseObservable {
    private Recipe recipe;
    private Resources res;

    public RecipeItemViewModel(Resources res) {
        this.res = res;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeName() {
        return recipe.name;
    }

    public String getServingsCount() {
        return res.getQuantityString(R.plurals.item_recipe_servings,
                recipe.servings, recipe.servings);
    }

    public String getIngredientsCount () {
        return res.getQuantityString(R.plurals.item_recipe_ingredients,
                recipe.ingredients.size(), recipe.ingredients.size());
    }

    public String getStepsCount() {
        return res.getQuantityString(R.plurals.item_recipe_steps,
                recipe.steps.size(), recipe.steps.size());
    }

    public String getRecipeImage() {
        return recipe.image;
    }
}
