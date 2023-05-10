package edu.vassar.cmpu203.high_noon_heist.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LeaderboardTest {

    /**
     * Tests addWinner() by creating a Leaderboard and a Winner object,
     * then adding the Winner object to the Leaderboard object with addWinner(),
     * then asserting that the Leaderboard object contains the created Winner object.
     */
    @Test
    void addWinner() {
        Leaderboard lb = new Leaderboard();

        Winner w = new Winner();

        lb.addWinner(w);
        assertTrue(lb.winners.contains(w));
    }
}