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
public class ResultScreenFragment extends Fragment {

    private FragmentResultScreenBinding binding;

    private MainActivity data;

    public ResultScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor for result screen
     * @param data to get game data
     */
    public ResultScreenFragment(MainActivity data){
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentResultScreenBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.binding.moneyStolen.setText("Money stolen: " + this.data.getMoney() + "$");
        if (this.data.checkWin() == 2){
            this.binding.winText.setText("Bandits win!");
        }
    }

}