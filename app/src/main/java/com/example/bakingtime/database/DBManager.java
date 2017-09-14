package com.example.bakingtime.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rezagama on 9/14/17.
 */

public class DBManager {
    private Context context;

    public DBManager(Context context) {
        this.context = context;
    }

    public void addRecipeToLocalDB(List<Recipe> recipes){
        for (Recipe recipe : recipes) {
            if (isRecipeExists(recipe.id)) return;
            ContentValues data = new ContentValues();
            data.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID, recipe.id);
            data.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME, recipe.name);
            data.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_SERVINGS, recipe.servings);

            context.getContentResolver().insert(RecipeContract.RecipeEntry.CONTENT_URI, data);
        }
    }

    public void addIngredientsToLocalDB(List<Recipe> recipes){
        for (Recipe recipe : recipes) {
            for (Ingredient ingredient : recipe.ingredients) {
                if (isIngredientExists(recipe.id, ingredient.ingredient)) return;
                ContentValues data = new ContentValues();
                data.put(RecipeContract.IngredientsEntry.COLUMN_RECIPE_ID, recipe.id);
                data.put(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_NAME, ingredient.ingredient);
                data.put(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_QUANTITY, ingredient.quantity);
                data.put(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_MEASURE, ingredient.measure);

                context.getContentResolver().insert(RecipeContract.IngredientsEntry.CONTENT_URI, data);
            }
        }
    }

    public List<Ingredient> getIngredientsFromLocalDB(long recipeId){
        List<Ingredient> ingredients = new ArrayList<>();
        Uri uri = ContentUris.withAppendedId(RecipeContract.IngredientsEntry.CONTENT_URI, recipeId);
        Cursor cursor = context.getContentResolver().query(uri, null,
                RecipeContract.IngredientsEntry.COLUMN_RECIPE_ID,
                new String[]{String.valueOf(recipeId)},
                null);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();

                int nameIndex = cursor.getColumnIndex(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_NAME);
                int quantityIndex = cursor.getColumnIndex(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_QUANTITY);
                int measureIndex = cursor.getColumnIndex(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_MEASURE);

                ingredient.ingredient = cursor.getString(nameIndex);
                ingredient.quantity = cursor.getFloat(quantityIndex);
                ingredient.measure = cursor.getString(measureIndex);

                ingredients.add(ingredient);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return ingredients;
    }

    private boolean isIngredientExists(long recipeId, String ingredient) {
        int size = 0;
        Uri uri = ContentUris.withAppendedId(RecipeContract.IngredientsEntry.CONTENT_URI, recipeId);
        Cursor cursor = context.getContentResolver().query(uri, null,
                RecipeContract.IngredientsEntry.COLUMN_RECIPE_ID,
                new String[]{String.valueOf(recipeId), ingredient},
                null);
        if(cursor != null) {
            size = cursor.getCount();
            cursor.close();
        }
        return size > 0;
    }

    private boolean isRecipeExists(int recipeId) {
        int size = 0;
        Uri uri = ContentUris.withAppendedId(RecipeContract.RecipeEntry.CONTENT_URI, recipeId);
        Cursor cursor = context.getContentResolver().query(uri, null,
                RecipeContract.RecipeEntry.COLUMN_RECIPE_ID,
                new String[]{String.valueOf(recipeId)},
                null);
        if(cursor != null) {
            size = cursor.getCount();
            cursor.close();
        }
        return size > 0;
    }
}
