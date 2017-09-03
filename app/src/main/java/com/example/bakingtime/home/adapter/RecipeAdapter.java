package com.example.bakingtime.home.adapter;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.ItemRecipeLayoutBinding;
import com.example.bakingtime.home.viewmodel.RecipeItemViewModel;
import com.example.bakingtime.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;
    private Resources resources;
    private RecipeClickListener listener;

    public RecipeAdapter(Resources resources, RecipeClickListener listener) {
        this.resources = resources;
        this.listener = listener;
        recipes = new ArrayList<>();
    }

    public void setRecipes(List<Recipe> recipes) {
        if(recipes == null) return;
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecipeLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_recipe_layout, parent, false);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        RecipeItemViewModel viewModel = new RecipeItemViewModel(resources);
        viewModel.setRecipe(recipes.get(position));
        holder.setClickListener(recipes.get(position));
        holder.setViewModel(viewModel);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private ItemRecipeLayoutBinding binding;

        public RecipeViewHolder(ItemRecipeLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(RecipeItemViewModel viewModel) {
            binding.setViewModel(viewModel);
        }

        public void setClickListener(Recipe recipe) {
            binding.getRoot().setOnClickListener(v ->
                    listener.onClick(recipe));
        }
    }

    public interface RecipeClickListener {
        void onClick(Recipe recipe);
    }
}
