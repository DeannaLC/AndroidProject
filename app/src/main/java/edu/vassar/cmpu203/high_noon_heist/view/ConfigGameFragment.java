package edu.vassar.cmpu203.high_noon_heist.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.high_noon_heist.R;
import edu.vassar.cmpu203.high_noon_heist.controller.MainActivity;
import edu.vassar.cmpu203.high_noon_heist.databinding.FragmentConfigGameBinding;

/**
 * Fragment for configuring the game
 */
public class ConfigGameFragment extends Fragment implements IConfigGame{

    private FragmentConfigGameBinding binding;

    private Listener listener;

    public ConfigGameFragment() {}

    public ConfigGameFragment(Listener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.binding = FragmentConfigGameBinding.inflate(inflater);
        return this.binding.getRoot(); // return top level view
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.binding.configGameButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Listener method to be called when the add item button is clicked.
             *
             * @param view the view that was clicked on
             */
            @Override
            public void onClick(View view) {
                Editable playerTotalEditable = ConfigGameFragment.this.binding.getPlayerEditable.getText();
                String playerTotalStr = playerTotalEditable.toString();
                Editable banditTotalEditable = ConfigGameFragment.this.binding.getBanditEditable.getText();
                String banditTotalStr = banditTotalEditable.toString();
                Editable dayLimEditable = ConfigGameFragment.this.binding.getDayEditable.getText();
                String dayLimStr = dayLimEditable.toString();
                Editable moneyLimEditable = ConfigGameFragment.this.binding.getMoneyEditable.getText();
                String moneyLimStr = moneyLimEditable.toString();

                if (playerTotalStr.length() == 0 || banditTotalStr.length() == 0 || dayLimStr.length() == 0 || moneyLimStr.length() == 0) {
                    Snackbar sb = Snackbar.make(view, "need all fields filled", Snackbar.LENGTH_LONG);
                    sb.show();
                    return;
                }

                int playerTotal = Integer.parseInt(playerTotalStr);
                int banditAmt = Integer.parseInt(banditTotalStr);
                int dayLim = Integer.parseInt(dayLimStr);
                int moneyLim = Integer.parseInt(moneyLimStr);

                if (playerTotal % 2 == 0) {
                    if (banditAmt >= playerTotal / 2) {
                        Snackbar sb = Snackbar.make(view, "too many bandits", Snackbar.LENGTH_LONG);
                        sb.show();
                        return;
                    }
                }
                else {
                    if (banditAmt > playerTotal / 2) {
                        Snackbar sb = Snackbar.make(view, "too many bandits", Snackbar.LENGTH_LONG);
                        sb.show();
                        return;
                    }
                }

                ConfigGameFragment.this.listener.onSetOptions(playerTotal, banditAmt, dayLim, moneyLim, ConfigGameFragment.this);
            }
        });
    }

    /**
     * Displays game options after they've been set
     * @param m, for displaying the configuration
     */
    @Override
    public void showConfig(MainActivity m){
        this.binding.showOptions.setText(m.toString());

        if (this.binding.nextButtons.getChildCount() == 1) {

            Button toAddPlayers = new MaterialButton(super.getContext());
            toAddPlayers.setText("Next");

            toAddPlayers.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ConfigGameFragment.this.listener.onOptionsSet();
                }
            });
            this.binding.nextButtons.addView(toAddPlayers);
        }

    }
}
