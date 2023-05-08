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

@RunWith(AndroidJUnit4.class)
public class VoteTest {


    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testVotes(){
        activityRule.getScenario().onActivity(activity -> {
            activity.testMode = true;
        });

        this.doVotes();
    }
    public void doVotes(){
        SelectObservationTest selectObs = new SelectObservationTest();
        selectObs.selectObservation();

        Espresso.onView(ViewMatchers.withId(1123)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(1123)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(1123)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(1123)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(1123)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(1337)).perform(ViewActions.click());

        Matcher<View> matcher1 = ViewMatchers.withId(2134);
        ViewInteraction removalVi = Espresso.onView(matcher1);
        Matcher<View> matcher2 = ViewMatchers.withId(5589);
        ViewInteraction roleVi = Espresso.onView(matcher2);

        removalVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Joe was removed from the game!")));
        roleVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("They were a bandit!")));

        Espresso.onView(ViewMatchers.withId(2023)).perform(ViewActions.click());
    }

    @Test
    public void testVotes2(){
        activityRule.getScenario().onActivity(activity -> {
            activity.testMode = true;
        });

        this.doVotes2();
    }

    public void doVotes2(){
        SelectObservationTest selectObs = new SelectObservationTest();
        selectObs.selectObservation2();

        Espresso.onView(ViewMatchers.withId(5813)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(5813)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(5813)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(5813)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText("Submit")).perform(ViewActions.click());

        Matcher<View> matcher1 = ViewMatchers.withId(2134);
        ViewInteraction removalVi = Espresso.onView(matcher1);
        Matcher<View> matcher2 = ViewMatchers.withId(5589);
        ViewInteraction roleVi = Espresso.onView(matcher2);

        removalVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Jebediah was removed from the game!")));
        roleVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("They were a bandit!")));
    }
}
