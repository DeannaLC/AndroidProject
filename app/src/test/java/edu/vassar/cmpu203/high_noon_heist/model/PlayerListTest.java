package edu.vassar.cmpu203.high_noon_heist.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

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


    /**
     * Tests voteVals() by creating a PlayerList object and adding 3 players to its list of players, adding the new Player object to the PlayerLists list of players,
     * then running findPlayer() on the Playerlist to check that the Player object is contained within its list of players.
     */
    @Test
    void testVoteVals()
    {
        PlayerList plList = new PlayerList();

        plList.addCowboy("a");
        plList.addBandit("b");
        plList.addCowboy("c");

        Player a = (Player) plList.players.get(0);
        Player b = (Player) plList.players.get(1);
        Player c = (Player) plList.players.get(2);

        a.vote(2);
        b.vote(8);
        c.vote(3);

        int[] exArr = {2,8,3};

        //boolean check = Arrays.equals(exArr, plList.voteVals());

        //assertTrue(check);
    }

    /**
     * Tests tallyVotes() by creating a PlayerList object and adding 3 players to its list of players,
     * then setting each of them to have their own vote values,
     * then asserting that running tallyVotes() on the Playerlist returns the correct number of votes.
     */
    @Test
    void testTallyVotes()
    {
        PlayerList plList = new PlayerList();

        plList.addCowboy("a");
        plList.addBandit("b");
        plList.addCowboy("c");

        Player a = (Player) plList.players.get(0);
        Player b = (Player) plList.players.get(1);
        Player c = (Player) plList.players.get(2);

        a.vote(2);
        b.vote(8);
        c.vote(3);

        assertEquals(plList.tallyVotes(), 13);
    }

    /**
     * Tests mostVotes() by creating a PlayerList object and adding 3 players to its list of players,
     * then setting each of them to have their own vote values,
     * then asserting that running tallyVotes() on the Playerlist returns the name of the player with the most votes.
     */
    @Test
    void testMostVotes()
    {
        PlayerList plList = new PlayerList();

        plList.addCowboy("a");
        plList.addBandit("b");
        plList.addCowboy("c");

        Player a = (Player) plList.players.get(0);
        Player b = (Player) plList.players.get(1);
        Player c = (Player) plList.players.get(2);

        a.vote(2);
        b.vote(8);
        c.vote(3);

        assertEquals((plList.mostVotes()).getName(), "b");
    }

    /**
     * Tests checkTie() by creating a PlayerList object and adding 3 players to its list of players,
     * then setting each of them to have their own vote values,
     * then asserting that running tallyVotes() on the Playerlist returns true if there is a tie for the most votes and false otherwise.
     */
    @Test
    void testCheckTie()
    {
        PlayerList plList = new PlayerList();

        plList.addCowboy("a");
        plList.addBandit("b");
        plList.addCowboy("c");

        Player a = (Player) plList.players.get(0);
        Player b = (Player) plList.players.get(1);
        Player c = (Player) plList.players.get(2);

        a.vote(2);
        b.vote(8);
        c.vote(3);

        assertFalse(plList.checkTie());

        c.vote(8);
        assertTrue(plList.checkTie());
    }

    /**
     * Tests canRemove() by creating a PlayerList object and adding 3 players to its list of players,
     * then setting each of them to have their own vote values,
     * then asserting that running canRemove() on the Playerlist returns true when the total number of votes is greater than the number of players / 2 and false otherwise.
     */
    @Test
    void testCanRemove()
    {
        PlayerList plList = new PlayerList();

        plList.addCowboy("a");
        plList.addBandit("b");
        plList.addCowboy("c");
        plList.addCowboy("d");

        Player a = (Player) plList.players.get(0);
        Player b = (Player) plList.players.get(1);
        Player c = (Player) plList.players.get(2);
        Player d = (Player) plList.players.get(3);

        a.vote(1);
        b.vote(3);
        c.vote(0);
        d.vote(0);

        assertTrue(plList.canRemove());

        b.vote(0);
        assertFalse(plList.canRemove());
    }

    /**
     * Tests resetAllVotes() by creating a PlayerList object and adding 3 players to its list of players,
     * then setting each of them to have their own vote values,
     * then running tallyVotes() on the Playerlist,
     * then asserting for each player that their votes value is 0.
     */
    @Test
    void testResetAllVotes()
    {
        PlayerList plList = new PlayerList();

        plList.addCowboy("a");
        plList.addBandit("b");
        plList.addCowboy("c");

        Player a = (Player) plList.players.get(0);
        Player b = (Player) plList.players.get(1);
        Player c = (Player) plList.players.get(2);

        a.vote(2);
        b.vote(8);
        c.vote(3);

        plList.resetAllVotes();

        Player cur;
        for (int i = 0; i < plList.players.size(); i = i + 1){
            cur = (Player) plList.players.get(i);
            assertEquals(cur.getVotes(), 0);
        }
    }
}