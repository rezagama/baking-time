package com.example.bakingtime.network;

import com.example.bakingtime.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by rezagama on 9/3/17.
 */

public interface NetworkService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Observable<List<Recipe>> getRecipeList();
}
