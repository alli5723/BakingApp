package com.omo_lanke.android.bakingapp;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class BakingAppViewTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource(){
        idlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void recipe_item_click() {
        ViewInteraction recipeView = onView(
                withId(R.id.recipeList));
        recipeView.perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
    }

    @Test
    public void step_item_click() {

        ViewInteraction recipeView = onView(
                withId(R.id.recipeList));
        recipeView.perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));

        ViewInteraction stepsView = onView(
                withId(R.id.stepsList));
        stepsView.perform(actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.playerView), isDisplayed()));
    }

    @Test
    public void next_button_click() {

        ViewInteraction recipeView = onView(
                withId(R.id.recipeList));
        recipeView.perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));

        ViewInteraction stepsView = onView(
                withId(R.id.stepsList));
        stepsView.perform(actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.playerView), isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.buttonNext), withText("NEXT"), isDisplayed()));
        button.perform(click());
    }

    @Test
    public void previous_button_click() {

        ViewInteraction recipeView = onView(
                withId(R.id.recipeList));
        recipeView.perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));

        ViewInteraction stepsView = onView(
                withId(R.id.stepsList));
        stepsView.perform(actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.playerView), isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.buttonPrevious), withText("PREVIOUS"), isDisplayed()));
        button.perform(click());
    }

    @Test
    public void up_button_click(){
        ViewInteraction recipeView = onView(
                withId(R.id.recipeList));
        recipeView.perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));

        ViewInteraction stepsView = onView(
                withId(R.id.stepsList));
        stepsView.perform(actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.playerView), isDisplayed()));

        ViewInteraction upButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        upButton.perform(click());
    }

    @After
    public void unregisteredIdlingResource(){
        if (idlingResource != null){
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }

//    @Test
//    public void mainActivityTest() {
//
//        ViewInteraction recyclerView2 = onView(
//                withId(R.id.stepsList));
//        recyclerView2.perform(actionOnItemAtPosition(0, click()));
//
//        // Added a sleep statement to match the app's execution delay.
//        // The recommended way to handle such scenarios is to use Espresso idling resources:
//        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ViewInteraction recyclerView3 = onView(
//                withId(R.id.stepsList));
//        recyclerView3.perform(actionOnItemAtPosition(1, click()));
//
//        // Added a sleep statement to match the app's execution delay.
//        // The recommended way to handle such scenarios is to use Espresso idling resources:
//        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ViewInteraction recyclerView4 = onView(
//                withId(R.id.stepsList));
//        recyclerView4.perform(actionOnItemAtPosition(2, click()));
//
//        // Added a sleep statement to match the app's execution delay.
//        // The recommended way to handle such scenarios is to use Espresso idling resources:
//        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ViewInteraction recyclerView5 = onView(
//                withId(R.id.stepsList));
//        recyclerView5.perform(actionOnItemAtPosition(3, click()));
//
//        // Added a sleep statement to match the app's execution delay.
//        // The recommended way to handle such scenarios is to use Espresso idling resources:
//        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ViewInteraction recyclerView6 = onView(
//                withId(R.id.stepsList));
//        recyclerView6.perform(actionOnItemAtPosition(4, click()));
//
//        // Added a sleep statement to match the app's execution delay.
//        // The recommended way to handle such scenarios is to use Espresso idling resources:
//        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ViewInteraction recyclerView7 = onView(
//                withId(R.id.stepsList));
//        recyclerView7.perform(actionOnItemAtPosition(5, click()));
//
//        // Added a sleep statement to match the app's execution delay.
//        // The recommended way to handle such scenarios is to use Espresso idling resources:
//        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ViewInteraction recyclerView8 = onView(
//                withId(R.id.stepsList));
//        recyclerView8.perform(actionOnItemAtPosition(6, click()));
//
//        // Added a sleep statement to match the app's execution delay.
//        // The recommended way to handle such scenarios is to use Espresso idling resources:
//        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ViewInteraction appCompatButton = onView(
//                allOf(withId(R.id.buttonNext), withText("NEXT"), isDisplayed()));
//        appCompatButton.perform(click());
//
//        ViewInteraction appCompatCheckBox = onView(
//                allOf(withId(R.id.ingredientCheckBox), withText("Graham Cracker crumbs (2.0 CUP)"), isDisplayed()));
//        appCompatCheckBox.perform(click());
//
//        ViewInteraction appCompatCheckBox2 = onView(
//                allOf(withId(R.id.ingredientCheckBox), withText("unsalted butter, melted (6.0 TBLSP)"), isDisplayed()));
//        appCompatCheckBox2.perform(click());
//
//        ViewInteraction appCompatCheckBox3 = onView(
//                allOf(withId(R.id.ingredientCheckBox), withText("granulated sugar (0.5 CUP)"), isDisplayed()));
//        appCompatCheckBox3.perform(click());
//
//        ViewInteraction appCompatCheckBox4 = onView(
//                allOf(withId(R.id.ingredientCheckBox), withText("salt (1.5 TSP)"), isDisplayed()));
//        appCompatCheckBox4.perform(click());
//
//        ViewInteraction appCompatCheckBox5 = onView(
//                allOf(withId(R.id.ingredientCheckBox), withText("vanilla (5.0 TBLSP)"), isDisplayed()));
//        appCompatCheckBox5.perform(click());
//
//        ViewInteraction appCompatCheckBox6 = onView(
//                allOf(withId(R.id.ingredientCheckBox), withText("Nutella or other chocolate-hazelnut spread (1.0 K)"), isDisplayed()));
//        appCompatCheckBox6.perform(click());
//
//        ViewInteraction appCompatCheckBox7 = onView(
//                allOf(withId(R.id.ingredientCheckBox), withText("Mascapone Cheese(room temperature) (500.0 G)"), isDisplayed()));
//        appCompatCheckBox7.perform(click());
//
//        ViewInteraction appCompatCheckBox8 = onView(
//                allOf(withId(R.id.ingredientCheckBox), withText("heavy cream(cold) (1.0 CUP)"), isDisplayed()));
//        appCompatCheckBox8.perform(click());
//
//        ViewInteraction appCompatCheckBox9 = onView(
//                allOf(withId(R.id.ingredientCheckBox), withText("cream cheese(softened) (4.0 OZ)"), isDisplayed()));
//        appCompatCheckBox9.perform(click());
//
//        ViewInteraction appCompatButton2 = onView(
//                allOf(withId(R.id.buttonPrevious), withText("PREVIOUS"), isDisplayed()));
//        appCompatButton2.perform(click());
//
//        ViewInteraction appCompatImageButton = onView(
//                allOf(withContentDescription("Navigate up"),
//                        withParent(allOf(withId(R.id.action_bar),
//                                withParent(withId(R.id.action_bar_container)))),
//                        isDisplayed()));
//        appCompatImageButton.perform(click());
//
//        ViewInteraction recyclerView9 = onView(
//                allOf(withId(R.id.recipeList), isDisplayed()));
//        recyclerView9.perform(actionOnItemAtPosition(1, click()));
//
//    }

}
