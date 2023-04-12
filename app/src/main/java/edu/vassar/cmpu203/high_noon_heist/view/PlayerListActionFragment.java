package edu.vassar.cmpu203.high_noon_heist.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.high_noon_heist.R;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentAddPlayersBinding;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentPlayerListActionBinding;
import edu.vassar.cmpu203.high_noon_heist.model.Bandit;
import edu.vassar.cmpu203.high_noon_heist.model.Cowboy;
import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

public class PlayerListActionFragment extends Fragment implements IPlayerListAction {

    private FragmentPlayerListActionBinding binding;

    private Listener listener;

    public PlayerListActionFragment() {
        // Required empty public constructor
    }

    public PlayerListActionFragment(Listener listener){
        this.listener = listener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        PlayerList active = this.listener.getPlayerListCopy();
        RadioButton newButton;
        Player cur;
        for (int i = 0; i < active.players.size(); i = i + 1) {
            newButton = new RadioButton(super.getContext());
            cur = (Player) active.players.get(i);
            newButton.setText(cur.getName());
            this.binding.playerActionList.addView(newButton);
        }

        this.binding.confirmActionPlayer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                int selected = PlayerListActionFragment.this.binding.playerActionList.getCheckedRadioButtonId();
                RadioButton selectedButton = PlayerListActionFragment.this.binding.playerActionList.findViewById(selected);
                String name = selectedButton.getText().toString();
                PlayerListActionFragment.this.listener.playerSelected(name);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPlayerListActionBinding.inflate(inflater);
        return this.binding.getRoot();
    }
}