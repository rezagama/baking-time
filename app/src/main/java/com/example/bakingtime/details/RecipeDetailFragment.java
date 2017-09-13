package com.example.bakingtime.details;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.FragmentRecipeStepsBinding;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.player.MediaPlayerFragment;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeDetailFragment extends Fragment {
    public static final String MEDIA_FULL_SCREEN_TAG = "media_full_screen";
    public static final String RECIPE_STEPS_DATA = "recipe_step";

    private MediaPlayerFragment mediaPlayer;
    private FragmentRecipeStepsBinding binding;
    private Step step;

    public static RecipeDetailFragment newInstance(Step step) {
        Bundle args = new Bundle();
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        args.putParcelable(RECIPE_STEPS_DATA, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_steps, container, false);
        step = getArguments().getParcelable(RECIPE_STEPS_DATA);
        mediaPlayer = MediaPlayerFragment.newInstance(step);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setTitle(step.shortDescription);
        binding.setStepDescription(step.description);
        initMediaPlayerView();
    }

    private void initMediaPlayerView() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT || isTabletDevice()) {
            closeMediaPlayerFullScreen();
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.container_media_player, mediaPlayer)
                    .commit();
            binding.containerMediaPlayer.setVisibility(View.VISIBLE);
        } else {
            binding.containerMediaPlayer.setVisibility(View.GONE);
        }
    }

    private void closeMediaPlayerFullScreen() {
        getActivity().getSupportFragmentManager().popBackStack(MEDIA_FULL_SCREEN_TAG,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private boolean isTabletDevice() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
