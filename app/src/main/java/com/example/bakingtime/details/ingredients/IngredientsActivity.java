package com.example.bakingtime.details.ingredients;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.example.bakingtime.BaseActivity;
import com.example.bakingtime.R;
import com.example.bakingtime.model.Recipe;

import static com.example.bakingtime.home.RecipeHomeActivity.RECIPE_DATA;

/**
 * Created by rezagama on 9/4/17.
 */

public class IngredientsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Recipe recipe = getIntent().getParcelableExtra(RECIPE_DATA);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, IngredientsFragment.newInstance(recipe))
                .commit();
        setToolbar(getString(R.string.text_recipe_ingredients));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
