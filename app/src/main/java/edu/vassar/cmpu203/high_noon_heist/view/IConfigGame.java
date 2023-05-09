package edu.vassar.cmpu203.high_noon_heist.view;

import androidx.annotation.NonNull;

import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;

/**
 * ConfigGameFragment interface
 */
public interface IConfigGame {

    /**
     * Listener for ConfigGameFragment
     */
    interface Listener{
        void onSetOptions(int total, int bandits, int dayLim, int moneyLim, IConfigGame config);

        void onOptionsSet();
    }

    void showConfig();

}
