package com.example.bakingtime.details.ingredients;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.FragmentRecipeIngredientsBinding;
import com.example.bakingtime.model.Recipe;

import static com.example.bakingtime.home.RecipeHomeActivity.RECIPE_DATA;

/**
 * Created by rezagama on 9/4/17.
 */

public class IngredientsFragment extends Fragment {
    private Recipe recipe;
    private FragmentRecipeIngredientsBinding binding;

    public static IngredientsFragment newInstance(Recipe recipe) {
        Bundle args = new Bundle();
        IngredientsFragment fragment = new IngredientsFragment();
        args.putParcelable(RECIPE_DATA, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_ingredients, container, false);
        recipe = getArguments().getParcelable(RECIPE_DATA);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IngredientsAdapter adapter = new IngredientsAdapter(getResources());
        binding.listIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.listIngredients.setAdapter(adapter);
        adapter.setIngredient(recipe.ingredients);
    }
}
