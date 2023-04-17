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

@RunWith(AndroidJUnit4.class)
public class SelectObservationEndScreenTest {


    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    public void doObservation(String name, ViewInteraction vi){
        Espresso.onView(ViewMatchers.withText(name)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.confirmActionPlayer)).perform(ViewActions.click());
        try {
            Espresso.onView(ViewMatchers.withText("Number")).perform(ViewActions.click());
            vi.check(ViewAssertions.matches(ViewMatchers.withSubstring("You saw 5 people at the bank")));
        }
        catch(NoMatchingViewException ignore){
            vi.check(ViewAssertions.matches(ViewMatchers.withSubstring("You hung out at the bank for the night")));
        }
        Espresso.onView(ViewMatchers.withText("Confirm")).perform(ViewActions.click());
    }

    @Test
    public void testSelectObservationEndScreen(){
        SelectActionTest selectAct = new SelectActionTest();
        selectAct.testActionSelect();

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

        Matcher<View> matcher3 = ViewMatchers.withId(R.id.moneyStolen);
        ViewInteraction stolenVi = Espresso.onView(matcher3);
        Matcher<View> matcher4 = ViewMatchers.withId(R.id.winText);
        ViewInteraction winVi = Espresso.onView(matcher4);

        stolenVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Money stolen: ")));
        winVi.check(ViewAssertions.matches(ViewMatchers.withSubstring("Bandits win!")));
    }
}
