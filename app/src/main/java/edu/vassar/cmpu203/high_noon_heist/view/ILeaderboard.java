package edu.vassar.cmpu203.high_noon_heist.view;

import edu.vassar.cmpu203.high_noon_heist.model.Leaderboard;

/**
 * LeaderboardFragment interface
 */
public interface ILeaderboard {

    /**
     * Listener for LeaderboardFragment
     */
    interface Listener{
        Leaderboard getLeaderboard();
        void onViewed();

        void onLeaderboardCleared(ILeaderboard l);
    }

    void showDisplay();

}
