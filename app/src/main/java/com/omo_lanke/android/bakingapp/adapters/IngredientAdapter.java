package com.omo_lanke.android.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.omo_lanke.android.bakingapp.R;
import com.omo_lanke.android.bakingapp.data.Ingredient;

import java.util.List;

/**
 * Created by omo_lanke on 20/06/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder>{
    private Context context;
    private List<Ingredient> ingredients;

    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        int layoutIdForList = R.layout.ingredient_item;
        View view = inflater.inflate(layoutIdForList, parent, shouldAttachToParentImmediately);
        view.setFocusable(true);
        return new IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapterViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.textView.setText(ingredient.getIngredient() +
                " ("+ingredient.getQuantity() + " " + ingredient.getMeasure()+")");

    }

    @Override
    public int getItemCount() {
        if (null == ingredients) return 0;
        return ingredients.size();
    }

    public IngredientAdapter(@NonNull Context context, List<Ingredient> ingredients){
        this.context = context;
        this.ingredients = ingredients;
    }

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        IngredientAdapterViewHolder(View view){
            super(view);
            textView = (TextView)view.findViewById(R.id.ingredientCheckBox);
        }
    }

}
