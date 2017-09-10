package com.example.bakingtime.menu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.example.bakingtime.BaseActivity;
import com.example.bakingtime.R;
import com.example.bakingtime.databinding.ActivityRecipeMenuBinding;
import com.example.bakingtime.details.RecipeDetailActivity;
import com.example.bakingtime.details.ingredients.IngredientsActivity;
import com.example.bakingtime.model.Recipe;

import static com.example.bakingtime.details.RecipeDetailActivity.VIEW_TYPE;
import static com.example.bakingtime.home.RecipeHomeActivity.RECIPE_DATA;
import static com.example.bakingtime.menu.RecipeMenuAdapter.VIEW_TYPE_INGREDIENTS;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeMenuActivity extends BaseActivity {
    private RecipeMenuAdapter adapter;
    private ActivityRecipeMenuBinding binding;
    private Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_menu);
        recipe = getIntent().getParcelableExtra(RECIPE_DATA);

        setToolbar(recipe.name);
        setMenuAdapter();
        setMenuList();
    }

    private void setMenuAdapter() {
        adapter = new RecipeMenuAdapter(getResources(), position -> {
            Intent intent;
            if(position == VIEW_TYPE_INGREDIENTS) {
                intent = new Intent(this, IngredientsActivity.class);
            } else {
                intent = new Intent(this, RecipeDetailActivity.class);
            }
            intent.putExtra(VIEW_TYPE, position);
            intent.putExtra(RECIPE_DATA, recipe);
            startActivity(intent);
        });
    }

    private void setMenuList() {
        binding.listRecipeMenu.setLayoutManager(new LinearLayoutManager(this));
        binding.listRecipeMenu.setAdapter(adapter);
        adapter.setRecipes(recipe);
    }
}
