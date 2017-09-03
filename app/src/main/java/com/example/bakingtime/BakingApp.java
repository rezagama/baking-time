package com.example.bakingtime;

import android.support.multidex.MultiDexApplication;

import com.example.bakingtime.common.AppModule;
import com.example.bakingtime.deps.BakingDeps;
import com.example.bakingtime.deps.BakingDepsProvider;
import com.example.bakingtime.deps.DaggerBakingDeps;
import com.example.bakingtime.network.NetworkModule;

/**
 * Created by rezagama on 9/3/17.
 */

public class BakingApp extends MultiDexApplication implements BakingDepsProvider {
    private BakingDeps bakingDeps;

    @Override
    public void onCreate() {
        super.onCreate();
        bakingDeps = DaggerBakingDeps.builder().appModule(new AppModule(this))
                .networkModule(new NetworkModule(getString(R.string.base_url)))
                .build();
        bakingDeps.inject(this);
    }

    @Override
    public BakingDeps provideAppDependencies() {
        return bakingDeps;
    }
}
