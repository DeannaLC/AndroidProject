package edu.vassar.cmpu203.high_noon_heist.view;

import edu.vassar.cmpu203.high_noon_heist.model.Player;

public interface IActionSelect {
    interface Listener{
        void observeAt(String place, Player player);

        int stealFrom(String place);

        void onActionDone();

        Player getCurrent();

        //void setLocInPL();

        //void setRobbedInPL();
    }
}
