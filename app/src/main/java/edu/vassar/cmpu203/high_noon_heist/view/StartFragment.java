package edu.vassar.cmpu203.high_noon_heist.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import edu.vassar.cmpu203.high_noon_heist.R;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentResultScreenBinding;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentStartBinding;

/**
 * Beginning fragment for starting the game, checking leaderboard, viewing rules
 */
public class StartFragment extends Fragment implements IStart{

    private Listener listener;

    private FragmentStartBinding binding;

    private boolean rules1 = false;
    private boolean rules2 = false;
    private boolean rules3 = false;
    private final static String RULES1 = "rules1";
    private final static String RULES2 = "rules2";
    private final static String RULES3 = "rules3";


    public StartFragment() {
        // Required empty public constructor
    }

    public StartFragment(Listener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentStartBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.binding.start.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                StartFragment.this.listener.onBegin();
            }
        });
        this.binding.leaderboard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                StartFragment.this.listener.onLeaderboardCheck();
            }
        });
        this.binding.rules.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                StartFragment.this.ruleSet1();
            }
        });
    }

    public void ruleSet1(){
        this.rules1 = true;
        this.binding.startOptions.removeAllViews();
        TextView a = new TextView(super.getContext());
        TextView b = new TextView(super.getContext());
        TextView c = new TextView(super.getContext());
        TextView d = new TextView(super.getContext());
        TextView e = new TextView(super.getContext());
        a.setText("You will be assigned one of two roles: Bandit or Cowboy\n");
        b.setText("As a Cowboy, you either want to remove all the bandits from the town, or not lose all the money before the sheriffs come into town\n");
        c.setText("As a Bandit, you want to steal enough money to skip town or take control by holding a majority\n");
        d.setText("Once any of the win conditions are met, the game will end. \n");
        e.setText("This game requires 3 or more players. Less than half the players can be bandits. \n");
        Button next = new MaterialButton(super.getContext());
        next.setText("Next");
        this.binding.startOptions.addView(a);
        this.binding.startOptions.addView(b);
        this.binding.startOptions.addView(c);
        next.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                StartFragment.this.ruleSet2();
            }
        });
        this.binding.startOptions.addView(next);
    }

    public void ruleSet2(){
        this.rules1 = false;
        this.rules2 = true;
        this.binding.startOptions.removeAllViews();

        TextView a = new TextView(super.getContext());
        TextView b = new TextView(super.getContext());
        TextView c = new TextView(super.getContext());

        a.setText("The game is split into 3 phases: Action Phase, Observation Phase, and Voting Phase. Day 1 begins at the first Voting Phase. \n");
        b.setText("First, you will set up the game settings and add in your names.\n");
        c.setText("As you add yourself and make per person actions throughout the game, keep the phone to yourself. It will show your role and things you can do that are unique");
        this.binding.startOptions.addView(a);
        this.binding.startOptions.addView(b);
        this.binding.startOptions.addView(c);
        Button next = new MaterialButton(super.getContext());
        next.setText("Next");
        next.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                StartFragment.this.ruleSet3();
            }
        });
        this.binding.startOptions.addView(next);
    }

    public void ruleSet3(){
        this.rules2 = false;
        this.rules3 = true;

        this.binding.startOptions.removeAllViews();

        TextView a = new TextView(super.getContext());
        TextView b = new TextView(super.getContext());
        TextView c = new TextView(super.getContext());

        a.setText("The first phase of the game is the Action phase. If you are a Cowboy, you will be able to go to a place to stake it out. If you are a Bandit, you can choose to either steal from a place, or try to blend in.\n");
        b.setText("The second phase is the Observation Phase. If you are a Cowboy, you can choose to see the total players including yourself at a location or a random player at the location. If you were a Bandit and chose to steal, you will not get any information. If you're a bandit and chose to watch, you will randomly see the number of players or another person. \n");
        c.setText("The third phase is the Voting Phase. You will be able to discuss amongst yourselves to decide who the Bandits are and try to vote them out, or try to stay hidden and cast suspicion on someone else. If more than half the players vote and there are no ties, the person with the most votes will be removed from the game and their role will be revealed.\n");

        this.binding.startOptions.addView(a);
        this.binding.startOptions.addView(b);
        this.binding.startOptions.addView(c);

        Button next = new MaterialButton(super.getContext());
        next.setText("Done");
        next.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                StartFragment.this.listener.onViewed();
            }
        });
        this.binding.startOptions.addView(next);
    }


    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RULES1, this.rules1);
        outState.putBoolean(RULES2, this.rules2);
        outState.putBoolean(RULES3, this.rules3);
    }

    public void onViewStateRestored(@NonNull Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            this.rules1 = savedInstanceState.getBoolean(RULES1);
            this.rules2 = savedInstanceState.getBoolean(RULES2);
            this.rules3 = savedInstanceState.getBoolean(RULES3);
        }
        if (this.rules1)
            this.ruleSet1();
        else if (this.rules2)
            this.ruleSet2();
        else if (this.rules3)
            this.ruleSet3();
    }

}