package edu.vassar.cmpu203.high_noon_heist.view;

import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

/**
 * VoteFragment interface
 */
public interface IVote {

    /**
     * Listener for VoteFragment
     */
    interface Listener{
        Player findPlayer(String name);

        Player onSubmitVotes();

        int getCurDay();

        void onVotingDone();

        PlayerList getPlayers();

        boolean getTestMode();
    }
}
