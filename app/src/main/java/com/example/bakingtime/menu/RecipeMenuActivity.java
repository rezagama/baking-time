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
import com.example.bakingtime.details.RecipeDetailFragment;
import com.example.bakingtime.details.ingredients.IngredientsActivity;
import com.example.bakingtime.details.ingredients.IngredientsFragment;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.Step;

import static com.example.bakingtime.details.RecipeDetailActivity.VIEW_POSITION;
import static com.example.bakingtime.home.RecipeHomeActivity.RECIPE_DATA;
import static com.example.bakingtime.menu.RecipeMenuAdapter.VIEW_TYPE_INGREDIENTS;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeMenuActivity extends BaseActivity {
    private static final String LATEST_SELECTED_MENU = "selected_menu";

    private int selectedMenu;
    private RecipeMenuAdapter adapter;
    private ActivityRecipeMenuBinding binding;
    private Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_menu);
        recipe = getIntent().getParcelableExtra(RECIPE_DATA);

        initRecipeDetailsPage();
        setToolbar(recipe.name);
        setMenuAdapter();
        setMenuList();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LATEST_SELECTED_MENU, selectedMenu);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (!isTabletDevice()) return;
        int position = savedInstanceState.getInt(LATEST_SELECTED_MENU);
        binding.listRecipeMenu.scrollToPosition(position);
        openRecipeDetailsPage(position);
    }

    private void initRecipeDetailsPage() {
        if(isTabletDevice()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_recipe_details, IngredientsFragment.newInstance(recipe))
                    .commit();
        }
    }

    private void setMenuAdapter() {
        adapter = new RecipeMenuAdapter(getResources(), position -> {
            if(isTabletDevice()) {
                openRecipeDetailsPage(position);
            } else {
                goToRecipeDetailsPage(position);
            }
        });
    }

    private void openRecipeDetailsPage(int position) {
        if(position == VIEW_TYPE_INGREDIENTS) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_recipe_details, IngredientsFragment.newInstance(recipe))
                    .commit();
        } else {
            Step step = recipe.steps.get(position - 1);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_recipe_details, RecipeDetailFragment.newInstance(step))
                    .commit();
        }
        selectedMenu = position;
    }

    private void goToRecipeDetailsPage(int position) {
        Intent intent;
        if(position == VIEW_TYPE_INGREDIENTS) {
            intent = new Intent(this, IngredientsActivity.class);
        } else {
            intent = new Intent(this, RecipeDetailActivity.class);
        }
        intent.putExtra(VIEW_POSITION, position);
        intent.putExtra(RECIPE_DATA, recipe);
        startActivity(intent);
    }

    private void setMenuList() {
        binding.listRecipeMenu.setLayoutManager(new LinearLayoutManager(this));
        binding.listRecipeMenu.setAdapter(adapter);
        adapter.setRecipes(recipe);
    }
}
