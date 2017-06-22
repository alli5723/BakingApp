package com.omo_lanke.android.bakingapp.data;

import java.util.ArrayList;

/**
 * Created by omo_lanke on 15/06/2017.
 */

public class Recipe {
    int id;
    String name;
    ArrayList<Ingredient> ingredients;
    ArrayList<Step> steps;
    int servings;
    String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
