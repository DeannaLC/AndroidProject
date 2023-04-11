package edu.vassar.cmpu203.high_noon_heist.view;

import android.view.View;

import androidx.annotation.NonNull;

import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

public interface IAddPlayersView {

    interface Listener{
        void onAddedPlayer(@NonNull String name);

        void onSetCount(int playerCount, int bandits);
    }

    View getRootView();

    void updatePlayerNames(PlayerList players);

}
