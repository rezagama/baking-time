package com.example.bakingtime.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeDetailFragment extends Fragment {
    public static RecipeDetailFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
