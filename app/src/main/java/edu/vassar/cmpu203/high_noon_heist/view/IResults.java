package edu.vassar.cmpu203.high_noon_heist.view;

public interface IResults {
    interface Listener{
        int checkWin();

        int getMoney();

        void onGameDone();
    }
}
