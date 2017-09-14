package com.example.bakingtime.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeContract {
    public static final String CONTENT_AUTHORITY = "com.example.bakingtime.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class RecipeEntry implements BaseColumns {
        public static final String RECIPE_TABLE = "recipe";

        public static final String _ID = "_id";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_RECIPE_NAME = "recipe_name";
        public static final String COLUMN_RECIPE_SERVINGS = "recipe_servings";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(RECIPE_TABLE).build();
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + RECIPE_TABLE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + RECIPE_TABLE;

        public static Uri buildFlavorsUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class IngredientsEntry implements BaseColumns {
        public static final String INGREDIENTS_TABLE = "ingredients";

        public static final String _ID = "_id";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_INGREDIENTS_NAME = "ingredient_name";
        public static final String COLUMN_INGREDIENTS_QUANTITY = "ingredient_quantity";
        public static final String COLUMN_INGREDIENTS_MEASURE = "ingredient_measure";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(INGREDIENTS_TABLE).build();
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + INGREDIENTS_TABLE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + INGREDIENTS_TABLE;

        public static Uri buildFlavorsUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
