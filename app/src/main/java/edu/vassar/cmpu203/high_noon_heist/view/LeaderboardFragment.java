package edu.vassar.cmpu203.high_noon_heist.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.high_noon_heist.R;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentLeaderboardBinding;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentPlayerListActionBinding;
import edu.vassar.cmpu203.high_noon_heist.model.Leaderboard;

/**
 * Fragment for showing previous wins
 */
public class LeaderboardFragment extends Fragment implements ILeaderboard{

    private FragmentLeaderboardBinding binding;
    private Listener listener;

    public LeaderboardFragment() {
    }

    public LeaderboardFragment(Listener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentLeaderboardBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    /**
     * Shows the leaderboard
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.showDisplay();
        this.binding.back.setOnClickListener(new View.OnClickListener(){
           public void onClick(View view){
               LeaderboardFragment.this.listener.onViewed();
           }
        });
        this.binding.clear.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                LeaderboardFragment.this.listener.onLeaderboardCleared(LeaderboardFragment.this);
            }
        });

    }

    /**
     * Shows leaderboard data
     */
    public void showDisplay(){
        Leaderboard display = this.listener.getLeaderboard();
        this.binding.leaderboardDisplay.setText(display.toString());
    }

}