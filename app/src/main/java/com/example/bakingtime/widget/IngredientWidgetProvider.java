package com.example.bakingtime.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.bakingtime.R;

import static com.example.bakingtime.widget.IngredientWidgetService.RECIPE_ID;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {
    public static final String RECIPE_NAME = "recipe_name";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        final SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_ingredient_widget);
        Intent intent = new Intent(context, WidgetConfigurationActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.
                getActivity(context, appWidgetId,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setTextViewText(R.id.recipe_title, prefs.getString(RECIPE_NAME, ""));
        views.setOnClickPendingIntent(R.id.btn_select_recipe, pendingIntent);

        Intent service = new Intent(context, IngredientWidgetService.class);
        service.putExtra(RECIPE_ID, prefs.getLong(RECIPE_ID, 0));
        views.setRemoteAdapter(R.id.list_ingredients, service);

        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list_ingredients);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

