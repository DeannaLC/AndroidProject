package edu.vassar.cmpu203.high_noon_heist.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CowboyTest {


    /**
     * Tests observation() by creating a location object, creating 2 Cowboy objects and having them .observe() the "bank,
     * then checking that the .observation() on the number of players returns the String "2" and running .observation() on a random name returns the other players name.
     */
    @Test
    void testObservation()
    {
        Location loc = new Location();

        Cowboy a = new Cowboy("a");
        Cowboy b = new Cowboy("b");
        a.observe(loc, "bank");
        b.observe(loc, "bank");

        String numPlayers = a.observation(loc, 0);
        assertEquals(numPlayers, "2");

        String name = b.observation(loc, 1);
        assertEquals(name, "a");

    }

    /**
     * Tests displayRole() by creating a cowboy object and running displayRole on it, then checking the result.
     */
    @Test
    void testDisplayRole()
    {
        Cowboy goodGuy = new Cowboy("goodGuy");
        assertEquals("Your role is Cowboy", goodGuy.displayRole());
    }
}