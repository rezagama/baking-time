package com.example.bakingtime.details;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.ActivityRecipeDetailBinding;
import com.example.bakingtime.model.Recipe;

import static com.example.bakingtime.home.RecipeHomeActivity.RECIPE_DATA;
import static com.example.bakingtime.menu.RecipeMenuAdapter.VIEW_TYPE_INGREDIENTS;

/**
 * Created by rezagama on 9/4/17.
 */

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String VIEW_TYPE = "view_type";

    private ActivityRecipeDetailBinding binding;
    private Recipe recipe;
    private int viewType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
        viewType = getIntent().getIntExtra(VIEW_TYPE, VIEW_TYPE_INGREDIENTS);
        recipe = getIntent().getParcelableExtra(RECIPE_DATA);
        setDetailLayout();
    }

    private void setDetailLayout() {
        if(viewType == VIEW_TYPE_INGREDIENTS) {

        } else {

        }
    }
}
