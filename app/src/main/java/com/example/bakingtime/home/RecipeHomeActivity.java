package com.example.bakingtime.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.ActivityRecipeHomeBinding;
import com.example.bakingtime.deps.BakingDepsProvider;
import com.example.bakingtime.home.adapter.RecipeAdapter;
import com.example.bakingtime.menu.RecipeMenuActivity;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.network.NetworkService;

import java.util.List;

import javax.inject.Inject;

public class RecipeHomeActivity extends AppCompatActivity implements RecipeHomeView {
    public static final String RECIPE_DATA = "recipe_data";

    private RecipeHomeViewModel viewModel;
    private ActivityRecipeHomeBinding binding;
    private RecipeAdapter adapter;

    @Inject
    NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BakingDepsProvider) getApplicationContext()).provideAppDependencies().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_home);
        binding.listRecipe.setLayoutManager(new LinearLayoutManager(this));
        viewModel = new RecipeHomeViewModel(service, this);
        adapter = new RecipeAdapter(getResources(), recipe -> {
            Intent intent = new Intent(this, RecipeMenuActivity.class);
            intent.putExtra(RECIPE_DATA, recipe);
            startActivity(intent);
        });
        binding.listRecipe.setAdapter(adapter);
        viewModel.getRecipeList();
    }

    @Override
    public void loadRecipeList(List<Recipe> recipes) {
        adapter.setRecipes(recipes);
    }

    @Override
    public void showProgressBar() {
        binding.setProgressBarVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.setProgressBarVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
