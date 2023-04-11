package edu.vassar.cmpu203.high_noon_heist.view;

import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.high_noon_heist.R;
import edu.vassar.cmpu203.high_noon_heist.databinding.ActivityMainBinding;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

public class AddPlayersView implements IAddPlayersView{
    ActivityMainBinding binding;

    Listener listener;

    public AddPlayersView(@NonNull Context context, @NonNull Listener listener) {

        this.listener = listener;

        // inflate the layout
        this.binding = ActivityMainBinding.inflate(LayoutInflater.from(context));
        //for adding a player
        this.binding.addPlayerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Editable playerNameEditable = AddPlayersView.this.binding.personNameEdit.getText();
                String playerName = playerNameEditable.toString();

                if (playerName.length() == 0){
                    Snackbar sb = Snackbar.make(view, "need player name", Snackbar.LENGTH_LONG);
                    sb.show();
                    return;
                }

                playerNameEditable.clear();

                AddPlayersView.this.listener.onAddedPlayer(playerName);

            }

        }
        );

        //for setting player count
        this.binding.setPlayerCount.setOnClickListener(new View.OnClickListener(){
           public void onClick(View view){
               Editable playerCountEditable = AddPlayersView.this.binding.playerCountEdit.getText();
               String playerCountStr = playerCountEditable.toString();

               Editable banditCountEditable = AddPlayersView.this.binding.banditEdit.getText();
               String banditCountStr = banditCountEditable.toString();
               if (playerCountStr.length() == 0 || banditCountEditable.length() == 0){
                   Snackbar sb = Snackbar.make(view, "invalid", Snackbar.LENGTH_LONG);
                   sb.show();
                   return;
               }
               int playerCount = Integer.parseInt(playerCountStr);
               int banditCount = Integer.parseInt(banditCountStr);

               AddPlayersView.this.listener.onSetCount(playerCount, banditCount);


           }
        });
    }


    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    @Override
    public void updatePlayerNames(PlayerList pl){
        this.binding.displayNames.setText(pl.toString());
    }
}
