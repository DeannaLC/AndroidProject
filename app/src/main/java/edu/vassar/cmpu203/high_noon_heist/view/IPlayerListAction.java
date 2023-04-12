package edu.vassar.cmpu203.high_noon_heist.view;

import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

public interface IPlayerListAction {
    interface Listener{
        public PlayerList getPlayerListCopy();
        public Player findPlayer(String name);

        public void setCurrentPlayer(Player player);

        public void playerSelected(String name);
    }


}
