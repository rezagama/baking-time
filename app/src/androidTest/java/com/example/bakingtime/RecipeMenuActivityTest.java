package com.example.bakingtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingtime.details.RecipeDetailActivity;
import com.example.bakingtime.details.ingredients.IngredientsActivity;
import com.example.bakingtime.menu.RecipeMenuActivity;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.util.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Instrumentation.ActivityResult;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.bakingtime.details.RecipeDetailActivity.VIEW_POSITION;
import static com.example.bakingtime.home.RecipeHomeActivity.RECIPE_DATA;
import static com.example.bakingtime.menu.RecipeMenuAdapter.VIEW_TYPE_INGREDIENTS;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by rezagama on 9/14/17.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeMenuActivityTest {
    private Context context;
    private Recipe recipe;

    @Rule
    public ActivityTestRule<RecipeMenuActivity> mActivityRule = new ActivityTestRule<>(RecipeMenuActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        Intents.init();
        context = InstrumentationRegistry.getTargetContext();
        recipe = TestUtils.buildRecipeData();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void testShouldOpenIngredientsDetailPage() {
        Intent intent = new Intent();
        intent.putExtra(RECIPE_DATA, recipe);
        intent.putExtra(VIEW_POSITION, VIEW_TYPE_INGREDIENTS);

        ActivityResult result = new ActivityResult(Activity.RESULT_OK, intent);
        intending(allOf(hasComponent(IngredientsActivity.class.getName()),
                hasExtraWithKey(RECIPE_DATA), hasExtraWithKey(VIEW_POSITION))).respondWith(result);

        mActivityRule.launchActivity(intent);
        onView(withText(context.getString(R.string.text_recipe_ingredients))).perform(click());

        intended(allOf(hasComponent(IngredientsActivity.class.getName()),
                hasExtraWithKey(RECIPE_DATA), hasExtraWithKey(VIEW_POSITION)));
    }

    @Test
    public void testShouldOpenStepsDetailPage() {
        Step step = recipe.steps.get(0);

        Intent intent = new Intent();
        intent.putExtra(RECIPE_DATA, recipe);
        intent.putExtra(VIEW_POSITION, VIEW_TYPE_INGREDIENTS);

        ActivityResult result = new ActivityResult(Activity.RESULT_OK, intent);
        intending(allOf(hasComponent(RecipeDetailActivity.class.getName()),
                hasExtraWithKey(RECIPE_DATA), hasExtraWithKey(VIEW_POSITION))).respondWith(result);

        mActivityRule.launchActivity(intent);
        onView(withText(step.shortDescription)).perform(click());

        intended(allOf(hasComponent(RecipeDetailActivity.class.getName()),
                hasExtraWithKey(RECIPE_DATA), hasExtraWithKey(VIEW_POSITION)));
    }
}
