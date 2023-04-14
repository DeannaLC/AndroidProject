package edu.vassar.cmpu203.high_noon_heist.view;

public interface IViewObservation {
    interface Listener{
        public String showObservation(int choice);

        public void onActionDone();
    }
}
