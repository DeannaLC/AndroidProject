package edu.vassar.cmpu203.high_noon_heist.view;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface IMainView {

    View getRootView();

    void displayFragment(Fragment fragment, boolean reversible, String name);
}
