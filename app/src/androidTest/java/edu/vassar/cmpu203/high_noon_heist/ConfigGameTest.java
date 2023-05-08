package edu.vassar.cmpu203.high_noon_heist;

import android.os.SystemClock;
import android.view.View;


import androidx.test.espresso.Espresso;
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

@RunWith(AndroidJUnit4.class)
public class ConfigGameTest {

    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test for game's configuration screen
     */
    @Test
    public void testConfigGame(){
        activityRule.getScenario().onActivity(activity -> {
            activity.testMode = true;
        });
        this.configGame();

    }

    public void configGame(){
        Espresso.onView(ViewMatchers.withId(R.id.start)).perform(ViewActions.click());
        //Espresso.onView(ViewMatchers.withId(R.id.secret)).perform(ViewActions.click());

        Matcher<View> matcher = ViewMatchers.withId(R.id.showOptions);

        ViewInteraction settingsVi = Espresso.onView(matcher);

        Espresso.onView(ViewMatchers.withId(R.id.getPlayerEditable)).perform(ViewActions.typeText("5"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.getBanditEditable)).perform(ViewActions.typeText("2"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.getDayEditable)).perform(ViewActions.typeText("10"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.getMoneyEditable)).perform(ViewActions.typeText("50000"));
        Espresso.closeSoftKeyboard();

        Espresso.onView(ViewMatchers.withId(R.id.configGameButton)).perform(ViewActions.click());

        SystemClock.sleep(1000);

        settingsVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("5 players, 2 bandits, 10 days, 50000$ to win")));
    }
}
