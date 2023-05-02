package edu.vassar.cmpu203.high_noon_heist.view;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;


public class HighNoonHeistFragFactory extends FragmentFactory{
    private static final String VIEW_PACKAGE = HighNoonHeistFragFactory.class.getPackage().getName(); // package where all all fragments reside
    private final MainActivity controller;

    public HighNoonHeistFragFactory(MainActivity controller){
        this.controller = controller;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
        Fragment fragment;
        fragment = new Fragment();
        switch(className) {
            case "edu.vassar.cmpu203.high_noon_heist.ConfigGameFragment":
                fragment = new ConfigGameFragment(this.controller);
                break;
            case "edu.vassar.cmpu203.high_noon_heist.AddPlayersFragment":
                fragment = new AddPlayersFragment(this.controller);
                break;
            case "edu.vassar.cmpu203.high_noon_heist.PlayerListActionFragment":
                fragment = new PlayerListActionFragment(this.controller);
                break;
            case "edu.vassar.cmpu203.high_noon_heist.ActionSelectFragment":
                fragment = new ActionSelectFragment(this.controller);
                break;
            case "edu.vassar.cmpu203.high_noon_heist.ViewObservationFragment":
                fragment = new ViewObservationFragment(this.controller);
                break;
            case "edu.vassar.cmpu203.high_noon_heist.VoteFragment":
                fragment = new VoteFragment(this.controller);
                break;
            case "edu.vassar.cmpu203.high_noon_heist.ResultScreenFragment":
                fragment = new ResultScreenFragment(this.controller);
                break;
        }
        return fragment;
    }
}
