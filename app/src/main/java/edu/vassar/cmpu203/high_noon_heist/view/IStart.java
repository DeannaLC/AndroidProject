package edu.vassar.cmpu203.high_noon_heist.view;

/**
 * StartFragment interface
 */
public interface IStart {

    /**
     * Listener for StartFragment
     */
    interface Listener{
        void onBegin();

        void onViewed();

        void onLeaderboardCheck();

    }
}
