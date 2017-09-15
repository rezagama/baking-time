package com.example.bakingtime.menu;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.ItemRecipeMenuBinding;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.Step;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeMenuAdapter extends RecyclerView.Adapter<RecipeMenuAdapter.RecipeMenuViewHolder> {
    public static final int VIEW_TYPE_INGREDIENTS = 0;
    public static final int VIEW_TYPE_STEPS = 1;

    private Recipe recipe;
    private Resources res;
    private RecipeMenuClickListener listener;

    public RecipeMenuAdapter(Resources res, RecipeMenuClickListener listener) {
        this.res = res;
        this.listener = listener;
    }

    public void setRecipes(Recipe recipe) {
        if(recipe == null) return;
        this.recipe = recipe;
        notifyDataSetChanged();
    }

    @Override
    public RecipeMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecipeMenuBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_recipe_menu, parent, false);
        return new RecipeMenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecipeMenuViewHolder holder, int position) {
        holder.setRecipeMenu(position);
    }

    @Override
    public int getItemCount() {
        return recipe != null ? getListCount() : 0;
    }

    private int getListCount() {
        return recipe.steps.size() + 1;
    }

    public class RecipeMenuViewHolder extends RecyclerView.ViewHolder {
        private ItemRecipeMenuBinding binding;

        public RecipeMenuViewHolder(ItemRecipeMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setRecipeMenu(int position) {
            if(position != VIEW_TYPE_INGREDIENTS) {
                Step step = recipe.steps.get(position - 1);
                binding.setMenuTitle(step.shortDescription);
                binding.setImageUrl(step.thumbnailURL);
            } else {
                binding.setMenuTitle(res.getString(R.string.text_recipe_ingredients));
            }
            binding.getRoot().setOnClickListener(v -> listener.onRecipeMenuClick(position));
        }
    }

    public interface RecipeMenuClickListener {
        void onRecipeMenuClick(int position);
    }
}