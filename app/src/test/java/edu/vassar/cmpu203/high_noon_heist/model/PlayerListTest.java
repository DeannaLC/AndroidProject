package edu.vassar.cmpu203.high_noon_heist.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerListTest {

    /**
     * Tests copyPlayers() by creating a PlayerList object, adding players to its list of players, running copyPlayers() on it,
     * and checking that the new list of players generated matches the original.
     */
    @Test
    void testCopyPlayers()
    {
        PlayerList plList = new PlayerList();

        Player a = new Player("a");
        Player b = new Player("b");
        Player c = new Player("c");
        plList.players.add(a);
        plList.players.add(b);
        plList.players.add(c);

        PlayerList copiedList = plList.copyPlayers();

        for (int i = 0; i < 3; i++)
        {
            assertEquals(plList.players.get(i), copiedList.players.get(i));
        }

    }

    /**
     * Tests findPlayer() by creating a Player object and a PlayerList object, adding the new Player object to the PlayerLists list of players,
     * then running findPlayer() on the Playerlist to check that the Player object is contained within its list of players.
     */
    @Test
    void testFindPlayer()
    {
        Player a = new Player("a");

        PlayerList plList = new PlayerList();

        plList.players.add(a);

        Player retPlayer = plList.findPlayer("a");

        assertEquals(a, retPlayer);

        Player notReal = plList.findPlayer("not real");
        assertEquals(notReal, null);

    }

}