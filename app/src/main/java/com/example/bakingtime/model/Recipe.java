package com.example.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rezagama on 9/3/17.
 */

public class Recipe implements Parcelable {
    public int id;
    public String name;
    public List<Ingredient> ingredients = new ArrayList<>();
    public List<Step> steps = new ArrayList<>();
    public int servings;
    public String image;

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        in.readList(ingredients, Ingredient.class.getClassLoader());
        in.readList(steps, Step.class.getClassLoader());
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(name);
        out.writeList(ingredients);
        out.writeList(steps);
        out.writeInt(servings);
        out.writeString(image);
    }

    public static Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
