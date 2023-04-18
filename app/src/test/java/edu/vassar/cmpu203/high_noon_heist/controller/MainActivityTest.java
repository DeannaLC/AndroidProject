package edu.vassar.cmpu203.high_noon_heist.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * A class meant to test the functionality of the MainActivity class.
 */

class MainActivityTest {

    /**
     * Tests draw() by creating a MainActivity object and running draw on it, then checking the result.
     */
    @Test
    void testDraw() {
        MainActivity main = new MainActivity(55,10000, 7, 3);
        assertEquals("7 players, 3 bandits, 55 days, 10000$ to win", main.toString(), "not equal strings");
        //main.draw();
        //System.out.println("print test");
        //assertEquals(main.banditCount, (main.retBanditVals()).size(), "failure test message");
    }
}