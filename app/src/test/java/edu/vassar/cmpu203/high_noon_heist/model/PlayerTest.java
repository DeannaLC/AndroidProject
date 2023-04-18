package edu.vassar.cmpu203.high_noon_heist.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

    /**
     * Tests observe() by creating a Player object, running observe() for each location String on it, and then checking that each given location contains the player.
     */
    @Test
    void testObserve()
    {
        Player gamer = new Player("gamer");

        Location loc1 = new Location();
        gamer.observe(loc1, "bank");
        int gamerIndex1 = loc1.bank.indexOf(gamer);
        assertTrue(gamerIndex1 != -1);

        Location loc2 = new Location();
        gamer.observe(loc2, "saloon");
        int gamerIndex2 = loc2.saloon.indexOf(gamer);
        assertTrue(gamerIndex2 != -1);

        Location loc3 = new Location();
        gamer.observe(loc3, "ranch");
        int gamerIndex3 = loc3.ranch.indexOf(gamer);
        assertTrue(gamerIndex3 != -1);
    }
}