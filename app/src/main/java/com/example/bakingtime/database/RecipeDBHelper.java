package com.example.bakingtime.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeDBHelper extends SQLiteOpenHelper {
    public static final String DELETE_FROM_SEQUENCE_QUERY = "DELETE FROM SQLITE_SEQUENCE WHERE NAME = ";
    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS ";
    private static final String DATABASE_NAME = "baking.db";
    private static final int DATABASE_VERSION = 1;

    public RecipeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_RECIPE_TABLE = "CREATE TABLE " +
                RecipeContract.RecipeEntry.RECIPE_TABLE + "(" +
               RecipeContract.RecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
               RecipeContract.RecipeEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
               RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
               RecipeContract.RecipeEntry.COLUMN_RECIPE_SERVINGS + " INT NOT NULL);";

        final String SQL_CREATE_INGREDIENTS_TABLE = "CREATE TABLE " +
               RecipeContract.IngredientsEntry.INGREDIENTS_TABLE + "(" +
               RecipeContract.IngredientsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
               RecipeContract.IngredientsEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
               RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_NAME + " TEXT NOT NULL, " +
               RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_QUANTITY + " FLOAT NOT NULL, " +
               RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_MEASURE + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_RECIPE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY + RecipeContract.RecipeEntry.RECIPE_TABLE);
        sqLiteDatabase.execSQL(DELETE_FROM_SEQUENCE_QUERY + "'" + RecipeContract.RecipeEntry.RECIPE_TABLE + "'");
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY + RecipeContract.IngredientsEntry.INGREDIENTS_TABLE);
        sqLiteDatabase.execSQL(DELETE_FROM_SEQUENCE_QUERY + "'" + RecipeContract.IngredientsEntry.INGREDIENTS_TABLE + "'");

        onCreate(sqLiteDatabase);
    }
}
