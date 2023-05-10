package edu.vassar.cmpu203.high_noon_heist.view;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;

/**
 * Used for state restoration to call listener constructor instead of empty constructor
 */
public class HighNoonHeistFragFactory extends FragmentFactory{
    private static final String VIEW_PACKAGE = HighNoonHeistFragFactory.class.getPackage().getName(); // package where all all fragments reside
    private final MainActivity controller;

    public HighNoonHeistFragFactory(MainActivity controller){
        this.controller = controller;
    }


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
