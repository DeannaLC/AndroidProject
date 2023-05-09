package edu.vassar.cmpu203.high_noon_heist.view;

/**
 * ResultScreenFragment interface
 */
public interface IResults {

    /**
     * Listener for ResultScreenFragment
     */
    interface Listener{
        int getWin();

        int getMoney();

        void onGameDone();
    }
}
