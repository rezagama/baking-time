package com.example.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rezagama on 9/3/17.
 */

public class Ingredient implements Parcelable {
    public float quantity;
    public String measure;
    public String ingredient;

    public Ingredient() {}

    protected Ingredient(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeFloat(quantity);
        out.writeString(measure);
        out.writeString(ingredient);
    }

    public static Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
