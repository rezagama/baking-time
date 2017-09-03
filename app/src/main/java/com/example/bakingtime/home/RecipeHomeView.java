package com.example.bakingtime.home;

import com.example.bakingtime.model.Recipe;

import java.util.List;

/**
 * Created by rezagama on 9/3/17.
 */

public interface RecipeHomeView {
    void loadRecipeList(List<Recipe> recipes);

    void showProgressBar();

    void hideProgressBar();
}
