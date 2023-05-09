package edu.vassar.cmpu203.high_noon_heist.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.high_noon_heist.R;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentViewObservationBinding;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentVoteBinding;
import edu.vassar.cmpu203.high_noon_heist.model.Bandit;
import edu.vassar.cmpu203.high_noon_heist.model.Cowboy;
import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

/**
 * Fragment for voting players out of the game
 */
public class VoteFragment extends Fragment implements IVote{

    private Listener listener;

    private FragmentVoteBinding binding;

    private boolean votesDone = false;
    private static final String VOTESDONE = "votesDone";
    private Player votingOut;
    private static final String VOTINGOUT = "votingOut";
    public VoteFragment(){}

    /**
     * VoteFragment constructor
     * @param listener, MainActivity that runs methods
     */
    public VoteFragment(Listener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentVoteBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    /**
     * Creates an OnClickListener for adding votes corresponding to a Player and updates display
     * @param name of Player having votes updated
     * @return OnClickListener that adds a vote to a player
     */
    public View.OnClickListener addVoteListener(String name){
        return new View.OnClickListener(){
            public void onClick(View view){
                PlayerList people = VoteFragment.this.listener.getPlayers();
                Player person = VoteFragment.this.listener.findPlayer(name);
                if ((people.tallyVotes() + 1) > people.players.size()){
                    Snackbar sb = Snackbar.make(view, "cannot vote more than number of players", Snackbar.LENGTH_LONG);
                    sb.show();
                    return;
                }
                person.addVote();
                int curId = people.players.indexOf(person) + 4300;
                TextView edit = (TextView) VoteFragment.this.binding.voting.findViewById(curId);
                edit.setText(person.getVotes() + "");
            }
        };
    }

    /**
     * Creates an OnClickListener for subtracting votes corresponding to a Player and updates display
     * @param name of Player having votes updated
     * @return OnClickListener that subtracts a vote from a player
     */
    public View.OnClickListener subVoteListener(String name){
        return new View.OnClickListener(){
            public void onClick(View view){
                PlayerList people = VoteFragment.this.listener.getPlayers();
                Player person = VoteFragment.this.listener.findPlayer(name);
                person.subVote();
                int curId = people.players.indexOf(person) + 4300;
                TextView edit = (TextView) VoteFragment.this.binding.voting.findViewById(curId);
                edit.setText(person.getVotes() + "");
            }
        };
    }

    /**
     * Dynamically generates vote buttons for Players, their names, and how many votes they have
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Player cur;
        TextView nameDisplay;
        TextView voteDisplay;
        TextView day = new TextView(super.getContext());
        day.setText("Day " + this.listener.getCurDay());
        this.binding.voting.addView(day);
        String curName;
        PlayerList people = this.listener.getPlayers();
        if (this.votesDone == false) {
            for (int i = 0; i < people.players.size(); i = i + 1) {
                LinearLayout addRow = new LinearLayout(super.getContext());
                addRow.setOrientation(LinearLayout.HORIZONTAL);
                Button up = new MaterialButton(super.getContext());
                Button down = new MaterialButton(super.getContext());
                up.setText("plus");
                if (i == 0 && this.listener.getTestMode())
                    up.setId(1123 + i);
                if (i == 2 && this.listener.getTestMode())
                    up.setId(5811 + 2);
                down.setText("minus");
                cur = (Player) people.players.get(i);
                curName = cur.getName();
                nameDisplay = new TextView(super.getContext());
                nameDisplay.setText(curName);
                voteDisplay = new TextView(super.getContext());
                voteDisplay.setText("" + cur.getVotes());
                voteDisplay.setId(4300 + i);
                up.setOnClickListener(addVoteListener(curName));
                down.setOnClickListener(subVoteListener(curName));
                addRow.addView(voteDisplay);
                addRow.addView(nameDisplay);
                addRow.addView(up);
                addRow.addView(down);
                this.binding.voting.addView(addRow);
            }
            Button submit = new MaterialButton(super.getContext());
            submit.setText("Submit");
            submit.setId(1337 + 0);
            submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    VoteFragment.this.doneVoting();
                }
            });
            this.binding.voting.addView(submit);
        }
    }

    /**
     * Shows what happens when voting is done
     */
    public void doneVoting(){
        this.votesDone = true;
        this.binding.voting.removeAllViews();
        if (this.votingOut == null)
            this.votingOut = this.listener.onSubmitVotes();
        TextView resultText = new TextView(super.getContext());
        resultText.setId(2134 + 0);
        TextView roleText = new TextView(super.getContext());
        roleText.setId(5589 + 0);
        if (votingOut == null){
            resultText.setText("Vote failed! No one was removed!");
        }
        else{
            resultText.setText(votingOut.getName() + " was removed from the game!");
            if (votingOut.role() == 1)
                roleText.setText("They were a bandit!");
            else
                roleText.setText("They were a cowboy!");
        }
        this.binding.voting.addView(resultText);
        this.binding.voting.addView(roleText);
        Button confirm = new MaterialButton(VoteFragment.super.getContext());
        confirm.setText("Confirm");
        confirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                VoteFragment.this.listener.onVotingDone();
            }
        });
        confirm.setId(2023 + 0);
        this.binding.voting.addView(confirm);
    }

    /**
     * Saves whether votes have been submitted
     * @param outState Bundle in which to place your saved state.
     */
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(VOTESDONE, this.votesDone);
        if (this.votingOut != null) {
            outState.putBundle(VOTINGOUT, this.votingOut.toBundle());
        }
    }

    /**
     * Restores previous fragment state
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    public void onViewStateRestored(@NonNull Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
            this.votesDone = savedInstanceState.getBoolean(VOTESDONE);
        if (this.votesDone) {
            Bundle pBundle = savedInstanceState.getBundle(VOTINGOUT);
            if (pBundle.getString("role").equals("cowboy"))
                this.votingOut = Cowboy.fromBundle(pBundle);
            else
                this.votingOut = Bandit.fromBundle(pBundle);
            this.doneVoting();
        }
    }
}