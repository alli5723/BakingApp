package com.omo_lanke.android.bakingapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omo_lanke.android.bakingapp.R;
import com.omo_lanke.android.bakingapp.adapters.IngredientAdapter;
import com.omo_lanke.android.bakingapp.adapters.StepAdapter;
import com.omo_lanke.android.bakingapp.data.Ingredient;
import com.omo_lanke.android.bakingapp.data.Step;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsFragment extends Fragment implements StepAdapter.StepAdapterOnClickHandler{
    private static final String ARG_ING = "ingredients";
    private static final String ARG_STE = "step";

    private ArrayList<Ingredient> ingredients = null;
    private ArrayList<Step> steps = null;

    StepAdapter stepAdapter;
    IngredientAdapter ingredientAdapter;

    OnStepClickListener stepCallback;

    public interface OnStepClickListener{
        void onStepSelected(int position, Step step);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            stepCallback = (OnStepClickListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnStepListener");
        }
    }

    public StepsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ingredients Parameter 1.
     * @param steps Parameter 2.
     * @return A new instance of fragment StepsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepsFragment newInstance(ArrayList<Ingredient> ingredients,
                                            ArrayList<Step> steps) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ING, ingredients);
        args.putParcelableArrayList(ARG_STE, steps);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                ingredients = getArguments().getParcelableArrayList(ARG_ING);
                steps = getArguments().getParcelableArrayList(ARG_STE);
            }catch (Exception ex){
                //no arraylist has been sent.
            }
        }
        stepAdapter = new StepAdapter(getContext(), this, steps);
        ingredientAdapter = new IngredientAdapter(getContext(), ingredients);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        RecyclerView ingredientsList = (RecyclerView)view.findViewById(R.id.ingredientsList);
        RecyclerView stepsList = (RecyclerView)view.findViewById(R.id.stepsList);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ingredientsList.setLayoutManager(layoutManager);
        ingredientsList.setHasFixedSize(true);
        ingredientsList.setAdapter(ingredientAdapter);

        final LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getContext());
        stepLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stepsList.setLayoutManager(stepLayoutManager);
        stepsList.setHasFixedSize(true);
        stepsList.setAdapter(stepAdapter);

        return view;
    }

    @Override
    public void onClick(int index, Step step) {
        stepCallback.onStepSelected(index, step);
    }
}
