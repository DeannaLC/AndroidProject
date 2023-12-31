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
import edu.vassar.cmpu203.high_noon_heist.ConfigGameTest;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;

/**
 * Test for the AddPlayersFragment
 */
@RunWith(AndroidJUnit4.class)
public class AddPlayersTest {


    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Function for each name to be added and make sure role is displayed
     *
     * @param name of player being added
     * @param vi view interaction being checked
     */
    public void addPlayerCheckRole(String name, ViewInteraction vi){
        Espresso.onView(ViewMatchers.withId(R.id.nameInputEditable)).perform(ViewActions.typeText(name));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.addNameButton)).perform(ViewActions.click());

        vi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Your role is")));
        Espresso.onView(ViewMatchers.withId(R.id.confirmButton)).perform(ViewActions.click());
    }

    /**
     * Test for add players screen
     */
    @Test
    public void testAddPlayers(){

        activityRule.getScenario().onActivity(activity -> {
            activity.testMode = true;
        });

        this.addPlayers();

    }

    /**
     * Tests adding players into the game, viewing their roles, and showing added players display
     */
    public void addPlayers(){
        ConfigGameTest config = new ConfigGameTest();
        config.configGame();

        Espresso.onView(ViewMatchers.withText("Next")).perform(ViewActions.click());

        Matcher<View> matcher1 = ViewMatchers.withId(R.id.showRoleView);
        ViewInteraction roleVi = Espresso.onView(matcher1);
        Matcher<View> matcher2 = ViewMatchers.withId(R.id.displayPlayers);
        ViewInteraction namesVi = Espresso.onView(matcher2);


        this.addPlayerCheckRole("Joe", roleVi);
        namesVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Joe\n")));
        this.addPlayerCheckRole("Jack", roleVi);
        namesVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Joe\nJack\n")));
        this.addPlayerCheckRole("Jeb", roleVi);
        namesVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Joe\nJack\nJeb\n")));
        this.addPlayerCheckRole("Jebediah", roleVi);
        namesVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Joe\nJack\nJeb\nJebediah\n")));
        this.addPlayerCheckRole("Phil", roleVi);
        namesVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Joe\nJack\nJeb\nJebediah\nPhil\n")));

    }
}