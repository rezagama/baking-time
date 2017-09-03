package com.example.bakingtime.network;

import android.content.Context;

import com.example.bakingtime.services.BakingService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rezagama on 9/3/17.
 */

@Module
public class NetworkModule {
    private String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkConfig providesNetworkConfig(){
        return new NetworkConfig(baseUrl);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Gson providesGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).
                registerTypeAdapter(Date.class, new DateTypeAdapter()).create();
    }
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public HttpLoggingInterceptor providesLoggingInterceptor(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public OkHttpClient providesOkHttpClient3(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Retrofit providesRetrofit(OkHttpClient okHttpClient,
                                     NetworkConfig networkConfig,
                                     Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(networkConfig.baseUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkService providesNetworkService(Retrofit retrofit){
        return retrofit.create(NetworkService.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public BakingService providesMovieService(NetworkService service){
        return new BakingService(service);
    }
}
