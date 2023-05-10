package edu.vassar.cmpu203.high_noon_heist.model;

import static org.junit.jupiter.api.Assertions.*;

import android.os.Bundle;

import org.junit.jupiter.api.Test;

class BanditTest {

    /**
     * Tests observation() by creating a location object, a Bandit object to .rob() the "bank, and a Bandit object to .observe() the "bank".
     * Then checking that running .observation() on the bandit that robbed the bank returns the empty string and that running .observation() on the bandit that observed the bank
     * will return "2" or the name of the other bandit.
     */
    @Test
    void testObservation()
    {
        Location loc = new Location();

        Bandit notSoBadGuy = new Bandit("Zangief");

        notSoBadGuy.observe(loc, "bank");

        Bandit badGuy = new Bandit("Villain");

        badGuy.rob(loc, "bank");

        assertEquals("", badGuy.observation(loc, 0));


        String obs = notSoBadGuy.observation(loc, 0);

        if (obs.length() == 1)
        {
            assertEquals("2", obs);
        }
        else
        {
            assertEquals("Villain", obs);
        }


    }

    /**
     * Tests rob() by creating a Bandit object, then for each location, creating a Location object,
     * running rob() on the Bandit object with the Location object and String for each location as parameters,
     * and checking the return value and that each given location contains the Bandit.
     */
    @Test
    void testRob()
    {
        Bandit badGuy = new Bandit("Bad Guy");

        Location loc1 = new Location();
        int robBank = badGuy.rob(loc1, "bank");
        System.out.println(robBank);
        assertTrue(robBank >= 1500 && robBank <= 2000);
        int banditIndex1 = loc1.bank.indexOf(badGuy);
        assertTrue(banditIndex1 != -1);

        Location loc2 = new Location();
        int robSaloon = badGuy.rob(loc2, "saloon");
        System.out.println(robSaloon);
        assertTrue(robSaloon >= 1000 && robSaloon <= 1500);
        int banditIndex2 = loc2.saloon.indexOf(badGuy);
        assertTrue(banditIndex2 != -1);

        Location loc3 = new Location();
        int robRanch = badGuy.rob(loc3, "ranch");
        System.out.println(robRanch);
        assertTrue(robRanch >= 500 && robRanch <= 1000);
        int banditIndex3 = loc3.ranch.indexOf(badGuy);
        assertTrue(banditIndex3 != -1);
    }

    /**
     * Tests displayRole() by creating a bandit object and running displayRole on it, then checking the result.
     */
    @Test
    void testDisplayRole()
    {
        Bandit badGuy = new Bandit("badGuy");
        assertEquals("Your role is Bandit", badGuy.displayRole());
    }

}