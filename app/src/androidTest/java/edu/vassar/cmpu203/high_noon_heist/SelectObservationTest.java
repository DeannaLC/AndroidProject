package edu.vassar.cmpu203.high_noon_heist;

import android.view.View;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;

/**
 * Tests for ViewObservationFragment
 */
@RunWith(AndroidJUnit4.class)
public class SelectObservationTest {


    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Makes a player check their observation
     *
     * @param name of player seeing an observation
     * @param vi view interaction being checked
     */
    public void doObservation(String name, ViewInteraction vi){
        Espresso.onView(ViewMatchers.withText(name)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.confirmActionPlayer)).perform(ViewActions.click());
        try {
            Espresso.onView(ViewMatchers.withText("Number")).perform(ViewActions.click());
            vi.check(ViewAssertions.matches(ViewMatchers.withSubstring("You saw")));
            vi.check(ViewAssertions.matches(ViewMatchers.withSubstring("total at the bank")));
        }
        catch(NoMatchingViewException ignore){
            vi.check(ViewAssertions.matches(ViewMatchers.withSubstring("You hung out at the bank for the night")));
        }
        Espresso.onView(ViewMatchers.withText("Confirm")).perform(ViewActions.click());
    }

    /**
     * Test for player observations
     */
    @Test
    public void testSelectObservation(){
        activityRule.getScenario().onActivity(activity -> {
            activity.testMode = true;
        });

        this.selectObservation();
    }

    /**
     * Selects players and checks their observations
     */
    public void selectObservation(){
        SelectActionTest selectAct = new SelectActionTest();
        selectAct.actionSelect();

        Matcher<View> matcher1 = ViewMatchers.withId(R.id.selectText);
        ViewInteraction selectVi = Espresso.onView(matcher1);
        Matcher<View> matcher2 = ViewMatchers.withId(R.id.viewPrompt);
        ViewInteraction promptVi = Espresso.onView(matcher2);

        selectVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Choose a player to view Action results")));

        this.doObservation("Joe", promptVi);
        this.doObservation("Jack", promptVi);
        this.doObservation("Jeb", promptVi);
        this.doObservation("Jebediah", promptVi);
        this.doObservation("Phil", promptVi);
    }

    /**
     * Test for observations after a player is removed
     */
    @Test
    public void testSelectObservation2() {
        activityRule.getScenario().onActivity(activity -> {
            activity.testMode = true;
        });

        this.selectObservation2();
    }

    /**
     * Selects players and checks their observations
     */
    public void selectObservation2(){
        SelectActionTest selectAct = new SelectActionTest();
        selectAct.actionSelect2();

        Matcher<View> matcher1 = ViewMatchers.withId(R.id.selectText);
        ViewInteraction selectVi = Espresso.onView(matcher1);
        Matcher<View> matcher2 = ViewMatchers.withId(R.id.viewPrompt);
        ViewInteraction promptVi = Espresso.onView(matcher2);

        selectVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Choose a player to view Action results")));

        this.doObservation("Jack", promptVi);
        this.doObservation("Jeb", promptVi);
        this.doObservation("Jebediah", promptVi);
        this.doObservation("Phil", promptVi);
    }
}
