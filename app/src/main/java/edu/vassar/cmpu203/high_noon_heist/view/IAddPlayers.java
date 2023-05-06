package edu.vassar.cmpu203.high_noon_heist.view;

import androidx.annotation.NonNull;

import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

public interface IAddPlayers {
    interface Listener{
        void onAddedPlayer(@NonNull String name, IAddPlayers addPlayers);

        //void onConfiguredGame();

        boolean checkPlayerCap();

        void onPlayersSet();

        PlayerList getPlayers();

        String showRole();

    }

    void showNames();

    void showRole();
}
