package edu.vassar.cmpu203.high_noon_heist.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.high_noon_heist.R;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentAddPlayersBinding;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentConfigGameBinding;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

public class AddPlayersFragment extends Fragment implements IAddPlayers {

    private FragmentAddPlayersBinding binding;
    private Listener listener;

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
        this.binding.addNameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Editable playerNameEditable = AddPlayersFragment.this.binding.nameInputEditable.getText();
                String playerName = playerNameEditable.toString();

                if (playerName.length() == 0){
                    Snackbar sb = Snackbar.make(view, "need player name", Snackbar.LENGTH_LONG);
                    sb.show();
                    return;
                }

                playerNameEditable.clear();

                AddPlayersFragment.this.listener.onAddedPlayer(playerName, AddPlayersFragment.this);

            }
        });
    }

    public void showNames(PlayerList players){
        this.binding.displayPlayers.setText(players.toString());
    }
}