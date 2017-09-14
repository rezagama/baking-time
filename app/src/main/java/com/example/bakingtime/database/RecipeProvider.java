package com.example.bakingtime.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private RecipeDBHelper recipeDBHelper;

    private static final int RECIPE_URI = 0;
    private static final int RECIPE_WITH_ID = 1;
    private static final int INGREDIENTS_URI = 2;
    private static final int INGREDIENTS_WITH_ID = 3;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RecipeContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, RecipeContract.RecipeEntry.RECIPE_TABLE, RECIPE_URI);
        matcher.addURI(authority, RecipeContract.RecipeEntry.RECIPE_TABLE + "/#", RECIPE_WITH_ID);
        matcher.addURI(authority, RecipeContract.IngredientsEntry.INGREDIENTS_TABLE, INGREDIENTS_URI);
        matcher.addURI(authority, RecipeContract.IngredientsEntry.INGREDIENTS_TABLE + "/#", INGREDIENTS_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate(){
        recipeDBHelper = new RecipeDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri){
        final int match = sUriMatcher.match(uri);

        switch (match){
            case RECIPE_URI:
                return RecipeContract.RecipeEntry.CONTENT_DIR_TYPE;
            case INGREDIENTS_WITH_ID:
                return RecipeContract.IngredientsEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case RECIPE_URI:
                cursor = recipeDBHelper.getReadableDatabase().query(
                        RecipeContract.RecipeEntry.RECIPE_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return cursor;
            case RECIPE_WITH_ID:
                cursor = recipeDBHelper.getReadableDatabase().query(
                        RecipeContract.RecipeEntry.RECIPE_TABLE,
                        projection,
                        RecipeContract.IngredientsEntry.COLUMN_RECIPE_ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return cursor;
            case INGREDIENTS_WITH_ID:
                String selectionQuery = getSelectionQuery(selectionArgs);
                String[] selectionArguments = getSelectionArguments(uri, selectionArgs);
                cursor = recipeDBHelper.getReadableDatabase().query(
                        RecipeContract.IngredientsEntry.INGREDIENTS_TABLE,
                        projection,
                        selectionQuery,
                        selectionArguments,
                        null,
                        null,
                        sortOrder);
                return cursor;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    private String[] getSelectionArguments(Uri uri, String[] selectionArgs) {
        if (selectionArgs.length > 1) {
            return new String[] {String.valueOf(ContentUris.parseId(uri)), selectionArgs[1]};
        } else {
            return new String[] {String.valueOf(ContentUris.parseId(uri))};
        }
    }

    private String getSelectionQuery(String[] selectionArgs) {
        if (selectionArgs.length > 1) {
            return RecipeContract.IngredientsEntry.COLUMN_RECIPE_ID + " = ? AND " +
                    RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_NAME + " = ?";
        } else {
            return RecipeContract.IngredientsEntry.COLUMN_RECIPE_ID + " = ?";
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values){
        Uri insertUri;
        final SQLiteDatabase db = recipeDBHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case RECIPE_URI: {
                long _id = db.insert(RecipeContract.RecipeEntry.RECIPE_TABLE, null, values);
                if (_id > 0) {
                    insertUri = RecipeContract.RecipeEntry.buildFlavorsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }
            case INGREDIENTS_URI: {
                long _id = db.insert(RecipeContract.IngredientsEntry.INGREDIENTS_TABLE, null, values);
                if (_id > 0) {
                    insertUri = RecipeContract.IngredientsEntry.buildFlavorsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return insertUri;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

//
//    public void addMovieToFavorite(){
//        ContentValues data = new ContentValues();
//        data.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.id);
//        data.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, movie.title);
//        data.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.releaseDate);
//        data.put(MovieContract.MovieEntry.COLUMN_MOVIE_AVERAGE_VOTE, movie.voteAverage);
//        data.put(MovieContract.MovieEntry.COLUMN_MOVIE_SYNOPSIS, movie.overview);
//
//        getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, data);
//        Toast.makeText(this, getString(R.string.text_added_to_favorites), Toast.LENGTH_SHORT).show();
//        setFavoriteButton();
//    }
}
