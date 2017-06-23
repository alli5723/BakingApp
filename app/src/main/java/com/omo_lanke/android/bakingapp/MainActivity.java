package com.omo_lanke.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.omo_lanke.android.bakingapp.adapters.RecipeAdapter;
import com.omo_lanke.android.bakingapp.api.BakingService;
import com.omo_lanke.android.bakingapp.data.Recipe;
import com.omo_lanke.android.bakingapp.utils.StoreRecipe;

import java.util.ArrayList;
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

    ArrayList<Recipe> recipes;
    public static Recipe selectedRecipe = null;

    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    private static final String BUNDLE_RECIPE_LIST = "recipe_list";

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

        if (!isNetworkOnline()){
            final Snackbar snackBar = Snackbar.make(progress,
                    "Seems you are not connected to the Internet.",
                    Snackbar.LENGTH_INDEFINITE);

            snackBar.setAction("Settings", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    snackBar.dismiss();
                    refreshButton();
                }
            });
            snackBar.setActionTextColor(getResources().getColor(R.color.colorPrimaryLighter));
            snackBar.show();
            return;
        }
        if (savedInstanceState != null) {
            //Restore from Saved instance if it exists
            recipes = savedInstanceState.getParcelableArrayList(BUNDLE_RECIPE_LIST);
            recipeAdapter.resetData(recipes);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            recipeList.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);

        }else{
            apiCall();
        }
    }

    public void apiCall(){
        progress.setVisibility(View.VISIBLE);
        Call<List<Recipe>> recipeCall = new BakingService().endpoints().getRecipe();

        recipeCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = new ArrayList<Recipe>(response.body());
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
                refreshButton();
            }
        });

    }

    public void refreshButton(){
        final Snackbar snackBar = Snackbar.make(progress,
                "Unable to fetch recipes, please try again.",
                Snackbar.LENGTH_INDEFINITE);

        snackBar.setAction("Refresh", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCall();
                snackBar.dismiss();
            }
        });
        snackBar.setActionTextColor(getResources().getColor(R.color.colorPrimaryLighter));
        snackBar.show();
    }

    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recipeList.getLayoutManager().onSaveInstanceState());

        outState.putParcelableArrayList(BUNDLE_RECIPE_LIST,recipes);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(int index, Recipe recipe) {
        //Open the ingredients and steps page
        selectedRecipe = recipe;
        StoreRecipe storage = new StoreRecipe(context);
        storage.setIngredients(recipe.getIngredients());
        AppWidgetManager appWidgetManager =
                AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, MainActivity.class)
        );
        appWidgetManager.notifyAppWidgetViewDataChanged(
                appWidgetIds, R.id.widget_listview
        );
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }
}
