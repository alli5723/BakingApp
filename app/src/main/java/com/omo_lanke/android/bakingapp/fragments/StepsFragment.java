package com.omo_lanke.android.bakingapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.omo_lanke.android.bakingapp.R;
import com.omo_lanke.android.bakingapp.adapters.IngredientAdapter;
import com.omo_lanke.android.bakingapp.adapters.StepAdapter;
import com.omo_lanke.android.bakingapp.data.Ingredient;
import com.omo_lanke.android.bakingapp.data.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    private static final String BUNDLE_RECYCLER_LAYOUT2 = "classname.recycler.layout2";
    private static final String BUNDLE_INGREDIENT_LIST = "ingredient_list";
    private static final String BUNDLE_STEP_LIST = "step_list";

    StepAdapter stepAdapter;
    IngredientAdapter ingredientAdapter;
    String TAG = StepsFragment.class.getSimpleName();

    @BindView(R.id.ingredientsList)
    RecyclerView ingredientsList;

    @BindView(R.id.stepsList)
    RecyclerView stepsList;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    View view;

    OnStepClickListener stepCallback;

    public interface OnStepClickListener{
        void onStepSelected(int position, Step step);
    }

    @Override
    public void onAttach(Context context){
        Log.e(TAG, "onAttach: " );
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
        Log.e(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        if(getArguments() != null ) {
            try {
                Log.d("Saved", "onCreate: step or ingredient is null");
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
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated: " );
        super.onActivityCreated(savedInstanceState);

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

        if (savedInstanceState != null) {
            //Restore from Saved instance if it exists
            ingredients = savedInstanceState.getParcelableArrayList(BUNDLE_INGREDIENT_LIST);
            steps = savedInstanceState.getParcelableArrayList(BUNDLE_STEP_LIST);
            ingredientAdapter.resetData(ingredients);
            stepAdapter.resetData(steps);
            Log.e("Saved", "onCreateView: instance retrieved" );
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            ingredientsList.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);

            Parcelable savedRecyclerLayoutState2 = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT2);
            stepsList.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState2);

            final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
            if(position != null)
                scrollView.post(new Runnable() {
                    public void run() {
                        scrollView.scrollTo(position[0], position[1]);
                    }
                });

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, ingredientsList.getLayoutManager().onSaveInstanceState());
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT2, stepsList.getLayoutManager().onSaveInstanceState());
        outState.putIntArray("SCROLL_POSITION",
                new int[]{ scrollView.getScrollX(), scrollView.getScrollY()});

        outState.putParcelableArrayList(BUNDLE_INGREDIENT_LIST,ingredients);
        outState.putParcelableArrayList(BUNDLE_STEP_LIST,steps);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: " );
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onClick(int index, Step step) {
        stepCallback.onStepSelected(index, step);
    }
}
