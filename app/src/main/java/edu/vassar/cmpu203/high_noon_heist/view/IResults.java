package edu.vassar.cmpu203.high_noon_heist.view;

public interface IResults {
    interface Listener{
        int getWin();

        int getMoney();

        void onGameDone();
    }
}
