package edu.vassar.cmpu203.high_noon_heist.view;

import edu.vassar.cmpu203.high_noon_heist.model.Player;

public interface IActionSelect {
    interface Listener{
        public void observeAt(String place, Player player);

        public int stealFrom(String place);

        public void onActionDone();
    }
}
