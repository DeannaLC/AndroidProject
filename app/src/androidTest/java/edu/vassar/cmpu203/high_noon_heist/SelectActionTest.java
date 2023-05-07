package edu.vassar.cmpu203.high_noon_heist;

import android.os.SystemClock;
import android.view.View;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
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

@RunWith(AndroidJUnit4.class)
public class SelectActionTest {

    public boolean robbed = false;


    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Has a player perform an action based on their role, and checks onscreen text is correct
     *
     * @param name of player being tested
     * @param vi view interaction being checked
     */
    public void doAction(String name, ViewInteraction vi){
        Espresso.onView(ViewMatchers.withText(name)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.confirmActionPlayer)).perform(ViewActions.click());
        try {
            Espresso.onView(ViewMatchers.withText("Steal")).perform(ViewActions.click());
            Espresso.onView(ViewMatchers.withText("Bank")).perform(ViewActions.click());
            vi.check(ViewAssertions.matches(ViewMatchers.withSubstring("You stole ")));
        }
        catch (NoMatchingViewException ignore){
            Espresso.onView(ViewMatchers.withText("Bank")).perform(ViewActions.click());
            vi.check(ViewAssertions.matches(ViewMatchers.withSubstring("You chose to watch the bank for the night")));
        }
        Espresso.onView(ViewMatchers.withText("Confirm")).perform(ViewActions.click());
    }

    /**
     * Test for player and action select screen
     */
    @Test
    public void testActionSelect(){
        AddPlayersTest addPeople = new AddPlayersTest();
        addPeople.testAddPlayers();

        Espresso.onView(ViewMatchers.withText("Next")).perform(ViewActions.click());

        Matcher<View> matcher1 = ViewMatchers.withId(R.id.selectText);
        ViewInteraction selectVi = Espresso.onView(matcher1);
        Matcher<View> matcher2 = ViewMatchers.withId(R.id.splashText);
        ViewInteraction promptVi = Espresso.onView(matcher2);

        selectVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Choose a player to take Action")));

        this.doAction("Joe", promptVi);
        this.doAction("Jack", promptVi);
        this.doAction("Jeb", promptVi);
        this.doAction("Jebediah", promptVi);
        this.doAction("Phil", promptVi);
    }
    @Test
    public void testActionSelect2(){
        VoteTest voteTest = new VoteTest();
        voteTest.testVotes();

        Matcher<View> matcher1 = ViewMatchers.withId(R.id.selectText);
        ViewInteraction selectVi = Espresso.onView(matcher1);
        Matcher<View> matcher2 = ViewMatchers.withId(R.id.splashText);
        ViewInteraction promptVi = Espresso.onView(matcher2);

        selectVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Choose a player to take Action")));
        this.doAction("Jack", promptVi);
        this.doAction("Jeb", promptVi);
        this.doAction("Jebediah", promptVi);
        this.doAction("Phil", promptVi);
    }

}