package edu.vassar.cmpu203.high_noon_heist.view;

import edu.vassar.cmpu203.high_noon_heist.model.Leaderboard;

public interface ILeaderboard {
    interface Listener{
        Leaderboard getLeaderboard();
        void onViewed();
    }

}
