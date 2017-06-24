package com.omo_lanke.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.omo_lanke.android.bakingapp.data.Recipe;
import com.omo_lanke.android.bakingapp.data.Step;
import com.omo_lanke.android.bakingapp.fragments.StepsFragment;
import com.omo_lanke.android.bakingapp.fragments.VideoFragment;

public class DetailsActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener, VideoFragment.OnVideoClickListener{

    Recipe recipe = null;
    private boolean mTwoPane;
    Step selectedStep;
    int selectedPosition = 0;

    VideoFragment videoFragment;
    Fragment stepsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recipe = MainActivity.selectedRecipe;

        getSupportActionBar().setTitle(recipe.getName());

        if (savedInstanceState != null) {
            Log.d("Saved", "onCreate: Retrieve Details Activity here");
            //Restore the fragment's instance
            stepsFragment = getSupportFragmentManager().getFragment(savedInstanceState, "steps");
        }else {

            stepsFragment = StepsFragment.
                    newInstance(recipe.getIngredients(), recipe.getSteps());//new StepsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, stepsFragment)
                    .commit();

        }

        if(findViewById(R.id.fragment_video) != null){
            mTwoPane = true;
            //Set the first video as what should be playing on the right side
            videoFragment = VideoFragment.newInstance(recipe.getSteps().get(0));

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_video, videoFragment)
                    .commit();
        }else {
            mTwoPane = false;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                    getSupportActionBar().setTitle(recipe.getName());
                }
                else super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        getSupportFragmentManager().putFragment(outState, "steps", stepsFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStepSelected(int position, Step step) {
        //change video to selected
        this.selectedStep = step;
        selectedPosition = position;
        changeVideoView(position, step, false);
    }

    public void changeVideoView(int position, Step step, boolean replace){
        if(mTwoPane){
            VideoFragment videoFragment = VideoFragment.newInstance(step);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_video, videoFragment)
                    .commit();

        }else {
            String suffix = (position == 0)? " Introduction" : " - Step " + position;
            getSupportActionBar().setTitle(recipe.getName() + suffix);
            VideoFragment videoFragment = VideoFragment.
                    newInstance(step);

            FragmentManager fragmentManager = getSupportFragmentManager();
            if (replace) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, videoFragment)
                        .commit();
            }else{
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, videoFragment)
                        .addToBackStack("Details")
                        .commit();
            }
        }
    }

    @Override
    public void onVideoButtonSelected(int button) {
        if(button == 0){//previous
            int newIndex = selectedPosition - 1;
            Log.e("Log", "onVideoButtonSelected: "+ selectedPosition + " Total is " + recipe.getSteps().size());
            if (newIndex < 0){
                //Show snackbar
                return;
            }else{
                selectedPosition = newIndex;
                selectedStep = recipe.getSteps().get(selectedPosition);
            }
        }else{//next
            int newIndex = selectedPosition + 1;
            Log.e("Log", "onVideoButtonSelected: "+ selectedPosition + " Total is " + recipe.getSteps().size());
            if (newIndex >= recipe.getSteps().size()){
                //Show snackbar out of bound
                return;
            }else{
                selectedPosition = newIndex;
                selectedStep = recipe.getSteps().get(selectedPosition);
            }
        }
        changeVideoView(selectedPosition, selectedStep, true);
    }
}
