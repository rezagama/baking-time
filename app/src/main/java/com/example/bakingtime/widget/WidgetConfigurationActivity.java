package com.example.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bakingtime.BaseActivity;
import com.example.bakingtime.R;
import com.example.bakingtime.database.RecipeContract;
import com.example.bakingtime.databinding.ActivityWidgetConfigurationBinding;
import com.example.bakingtime.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_IDS;
import static com.example.bakingtime.widget.IngredientWidgetProvider.RECIPE_NAME;
import static com.example.bakingtime.widget.IngredientWidgetService.RECIPE_ID;

/**
 * Created by rezagama on 9/13/17.
 */

public class WidgetConfigurationActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Recipe> recipes = getRecipeList();
        ActivityWidgetConfigurationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_widget_configuration);
        ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(this, android.R.layout.simple_list_item_1, recipes) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                String number = String.valueOf(position + 1);
                TextView txtRecipe = (TextView) super.getView(position, convertView, parent);
                txtRecipe.setText(String.format("%s. %s", number, recipes.get(position).name));
                return txtRecipe;
            }
        };
        final SharedPreferences prefs = this.getSharedPreferences(
                getPackageName(), Context.MODE_PRIVATE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName thisAppWidget = new ComponentName(getPackageName(), IngredientWidgetProvider.class.getName());

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        binding.listRecipe.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, IngredientWidgetProvider.class);
            intent.setAction(ACTION_APPWIDGET_UPDATE);
            intent.putExtra(EXTRA_APPWIDGET_IDS, appWidgetIds);
            saveSelectedRecipe(prefs, recipes.get(i));
            sendBroadcast(intent);
            finish();
        });

        binding.listRecipe.setAdapter(adapter);
        setToolbar(getString(R.string.text_select_recipe));
    }

    private void saveSelectedRecipe(SharedPreferences prefs, Recipe recipe) {
        prefs.edit().putLong(RECIPE_ID, recipe.id).apply();
        prefs.edit().putString(RECIPE_NAME, recipe.name).apply();
    }

    private List<Recipe> getRecipeList() {
        List<Recipe> recipes = new ArrayList<>();
        Cursor cursor = getContentResolver().query(RecipeContract.RecipeEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if(cursor != null && cursor.moveToFirst()){
            do {
                Recipe recipe = new Recipe();

                int idIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID);
                int nameIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME);
                int servingsIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_SERVINGS);

                recipe.id = cursor.getInt(idIndex);
                recipe.name = cursor.getString(nameIndex);
                recipe.servings = cursor.getInt(servingsIndex);

                recipes.add(recipe);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return recipes;
    }
}
