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

    public ActionSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor for ActionSelectFragment
     * @param active, current player who's making an action
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
                ActionSelectFragment.this.listener.observeAt(place.toLowerCase(), ActionSelectFragment.this.listener.getCurrent());
                ActionSelectFragment.this.binding.buttonSet.removeAllViews();
                ActionSelectFragment.this.binding.splashText.setText("You chose to watch the " + place + " for the night");
                ActionSelectFragment.this.binding.buttonSet.addView(ActionSelectFragment.this.addConfirm());
            }
        };
        return ret;
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
                int val = ActionSelectFragment.this.listener.stealFrom(place);
                ActionSelectFragment.this.binding.buttonSet.removeAllViews();
                ActionSelectFragment.this.binding.splashText.setText("You stole " + val + "$ from the " + place);
                ActionSelectFragment.this.binding.buttonSet.addView(ActionSelectFragment.this.addConfirm());
            }
        };
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
               ActionSelectFragment.this.cowboyAction();
           }
        });
        Button steal = new MaterialButton(super.getContext());
        steal.setText("Steal");
        steal.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view){
               ActionSelectFragment.this.binding.splashText.setText("Choose where to Steal from");
               ActionSelectFragment.this.binding.buttonSet.removeAllViews();
               Button bank = new MaterialButton(ActionSelectFragment.super.getContext());
               bank.setText("Bank");
               bank.setOnClickListener(ActionSelectFragment.this.generalStealButton("bank"));
               Button saloon = new MaterialButton(ActionSelectFragment.super.getContext());
               saloon.setText("Saloon");
               saloon.setOnClickListener(ActionSelectFragment.this.generalStealButton("saloon"));
               Button ranch = new MaterialButton(ActionSelectFragment.super.getContext());
               ranch.setText("ranch");
               ranch.setOnClickListener(ActionSelectFragment.this.generalStealButton("ranch"));
               ActionSelectFragment.this.binding.buttonSet.addView(bank);
               ActionSelectFragment.this.binding.buttonSet.addView(saloon);
               ActionSelectFragment.this.binding.buttonSet.addView(ranch);;
           }
        });
        this.binding.buttonSet.addView(watch);
        this.binding.buttonSet.addView(steal);
    }
}