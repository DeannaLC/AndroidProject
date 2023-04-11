package edu.vassar.cmpu203.high_noon_heist.view;

import androidx.annotation.NonNull;

import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;

public interface IConfigGame {

    interface Listener{
        void onSetOptions(int total, int bandits, int dayLim, int moneyLim, IConfigGame config);

        void onOptionsSet();

    }

    void showConfig(MainActivity m);

}
