package com.example.bakingtime.deps;

import com.example.bakingtime.BakingApp;
import com.example.bakingtime.home.RecipeHomeActivity;
import com.example.bakingtime.common.AppModule;
import com.example.bakingtime.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rezagama on 9/3/17.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class
})

public interface BakingDeps {
    void inject(BakingApp bakingApp);

    void inject(RecipeHomeActivity recipeHomeActivity);
}
