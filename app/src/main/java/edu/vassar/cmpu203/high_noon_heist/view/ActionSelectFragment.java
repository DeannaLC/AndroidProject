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
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentActionSelectBinding;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentAddPlayersBinding;
import edu.vassar.cmpu203.high_noon_heist.model.Player;

/**
 * Fragment for a Player taking an action
 */
public class ActionSelectFragment extends Fragment implements IActionSelect{

    //Player active;
    Listener listener;
    FragmentActionSelectBinding binding;
    boolean stealing = false;
    private static final String STEALING = "stealing";
    boolean watchingPlace = false;
    private static final String WATCHINGPLACE = "watchingPlace";
    boolean onStealConfirm = false;
    private static final String ONSTEALCONFIRM = "onStealConfirm";
    boolean onWatchConfirm = false;
    private static final String ONWATCHCONFIRM = "onWatchConfirm";
    String place;
    private static final String PLACE = "place";
    int stealingVal = 0;
    private static final String STEALINGVAL = "stealingVal";

    public ActionSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor for ActionSelectFragment
     * @param listener, MainActivity which controls the game
     */
    public ActionSelectFragment(Listener listener){//Player active, Listener listener){
        //this.active = active;
        this.listener = listener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Player active = this.listener.getCurrent();
        this.binding.showNameText.setText(active.getName());
        if (active.role() == 0){
            this.cowboyAction();
        }
        else
            this.banditAction();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentActionSelectBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    /**
     * General template for a button made to observe a location
     *
     * @param place, name of the location to be observed
     * @return View.OnClickListener for a button to use
     */
    public View.OnClickListener generalObserveButton(String place) {
        View.OnClickListener ret = new View.OnClickListener() {
            public void onClick(View view) {
                ActionSelectFragment.this.place = place;
                ActionSelectFragment.this.listener.observeAt(place.toLowerCase(), ActionSelectFragment.this.listener.getCurrent());
                ActionSelectFragment.this.watchConfirm();
            }
        };
        return ret;
    }

    public void watchConfirm(){
        this.watchingPlace = false;
        this.onWatchConfirm = true;
        ActionSelectFragment.this.binding.buttonSet.removeAllViews();
        ActionSelectFragment.this.binding.splashText.setText("You chose to watch the " + this.place + " for the night");
        ActionSelectFragment.this.binding.buttonSet.addView(ActionSelectFragment.this.addConfirm());
    }

    /**
     * General template for a button made to steal from a location
     *
     * @param place, name of the location to be stolen from
     * @return View.OnClickListener for a button to use
     */
    public View.OnClickListener generalStealButton(String place){
        return new View.OnClickListener() {
            public void onClick(View view){
                ActionSelectFragment.this.place = place;
                ActionSelectFragment.this.stealConfirm();
            }
        };
    }

    public void stealConfirm(){
        this.stealing = false;
        this.onStealConfirm = true;
        if (this.stealingVal == 0)
            this.stealingVal = this.listener.stealFrom(this.place);
        this.binding.buttonSet.removeAllViews();
        this.binding.splashText.setText("You stole " + this.stealingVal + "$ from the " + this.place);
        this.binding.buttonSet.addView(ActionSelectFragment.this.addConfirm());
    }

    /**
     * Creates a confirm button
     * @return Confirm button to go to another screen
     */
    public Button addConfirm(){
        Button confirm = new MaterialButton(super.getContext());
        confirm.setText("Confirm");
        confirm.setOnClickListener(new View.OnClickListener(){
           public void onClick(View view){
               ActionSelectFragment.this.listener.onActionDone();
           }
        });
        return confirm;
    }

    /**
     * Called for a Cowboy to perform an action
     */
    public void cowboyAction() {
        this.binding.splashText.setText("Choose a place to watch for the night");
        Button bank = new MaterialButton(super.getContext());
        bank.setText("Bank");
        bank.setOnClickListener(this.generalObserveButton("bank"));
        Button ranch = new MaterialButton(super.getContext());
        ranch.setText("Ranch");
        ranch.setOnClickListener(this.generalObserveButton("ranch"));
        Button saloon = new MaterialButton(super.getContext());
        saloon.setText("Saloon");
        saloon.setOnClickListener(this.generalObserveButton("saloon"));
        this.binding.buttonSet.addView(bank);
        this.binding.buttonSet.addView(saloon);
        this.binding.buttonSet.addView(ranch);
    }

    /**
     * Called for a bandit to perform an action
     */
    public void banditAction(){
        this.binding.splashText.setText("Choose to Watch or Steal");
        Button watch = new MaterialButton(super.getContext());
        watch.setText("Watch");
        watch.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view){
               ActionSelectFragment.this.binding.buttonSet.removeAllViews();
               ActionSelectFragment.this.watchingPlace = true;
               ActionSelectFragment.this.cowboyAction();
           }
        });
        Button steal = new MaterialButton(super.getContext());
        steal.setText("Steal");
        steal.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view){
               ActionSelectFragment.this.stealingOptions();
           }
        });
        if (this.stealing == false && this.watchingPlace == false) {
            this.binding.buttonSet.addView(watch);
            this.binding.buttonSet.addView(steal);
        }
    }

    public void stealingOptions(){
        this.stealing = true;
        this.binding.splashText.setText("Choose where to Steal from");
        this.binding.buttonSet.removeAllViews();
        Button bank = new MaterialButton(ActionSelectFragment.super.getContext());
        bank.setText("Bank");
        bank.setOnClickListener(ActionSelectFragment.this.generalStealButton("bank"));
        Button saloon = new MaterialButton(ActionSelectFragment.super.getContext());
        saloon.setText("Saloon");
        saloon.setOnClickListener(ActionSelectFragment.this.generalStealButton("saloon"));
        Button ranch = new MaterialButton(ActionSelectFragment.super.getContext());
        ranch.setText("ranch");
        ranch.setOnClickListener(ActionSelectFragment.this.generalStealButton("ranch"));
        this.binding.buttonSet.addView(bank);
        this.binding.buttonSet.addView(saloon);
        this.binding.buttonSet.addView(ranch);;
    }

    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(PLACE, this.place);
        outState.putBoolean(STEALING, this.stealing);
        outState.putBoolean(WATCHINGPLACE, this.watchingPlace);
        outState.putBoolean(ONSTEALCONFIRM, this.onStealConfirm);
        outState.putBoolean(ONWATCHCONFIRM, this.onWatchConfirm);
        outState.putInt(STEALINGVAL, this.stealingVal);
    }

    public void onViewStateRestored(@NonNull Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            this.place = savedInstanceState.getString(PLACE);
            this.stealing = savedInstanceState.getBoolean(STEALING);
            this.watchingPlace = savedInstanceState.getBoolean(WATCHINGPLACE);
            this.onStealConfirm = savedInstanceState.getBoolean(ONSTEALCONFIRM);
            this.onWatchConfirm = savedInstanceState.getBoolean(ONWATCHCONFIRM);
            this.stealingVal = savedInstanceState.getInt(STEALINGVAL);
        }
        if (this.stealing)
            this.stealingOptions();
        else if (this.watchingPlace)
            this.cowboyAction();
        else if (this.onStealConfirm)
            this.stealConfirm();
        else if (this.onWatchConfirm)
            this.watchConfirm();
    }
}