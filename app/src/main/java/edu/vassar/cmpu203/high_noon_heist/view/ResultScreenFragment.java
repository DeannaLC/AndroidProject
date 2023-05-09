package edu.vassar.cmpu203.high_noon_heist.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.high_noon_heist.R;
import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentPlayerListActionBinding;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentResultScreenBinding;

/**
 * Fragment for viewing the game results
 */
public class ResultScreenFragment extends Fragment implements IResults{

    private FragmentResultScreenBinding binding;

    private Listener listener;

    public ResultScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor for result screen
     */
    public ResultScreenFragment(Listener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentResultScreenBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    /**
     * Takes data from the listener and shows winner
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.binding.moneyStolen.setText("Money stolen: " + this.listener.getMoney() + "$");
        if (this.listener.getWin() == 2){
            this.binding.winText.setText("Bandits win!");
        }
        else
            this.binding.winText.setText("Cowboys win!");
        this.binding.menu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                ResultScreenFragment.this.listener.onGameDone();
            }
        });
    }

}