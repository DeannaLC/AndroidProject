package edu.vassar.cmpu203.high_noon_heist.view;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import edu.vassar.cmpu203.high_noon_heist.databinding.ActivityMainBinding;

public class AddPlayersView {
    ActivityMainBinding binding;

    public AddPlayersView(@NonNull Context context) {

        // inflate the layout
        this.binding = ActivityMainBinding.inflate(LayoutInflater.from(context));

    }

}
