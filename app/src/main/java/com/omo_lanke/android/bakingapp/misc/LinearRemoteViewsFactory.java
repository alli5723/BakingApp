package com.omo_lanke.android.bakingapp.misc;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.omo_lanke.android.bakingapp.R;
import com.omo_lanke.android.bakingapp.data.Ingredient;
import com.omo_lanke.android.bakingapp.utils.StoreRecipe;

import java.util.ArrayList;

/**
 * Created by omo_lanke on 22/06/2017.
 */

public class LinearRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    ArrayList<Ingredient> ingredients = new ArrayList<>();

    public LinearRemoteViewsFactory(Context applicationContext){
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        StoreRecipe storage = new StoreRecipe(mContext);
        ingredients = storage.getIngredients();
    }

    @Override
    public void onDataSetChanged() {
        Log.i("Widget", "onDataSetChanged: data changed");
        StoreRecipe storage = new StoreRecipe(mContext);
        ingredients = storage.getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients == null) return 0;
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (ingredients == null || ingredients.size() == 0) return null;

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_app_widget);

        double quantity = ingredients.get(position).getQuantity();
        String measure = ingredients.get(position).getMeasure();
        String ingredient = ingredients.get(position).getIngredient();
        ingredient += " ("+measure+" "+quantity+")";
        views.setTextViewText(R.id.appwidget, ingredient);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
