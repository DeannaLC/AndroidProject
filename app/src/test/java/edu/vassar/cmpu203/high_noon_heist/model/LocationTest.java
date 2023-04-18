package edu.vassar.cmpu203.high_noon_heist.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LocationTest {

    /**
     * Tests randPlayer() by creating a location object, 3 player Objects, and then having those .observe() the "bank" location to add them to the list of Players at the bank.
     * Making a 4th Player to .observe() the "ranch", then checking that running randPlayer() on the 1st player does not return itself.
     * Also checking that running randPlayer() on the 4th Player returns null.
     */
    @Test
    void testRandPlayer()
    {
        Location loc = new Location();

        Player a = new Player("a");
        Player b = new Player("b");
        Player c = new Player("c");
        a.observe(loc, "bank");
        b.observe(loc, "bank");
        c.observe(loc, "bank");

        Player d = new Player("d");
        d.observe(loc, "ranch");
        Player e = loc.randPlayer("a", "bank");
        assertNotEquals(e, a);

        Player f = loc.randPlayer("d", "ranch");
        assertEquals(f, null);
    }

    /**
     * Tests getValue() by creating a location object for each location and running getValue on them, then checking that the result is between the given range.
     */
    @Test
    void testGetValue()
    {
        Location bank = new Location();
        int bankVal = bank.getValue("bank");
        System.out.println(bankVal);
        assertTrue(bankVal >= 1500 && bankVal <= 2000);

        Location saloon = new Location();
        int saloonVal = bank.getValue("saloon");
        System.out.println(saloonVal);
        assertTrue(saloonVal >= 1000 && saloonVal <= 1500);

        Location ranch = new Location();
        int ranchVal = ranch.getValue("ranch");
        System.out.println(ranchVal);
        assertTrue(ranchVal >= 500 && ranchVal <= 1000);

    }
}