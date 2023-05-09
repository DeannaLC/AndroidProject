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
import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;

/**
 * Test for ResultScreenFragment
 */
@RunWith(AndroidJUnit4.class)
public class ResultScreenTest {

    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Checks display upon game end
     */
    @Test
    public void testResults(){
        activityRule.getScenario().onActivity(activity -> {
            activity.testMode = true;
        });

        VoteTest voteTest = new VoteTest();
        voteTest.doVotes2();

        Espresso.onView(ViewMatchers.withId(2023)).perform(ViewActions.click());

        Matcher<View> matcher1 = ViewMatchers.withId(R.id.moneyStolen);
        ViewInteraction moneyVi = Espresso.onView(matcher1);
        Matcher<View> matcher2 = ViewMatchers.withId(R.id.winText);
        ViewInteraction winVi = Espresso.onView(matcher2);

        moneyVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Money stolen:")));
        winVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Cowboys win!")));
    }
}
