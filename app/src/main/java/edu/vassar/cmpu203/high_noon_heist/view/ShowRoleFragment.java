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
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentShowRoleBinding;
import edu.vassar.cmpu203.high_noon_heist.model.Player;

public class ShowRoleFragment extends Fragment implements IShowRole {

    private FragmentShowRoleBinding binding;
    private Listener listener;

    public ShowRoleFragment() {
    }

    public ShowRoleFragment(Listener listener){
        this.listener = listener;
    }



    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        this.binding.playerRole.setText(this.listener.showRole());
    }


}