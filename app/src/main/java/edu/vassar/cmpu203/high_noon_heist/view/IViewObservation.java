package edu.vassar.cmpu203.high_noon_heist.view;

import edu.vassar.cmpu203.high_noon_heist.model.Player;

/**
 * ViewObservationFragment interface
 */
public interface IViewObservation {

    /**
     * Listener for ViewObservationFragment
     */
    interface Listener{
        String showObservation(int choice);

        void onActionDone();

        Player getCurrent();

        String doViewLoc();
    }
}
