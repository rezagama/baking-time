package com.example.bakingtime.common;

import android.content.Context;

import com.example.bakingtime.database.DBManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rezagama on 9/3/17.
 */

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Context providesApplicationContext(){
        return context;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public DBManager providesDBManager(Context context){
        return new DBManager(context);
    }
}
