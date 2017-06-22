package com.omo_lanke.android.bakingapp.api;

import com.omo_lanke.android.bakingapp.data.Ingredient;
import com.omo_lanke.android.bakingapp.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by omo_lanke on 14/06/2017.
 */

public interface Endpoints {
    @GET("/android-baking-app-json")
    Call<List<Recipe>> getRecipe();
}
