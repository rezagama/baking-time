package com.example.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rezagama on 9/3/17.
 */

public class Step implements Parcelable {
    public int id;
    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnailURL;

    public Step() {}

    protected Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(shortDescription);
        out.writeString(description);
        out.writeString(videoURL);
        out.writeString(thumbnailURL);
    }

    public static Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
