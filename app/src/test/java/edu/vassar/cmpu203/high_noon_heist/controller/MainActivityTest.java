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
        main.draw();
        System.out.println("print test");
        assertEquals(main.banditCount, (main.retBanditVals()).size(), "failure test message");
    }
}