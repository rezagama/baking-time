package com.example.bakingtime.details;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.example.bakingtime.BaseActivity;
import com.example.bakingtime.R;
import com.example.bakingtime.databinding.ActivityRecipeDetailBinding;
import com.example.bakingtime.details.steps.StepViewPagerAdapter;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.player.MediaPlayerFragment;

import static com.example.bakingtime.details.RecipeDetailFragment.MEDIA_FULL_SCREEN_TAG;
import static com.example.bakingtime.home.RecipeHomeActivity.RECIPE_DATA;
import static com.example.bakingtime.menu.RecipeMenuAdapter.VIEW_TYPE_INGREDIENTS;

/**
 * Created by rezagama on 9/4/17.
 */

public class RecipeDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    public static final String VIEW_POSITION = "position";

    private ActivityRecipeDetailBinding binding;
    private StepViewPagerAdapter stepAdapter;
    private Recipe recipe;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setFullScreenMode();
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
        recipe = getIntent().getParcelableExtra(RECIPE_DATA);
        stepAdapter = new StepViewPagerAdapter(getSupportFragmentManager());
        setSelectedDetail(savedInstanceState);
        showFullScreenPlayer();
        setDetailLayout();
        initActionBar();
    }

    private void setSelectedDetail(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            position = getIntent().getIntExtra(VIEW_POSITION, 0);
        } else  {
            position = savedInstanceState.getInt(VIEW_POSITION);
        }
    }

    private void initActionBar() {
        setToolbar(getString(R.string.text_recipe_steps));
        if(isLandscapeOrientation()) hideActionBar();
    }

    private void setFullScreenMode() {
        if(isLandscapeOrientation()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void setDetailLayout() {
        for(Step step : recipe.steps) {
            stepAdapter.addPage(RecipeDetailFragment.newInstance(step), step.shortDescription);
        }
        binding.viewPagerSteps.addOnPageChangeListener(this);
        binding.viewPagerSteps.setAdapter(stepAdapter);
        binding.viewPagerSteps.setCurrentItem(position - 1);
    }

    private void showFullScreenPlayer() {
        if(isLandscapeOrientation()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, MediaPlayerFragment.newInstance(recipe.steps.get(position)))
                    .addToBackStack(MEDIA_FULL_SCREEN_TAG)
                    .commit();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.position = position;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(VIEW_POSITION, position);
    }

    @Override
    public void onBackPressed() {
        if (isLandscapeOrientation()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            super.onBackPressed();
        }
    }
}
