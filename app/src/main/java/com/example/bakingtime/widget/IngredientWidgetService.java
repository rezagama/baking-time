package com.example.bakingtime.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingtime.database.DBManager;
import com.example.bakingtime.model.Ingredient;

import java.util.List;

import static com.example.bakingtime.widget.IngredientWidgetService.RECIPE_ID;

/**
 * Created by rezagama on 9/13/17.
 */

public class IngredientWidgetService extends RemoteViewsService {
    public static final String RECIPE_ID = "recipe_id";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private DBManager dbManager;
    private List<Ingredient> ingredients;
    private SharedPreferences prefs;

    public ListRemoteViewsFactory(Context context) {
        this.context = context;
        dbManager = new DBManager(context);
        initSharedPreference();
    }

    private void initSharedPreference() {
        prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate() {
        ingredients = dbManager.getIngredientsFromLocalDB(prefs.getLong(RECIPE_ID, 0));
    }

    @Override
    public void onDataSetChanged() {
        ingredients = dbManager.getIngredientsFromLocalDB(prefs.getLong(RECIPE_ID, 0));
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        String number = String.valueOf(i+1);
        Ingredient ingredient = ingredients.get(i);
        RemoteViews view = new RemoteViews(context.getPackageName(),
                android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, number + ". " + ingredient.ingredient + " "
                + ingredient.quantity + " " + ingredient.measure);
        view.setTextColor(android.R.id.text1, ContextCompat.getColor(context, android.R.color.white));

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
