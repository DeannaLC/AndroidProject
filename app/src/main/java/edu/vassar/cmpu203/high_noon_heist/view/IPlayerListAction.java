package edu.vassar.cmpu203.high_noon_heist.view;

import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

/**
 * PlayerListActionFragment interface
 */
public interface IPlayerListAction {

    /**
     * Listener for PlayerListActionFragment
     */
    interface Listener{
        void setCurrentPlayer(Player player);

        void playerSelected(String name);

        int checkPhase();

        PlayerList getCanAct();
    }
}
