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
        //super();
        this.controller = controller;
    }

    /*@NonNull
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
    }*/

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {

        // convert from class name to class
        Class<? extends Fragment> fragClass = loadFragmentClass(classLoader, className);

        // is this fragment in our view package? if so, it must be one of ours!
        if (fragClass.getPackage().getName().equals(VIEW_PACKAGE)) {
            try {
                Constructor<?>[] fcons = fragClass.getConstructors(); // get all the constructors
                assert fcons.length > 0 : "Fragment class does not have a constructor";
                return (Fragment) fcons[1].newInstance(controller); // go with first constructor
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                final String emsg = String.format("Can't instantiate %s: ensure it's concrete and " +
                        "has a public constructor with a ControllerActivity parameter", fragClass);
                Log.e("NextGenPos", emsg);
                e.printStackTrace();
            }
        }

        // default is to delegate to superclass
        return super.instantiate(classLoader, className);
    }
}
