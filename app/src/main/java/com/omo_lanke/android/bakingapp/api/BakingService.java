package com.omo_lanke.android.bakingapp.api;

import com.omo_lanke.android.bakingapp.utils.AppConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by omo_lanke on 14/06/2017.
 */

public class BakingService {
    private Endpoints endpoints;
    public BakingService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        endpoints = retrofit.create(Endpoints.class);
    }

    public Endpoints endpoints(){
        return endpoints;
    }
}
