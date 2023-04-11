package edu.vassar.cmpu203.high_noon_heist.view;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.vassar.cmpu203.high_noon_heist.databinding.MainScreenBinding;

public class MainView implements IMainView{
    FragmentManager fmanager;
    MainScreenBinding binding;

    public MainView(FragmentActivity activity){
        this.fmanager = activity.getSupportFragmentManager();
        this.binding = MainScreenBinding.inflate(activity.getLayoutInflater());
    }

    public View getRootView() {
        return this.binding.getRoot();
    }

    public void displayFragment(Fragment fragment, boolean reversible, String name) {
        FragmentTransaction ft = fmanager.beginTransaction()
                .replace(this.binding.fragmentContainerView.getId(), fragment);

        if (reversible) ft.addToBackStack(name);
        ft.commit(); // execute transaction
    }

}
