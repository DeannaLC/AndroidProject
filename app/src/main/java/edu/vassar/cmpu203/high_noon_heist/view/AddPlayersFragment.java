package edu.vassar.cmpu203.high_noon_heist.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.high_noon_heist.R;
import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentAddPlayersBinding;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentConfigGameBinding;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

/**
 * Fragment for adding players to the game
 */
public class AddPlayersFragment extends Fragment implements IAddPlayers {

    private FragmentAddPlayersBinding binding;
    private Listener listener;
    private boolean viewingRole = false;
    private static final String VIEWINGROLE = "onViewingRole";

    public AddPlayersFragment() {
        // Required empty public constructor
    }

    public AddPlayersFragment(Listener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentAddPlayersBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.binding.confirmButton.setVisibility(View.INVISIBLE);
        this.binding.addNameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                    Editable playerNameEditable = AddPlayersFragment.this.binding.nameInputEditable.getText();
                    String playerName = playerNameEditable.toString();

                    if (AddPlayersFragment.this.listener.findPlayer(playerName) != null){
                        Snackbar sb = Snackbar.make(view, "no repeat names", Snackbar.LENGTH_LONG);
                        sb.show();
                        return;
                    }

                    if (playerName.length() == 0) {
                        Snackbar sb = Snackbar.make(view, "need player name", Snackbar.LENGTH_LONG);
                        sb.show();
                        return;
                    }

                    playerNameEditable.clear();

                    AddPlayersFragment.this.listener.onAddedPlayer(playerName, AddPlayersFragment.this);

                }
        });
        this.showNames();
    }


    /**
     * Displays a list of player names, or option to advance to next screen if player amount is reached
     */
    public void showNames(){
        PlayerList players = this.listener.getPlayers();
        this.binding.displayPlayers.setText(players.toString());
        if (this.listener.checkPlayerCap()){
            this.binding.nameInputEditable.setVisibility(View.INVISIBLE);
            this.binding.addPlayersText.setVisibility(View.INVISIBLE);
            this.binding.addNameButton.setText("Next");
            this.binding.addNameButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    AddPlayersFragment.this.listener.onPlayersSet();
                }
            });
        }
    }

    /**
     * Displays an added player's role
     */
    public void showRole(){
        this.viewingRole = true;
        this.binding.addNameButton.setVisibility(View.INVISIBLE);
        this.binding.showRoleView.setText(this.listener.showRole());
        this.binding.confirmButton.setVisibility(View.VISIBLE);
        this.binding.nameInputEditable.setVisibility(View.INVISIBLE);
        this.binding.addPlayersText.setVisibility(View.INVISIBLE);
        this.binding.displayPlayers.setVisibility(View.INVISIBLE);

        this.binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AddPlayersFragment.this.clearRole();
            }
        });
    }

    /**
     * Changes layout after a player has viewed their role
     */
    public void clearRole(){
        this.viewingRole = false;
        this.binding.nameInputEditable.setVisibility(View.VISIBLE);
        this.binding.addPlayersText.setVisibility(View.VISIBLE);
        this.binding.displayPlayers.setVisibility(View.VISIBLE);
        this.binding.showRoleView.setText(null);
        this.binding.confirmButton.setVisibility(View.INVISIBLE);
        this.binding.addNameButton.setVisibility(View.VISIBLE);
        this.showNames();
    }

    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(VIEWINGROLE, this.viewingRole);
    }

    public void onViewStateRestored(@NonNull Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
            this.viewingRole = savedInstanceState.getBoolean(VIEWINGROLE);
        if (viewingRole)
            this.showRole();
    }

}