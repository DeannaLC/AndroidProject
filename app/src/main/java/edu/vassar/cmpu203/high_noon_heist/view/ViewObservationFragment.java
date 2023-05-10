package edu.vassar.cmpu203.high_noon_heist.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

import edu.vassar.cmpu203.high_noon_heist.R;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentPlayerListActionBinding;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentViewObservationBinding;
import edu.vassar.cmpu203.high_noon_heist.model.Player;

/**
 * Fragment for viewing action results
 */
public class ViewObservationFragment extends Fragment implements IViewObservation {

    private FragmentViewObservationBinding binding;
    private Listener listener;
    private boolean viewingPerson = false;
    private final static String VIEWINGPERSON = "viewingPerson";
    private final static String VIEWINGNUMBER = "viewingNumber";
    private boolean viewingNumber = false;
    private final static String RESULT = "result";
    private String result = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentViewObservationBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    public ViewObservationFragment(){
    }

    public ViewObservationFragment(Listener listener){
        this.listener = listener;
    }

    /**
     * Decides what is shown based on a player's role
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Player current = this.listener.getCurrent();
        if (current.role() == 0)
            this.cowboyObservation(current);
        else
            this.banditObservation(current);
    }

    /**
     * Generates options for a Cowboy to select an observation
     * @param current, player who sees something
     */
    public void cowboyObservation(Player current){
        //Player current = this.listener.getCurrent();
        this.binding.viewPrompt.setText("View number of players or a specific player at " + current.viewLoc() + "?");
        Button number = new MaterialButton(super.getContext());
        number.setText("Number");
        number.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view){
               ViewObservationFragment.this.showNumber();
           }
        });
        Button person = new MaterialButton(super.getContext());
        person.setText("Person");
        person.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                ViewObservationFragment.this.showPerson();
            }
        });
        if (this.viewingNumber == false && this.viewingPerson == false) {
            this.binding.viewPromptButtons.addView(number);
            this.binding.viewPromptButtons.addView(person);
        }
    }

    /**
     * Shows what a bandit will see
     * @param current, bandit who views somethign
     */
    public void banditObservation(Player current){
        String text = this.listener.showObservation(0);
        if (text == null){
            this.binding.viewPrompt.setText("No other players at the " + current.viewLoc());
            this.binding.viewPromptButtons.addView(this.addConfirm());
            return;

        }
        else if (text.equals("")){
            this.binding.viewPrompt.setText("You hung out at the " + current.viewLoc() + " for the night");
        }
        else
            this.binding.viewPrompt.setText("You saw " + text + " at " + current.viewLoc());
        this.binding.viewPromptButtons.addView(this.addConfirm());
    }

    /**
     * Shows total players at someone's location
     */
    public void showNumber(){
        this.viewingNumber = true;
        this.binding.viewPromptButtons.removeAllViews();
        if (this.result.equals(""))
            this.result = this.listener.showObservation(0);
        this.binding.viewPrompt.setText("You saw " + this.result + " total at the " + this.listener.doViewLoc());
        this.binding.viewPromptButtons.addView(ViewObservationFragment.this.addConfirm());
    }

    /**
     * Shows a random person at someone's location
     */
    public void showPerson(){
        this.viewingPerson = true;
        this.binding.viewPromptButtons.removeAllViews();
        if (this.result.equals(""))
            this.result = this.listener.showObservation(1);
        if (this.result == null)
            this.binding.viewPrompt.setText("No other players at the " + this.listener.doViewLoc());
        else
            this.binding.viewPrompt.setText("You saw " + this.result + " at the " + this.listener.doViewLoc());
        this.binding.viewPromptButtons.addView(ViewObservationFragment.this.addConfirm());
    }

    /**
     * Adds a confirm button
     * @return confirm button
     */
    public Button addConfirm(){
        Button confirm = new MaterialButton(super.getContext());
        confirm.setText("Confirm");
        confirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                ViewObservationFragment.this.listener.onActionDone();
            }
        });
        return confirm;
    }

    /**
     * Saves if a player is viewing something, and what they're seeing
     * @param outState Bundle in which to place your saved state.
     */
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(VIEWINGNUMBER, this.viewingNumber);
        outState.putBoolean(VIEWINGPERSON, this.viewingPerson);
        outState.putString(RESULT, this.result);
    }

    /**
     * Restores previous fragment state
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    public void onViewStateRestored(@NonNull Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            this.viewingNumber = savedInstanceState.getBoolean(VIEWINGNUMBER);
            this.viewingPerson = savedInstanceState.getBoolean(VIEWINGPERSON);
            this.result = savedInstanceState.getString(RESULT);
        }
        if (this.viewingNumber)
            this.showNumber();
        if (this.viewingPerson)
            this.showPerson();
    }

}