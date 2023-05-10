package edu.vassar.cmpu203.high_noon_heist.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WinnerTest {

    /**
     * Tests toString() by creating a Winner object for a Cowboy win and a Bandit win and setting those wins,
     * then asserting that the toString() for each contains the win message.
     */
    @Test
    void testToString()
    {
        Winner cowboyWinner = new Winner();
        Winner banditWinner = new Winner();

        cowboyWinner.setCowboyWin();
        banditWinner.setBanditWin();


        assertTrue(cowboyWinner.toString().contains("Cowboys won on "));
        assertTrue(banditWinner.toString().contains("Bandits won on "));
    }
}