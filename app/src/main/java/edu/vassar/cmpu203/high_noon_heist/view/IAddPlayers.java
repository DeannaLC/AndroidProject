package edu.vassar.cmpu203.high_noon_heist.view;

import androidx.annotation.NonNull;

import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;
import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

/**
 * AddPlayersFragment interface
 */
public interface IAddPlayers {
    /**
     * Listener for AddPlayersFragment
     */
    interface Listener{
        void onAddedPlayer(@NonNull String name, IAddPlayers addPlayers);

        boolean checkPlayerCap();

        void onPlayersSet();

        PlayerList getPlayers();

        String showRole();

        Player findPlayer(String name);

    }

    void showNames();

    void showRole();
}
