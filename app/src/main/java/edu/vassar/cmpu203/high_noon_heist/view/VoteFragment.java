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
import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

public class VoteFragment extends Fragment implements IVote{

    private Listener listener;

    private FragmentVoteBinding binding;

    private PlayerList people;

    public VoteFragment(){}

    public VoteFragment(Listener listener, PlayerList people){
        this.listener = listener;
        this.people = people;
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
                Player person = VoteFragment.this.listener.findPlayer(name);
                if ((VoteFragment.this.people.tallyVotes() + 1) > VoteFragment.this.people.players.size()){
                    Snackbar sb = Snackbar.make(view, "cannot vote more than number of players", Snackbar.LENGTH_LONG);
                    sb.show();
                    return;
                }
                person.addVote();
                int curId = VoteFragment.this.people.players.indexOf(person) + 4300;
                TextView edit = (TextView) VoteFragment.this.binding.voting.findViewById(curId);
                edit.setText(person.getVotes() + "");
            }
        };
    }

    public View.OnClickListener subVoteListener(String name){
        return new View.OnClickListener(){
            public void onClick(View view){
                Player person = VoteFragment.this.listener.findPlayer(name);
                person.subVote();
                //VoteFragment.this.binding.voteStr.setText(VoteFragment.this.people.voteValsStr());
                int curId = VoteFragment.this.people.players.indexOf(person) + 4300;
                TextView edit = (TextView) VoteFragment.this.binding.voting.findViewById(curId);
                edit.setText(person.getVotes() + "");

            }
        };
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        Player cur;
        TextView nameDisplay;
        TextView voteDisplay;
        String curName;
        for (int i = 0; i < this.people.players.size(); i = i + 1) {
            LinearLayout addRow = new LinearLayout(super.getContext());
            addRow.setOrientation(LinearLayout.HORIZONTAL);
            Button up = new MaterialButton(super.getContext());
            Button down = new MaterialButton(super.getContext());
            up.setText("plus");
            down.setText("minus");
            cur = (Player) this.people.players.get(i);
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
    }
}