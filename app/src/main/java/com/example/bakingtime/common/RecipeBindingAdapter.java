package com.example.bakingtime.common;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bakingtime.R;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeBindingAdapter {
    @BindingAdapter("android:src")
    public static void loadImage(ImageView imageView, String imageUrl) {
        RequestOptions options = new RequestOptions().fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }
}
