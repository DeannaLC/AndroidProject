package edu.vassar.cmpu203.high_noon_heist.view;

import edu.vassar.cmpu203.high_noon_heist.model.Player;

public interface IViewObservation {
    interface Listener{
        public String showObservation(int choice);

        public void onActionDone();

        public Player getCurrent();

        public String doViewLoc();

    }
}
