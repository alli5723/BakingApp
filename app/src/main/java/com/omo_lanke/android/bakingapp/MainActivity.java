package com.omo_lanke.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.omo_lanke.android.bakingapp.adapters.RecipeAdapter;
import com.omo_lanke.android.bakingapp.api.BakingService;
import com.omo_lanke.android.bakingapp.data.Recipe;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler{

    @BindView(R.id.progress)
    ProgressBar progress;

    @BindView(R.id.recipeList)
    RecyclerView recipeList;

    public static final String TAG = MainActivity.class.getSimpleName();
    Context context;
    RecipeAdapter recipeAdapter;

    List<Recipe> recipes;
    public static Recipe selectedRecipe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        ButterKnife.bind(this);
        recipeAdapter = new RecipeAdapter(context, this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recipeList.setLayoutManager(layoutManager);
        recipeList.setHasFixedSize(true);
        recipeList.setAdapter(recipeAdapter);

        apiCall();
    }

    public void apiCall(){
        progress.setVisibility(View.VISIBLE);
        Call<List<Recipe>> recipeCall = new BakingService().endpoints().getRecipe();

        recipeCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = response.body();
                try {
                    recipeAdapter.resetData(recipes);
                }catch (Exception ex){

                }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                progress.setVisibility(View.GONE);
                t.printStackTrace();
                Snackbar.make(progress, "Unable to fetch recipes, please try again later.",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

    }

    @Override
    public void onClick(int index, Recipe recipe) {
        //Open the ingredients and steps page
        selectedRecipe = recipe;
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }
}
