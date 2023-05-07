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

public class VoteFragment extends Fragment implements IVote{

    private Listener listener;

    private FragmentVoteBinding binding;

    private boolean votesDone = false;
    private static final String VOTESDONE = "votesDone";
    private Player votingOut;
    private static final String VOTINGOUT = "votingOut";

   // private PlayerList people;

    public VoteFragment(){}

    public VoteFragment(Listener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentVoteBinding.inflate(inflater);
        return this.binding.getRoot();
    }

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

    public View.OnClickListener subVoteListener(String name){
        return new View.OnClickListener(){
            public void onClick(View view){
                PlayerList people = VoteFragment.this.listener.getPlayers();
                Player person = VoteFragment.this.listener.findPlayer(name);
                person.subVote();
                //VoteFragment.this.binding.voteStr.setText(VoteFragment.this.people.voteValsStr());
                int curId = people.players.indexOf(person) + 4300;
                TextView edit = (TextView) VoteFragment.this.binding.voting.findViewById(curId);
                edit.setText(person.getVotes() + "");

            }
        };
    }

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
                if (i == 4 && this.listener.getTestMode())
                    up.setId(5809 + i);
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

    public void doneVoting(){
        this.votesDone = true;
        this.binding.voting.removeAllViews();
        if (this.votingOut == null)
            this.votingOut = this.listener.onSubmitVotes();
        TextView resultText = new TextView(super.getContext());
        resultText.setId(2134 + 0);
        TextView roleText = new TextView(super.getContext());
        roleText.setText(5589 + 0);
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

    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(VOTESDONE, this.votesDone);
        outState.putBundle(VOTINGOUT, this.votingOut.toBundle());
    }

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