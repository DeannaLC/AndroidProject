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

    private Player current;

    private Listener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentViewObservationBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    public ViewObservationFragment(){
    }

    public ViewObservationFragment(Listener listener, Player current){
        this.listener = listener;
        this.current = current;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (current.role() == 0)
            this.cowboyObservation();
        else
            this.banditObservation();
    }

    public void cowboyObservation(){
        this.binding.viewPrompt.setText("View number of players or a specific player at " + this.current.viewLoc() + "?");
        Button number = new MaterialButton(super.getContext());
        number.setText("Number");
        number.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view){
               ViewObservationFragment.this.binding.viewPromptButtons.removeAllViews();
               String res = ViewObservationFragment.this.listener.showObservation(0);
               ViewObservationFragment.this.binding.viewPrompt.setText("You saw " + res + " total at the " + ViewObservationFragment.this.current.viewLoc());
               ViewObservationFragment.this.binding.viewPromptButtons.addView(ViewObservationFragment.this.addConfirm());
           }
        });
        Button person = new MaterialButton(super.getContext());
        person.setText("Person");
        person.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                ViewObservationFragment.this.binding.viewPromptButtons.removeAllViews();
                String res = ViewObservationFragment.this.listener.showObservation(1);
                if (res == null)
                    ViewObservationFragment.this.binding.viewPrompt.setText("No other players at the " + ViewObservationFragment.this.current.viewLoc());
                else
                    ViewObservationFragment.this.binding.viewPrompt.setText("You saw " + res + " at the " + ViewObservationFragment.this.current.viewLoc());
                ViewObservationFragment.this.binding.viewPromptButtons.addView(ViewObservationFragment.this.addConfirm());
            }
        });
        this.binding.viewPromptButtons.addView(number);
        this.binding.viewPromptButtons.addView(person);
    }

    public void banditObservation(){
        String text = this.listener.showObservation(0);
        if (text == null){
            this.binding.viewPrompt.setText("No other players at the " + this.current.viewLoc());
            this.binding.viewPromptButtons.addView(this.addConfirm());
            return;

        }
        else if (text.equals("")){
            this.binding.viewPrompt.setText("You hung out at the " + this.current.viewLoc() + " for the night");
        }
        else
            this.binding.viewPrompt.setText("You saw " + text + " at " + this.current.viewLoc());
        this.binding.viewPromptButtons.addView(this.addConfirm());
    }

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

}