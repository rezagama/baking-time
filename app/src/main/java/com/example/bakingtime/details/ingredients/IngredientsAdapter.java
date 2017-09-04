package com.example.bakingtime.details.ingredients;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.ItemRecipeIngredientsBinding;
import com.example.bakingtime.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rezagama on 9/4/17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.RecipeViewHolder> {
    private List<Ingredient> ingredients;
    private Resources resources;

    public IngredientsAdapter(Resources resources) {
        this.resources = resources;
        ingredients = new ArrayList<>();
    }

    public void setIngredient(List<Ingredient> ingredients) {
        if(ingredients == null) return;
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecipeIngredientsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_recipe_ingredients, parent, false);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.setIngredient(ingredients.get(position), position + 1);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private ItemRecipeIngredientsBinding binding;

        public RecipeViewHolder(ItemRecipeIngredientsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setIngredient(Ingredient ingredient, int position) {
            binding.setIngredient(String.format(resources.getString(R.string.item_ingredients_name),
                    String.valueOf(position), ingredient.ingredient));
            binding.setMeasure(String.format(resources.getString(R.string.item_ingredients_measure),
                    String.valueOf(ingredient.quantity), ingredient.measure));
        }
    }
}