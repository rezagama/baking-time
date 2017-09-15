package com.example.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingtime.details.RecipeDetailActivity;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.util.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.bakingtime.home.RecipeHomeActivity.RECIPE_DATA;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by rezagama on 9/15/17.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {
    private Context context;
    private Recipe recipe;

    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityRule = new ActivityTestRule<>(RecipeDetailActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        recipe = TestUtils.buildRecipeData();
    }

    @Test
    public void testShouldSwipeToDisplayNextRecipeStep() {
        Step step = recipe.steps.get(1);

        Intent intent = new Intent();
        intent.putExtra(RECIPE_DATA, recipe);

        mActivityRule.launchActivity(intent);
        onView(withId(R.id.view_pager_steps)).perform(swipeLeft());

        onView(allOf(withId(R.id.text_step_title), withText(step.shortDescription))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.text_step_description), withText(step.description))).check(matches(isDisplayed()));
    }

    @Test
    public void testShouldDisplayVideoPlayer() {
        Intent intent = new Intent();
        intent.putExtra(RECIPE_DATA, recipe);

        mActivityRule.launchActivity(intent);
        onView(allOf(withId(R.id.media_player), isDisplayed()));
        onView(allOf(withId(R.id.text_video_not_available), not(isDisplayed())));
    }

    @Test
    public void testShouldDisplayNoVideoAvailable() {
        Intent intent = new Intent();
        intent.putExtra(RECIPE_DATA, recipe);

        mActivityRule.launchActivity(intent);
        onView(withId(R.id.view_pager_steps)).perform(swipeLeft());

        onView(allOf(withId(R.id.media_player), not(isDisplayed())));
        onView(allOf(withId(R.id.text_video_not_available), isDisplayed()))
                .check(matches(withText(context.getString(R.string.text_no_video_available))));
    }
}
