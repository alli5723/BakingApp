package com.omo_lanke.android.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.omo_lanke.android.bakingapp.data.Ingredient;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by omo_lanke on 23/06/2017.
 */

public class StoreRecipe {
    private SharedPreferences sharedPreferences;
    private Context mContext;

    public StoreRecipe(Context context){
        mContext = context;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ingredients);
        editor.putString("ingredients", json);
        editor.apply();
    }

    public ArrayList<Ingredient> getIngredients(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("ingredients", null);
        Type type = new TypeToken<ArrayList<Ingredient>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
