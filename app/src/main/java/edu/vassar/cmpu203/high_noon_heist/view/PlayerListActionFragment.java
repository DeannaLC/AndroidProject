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

/**
 * Fragment for choosing a player to take actions or view results
 */
public class PlayerListActionFragment extends Fragment implements IPlayerListAction {

    private FragmentPlayerListActionBinding binding;

    private Listener listener;
    //private PlayerList activePlayers;

    public PlayerListActionFragment(){
        // Required empty public constructor
    }

    /**
     * Constructor for PlayerListActionFragment
     * @param listener, MainActivity to run methods
     */
    public PlayerListActionFragment(Listener listener){
        this.listener = listener;
    }

    /**
     * Dynamically generates RadioButtons based on canAct from listener
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RadioButton newButton;
        Player cur;
        PlayerList activePlayers = this.listener.getCanAct();
        for (int i = 0; i < activePlayers.players.size(); i = i + 1) {
            newButton = new RadioButton(super.getContext());
            cur = (Player) activePlayers.players.get(i);
            newButton.setText(cur.getName());
            this.binding.playerActionList.addView(newButton);
        }
        if (this.listener.checkPhase() == 1)
            this.binding.selectText.setText("Choose a player to take Action");
        else
            this.binding.selectText.setText("Choose a player to view Action results");
        this.binding.confirmActionPlayer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                int selected = PlayerListActionFragment.this.binding.playerActionList.getCheckedRadioButtonId();
                RadioButton selectedButton = PlayerListActionFragment.this.binding.playerActionList.findViewById(selected);
                if (selectedButton == null){
                    Snackbar sb = Snackbar.make(view, "need to select a player", Snackbar.LENGTH_LONG);
                    sb.show();
                    return;
                }
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