package com.omo_lanke.android.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omo_lanke.android.bakingapp.R;
import com.omo_lanke.android.bakingapp.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by omo_lanke on 16/06/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder>{
    private Context context;
    private List<Recipe> recipes;

    final private RecipeAdapterOnClickHandler clickHandler;

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        int layoutIdForList = R.layout.recipe_item;
        View view = inflater.inflate(layoutIdForList, parent, shouldAttachToParentImmediately);
        view.setFocusable(true);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.textViewRecipeServing.setText("Servings: " + recipe.getServings());
        holder.textViewRecipeName.setText(recipe.getName());

        try {
            if (!(recipe.getImage()).isEmpty()) {
                Picasso.with(context).load(recipe.getImage())
                        .placeholder(R.drawable.recipe)
                        .error(R.drawable.recipe)
                        .into(holder.imageViewRecipe);
            }
        }catch (Exception ex){
            
        }
    }

    @Override
    public int getItemCount() {
        if (null == recipes) return 0;
        return recipes.size();
    }

    public void resetData(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public interface RecipeAdapterOnClickHandler{
        void onClick(int index, Recipe recipe);
    }

    public RecipeAdapter(@NonNull Context context, RecipeAdapterOnClickHandler clickHandler){
        this.context = context;
        this.clickHandler = clickHandler;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewRecipeName;
        public TextView textViewRecipeServing;
        public ImageView imageViewRecipe;
        RecipeAdapterViewHolder(View view){
            super(view);
            textViewRecipeName = (TextView)view.findViewById(R.id.textViewRecipeName);
            textViewRecipeServing = (TextView)view.findViewById(R.id.textViewRecipeServing);
            imageViewRecipe = (ImageView)view.findViewById(R.id.imageViewRecipe);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            clickHandler.onClick(pos, recipes.get(pos));
        }
    }

}
