package edu.vassar.cmpu203.high_noon_heist.model;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Class for displaying previous wins
 *
 * Aggregation of winners
 */
public class Leaderboard implements Serializable {
    ArrayList winners = new ArrayList<>();
    final static String WINNERS = "winners";

    public Leaderboard(){}

    /**
     * Adds a Winner to winners field
     * @param win, Winner being added
     */
    public void addWinner(Winner win){
        this.winners.add(win);
    }

    /**
     * Shows all the Winners in the class
     * @return String with all the winners
     */
    public String toString(){
        String ret = "";
        for (int i = 0; i < winners.size(); i = i + 1){
            ret = ret + winners.get(i).toString() + "\n";
        }
        return ret;
    }

    /**
     * Puts Leaderboard into a bundle
     * @return Bundle with leaderboard data
     */
    public Bundle toBundle(){
        Bundle b = new Bundle();
        final Bundle[] winnerBundle = new Bundle[this.winners.size()];
        Winner cur;
        for (int i = 0; i < winners.size(); i = i + 1) {
            cur = (Winner) winners.get(i);
            winnerBundle[i] = cur.toBundle();
        }
        b.putParcelableArray(WINNERS, winnerBundle);
        return b;
    }

    /**
     * Retrieves leaderboard from a bundle
     * @param b, bundle data is retrieved from
     * @return Leaderboard from bundle
     */
    public static Leaderboard fromBundle(Bundle b){
        final Leaderboard ret = new Leaderboard();
        for (Parcelable winnerParcelable : b.getParcelableArray(WINNERS)){
            Bundle x = (Bundle) winnerParcelable;
            ret.winners.add(Winner.fromBundle(x));
        }
        return ret;
    }
}
