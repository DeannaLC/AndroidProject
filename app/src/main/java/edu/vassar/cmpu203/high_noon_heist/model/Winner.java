package edu.vassar.cmpu203.high_noon_heist.model;

import android.os.Bundle;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Class saving who won and the time they won
 */
public class Winner implements Serializable {
    boolean banditWin = false;
    boolean cowboyWin = false;
    final static String BANDITWIN = "banditWin";
    final static String COWBOYWIN = "cowboyWin";
    final static String DATE = "date";
    String date;
    Date winDate;

    /**
     * Empty constructor for Winner
     */
    public Winner(){
        this.winDate = Calendar.getInstance().getTime();
    }

    /**
     * Sets a bandit winning and dates when it happens
     */
    public void setBanditWin(){
        date = this.winDate.toString();
        this.banditWin = true;
    }

    /**
     * Sets a cowboy win and dates when it happens
     */
    public void setCowboyWin(){
        date = this.winDate.toString();
        this.cowboyWin = true;
    }

    /**
     * Shows who won and at what time
     * @return String showing winner and time
     */
    public String toString(){
        if (this.banditWin)
            return "Bandits won on " + this.date;
        return "Cowboys won on " + this.date;
    }

    /**
     * Puts Winner object into a bundle
     * @return Bundle with Winner object data
     */
    public Bundle toBundle(){
        final Bundle retBundle = new Bundle();
        retBundle.putBoolean(BANDITWIN, this.banditWin);
        retBundle.putBoolean(COWBOYWIN, this.cowboyWin);
        retBundle.putString(DATE, this.date);

        return retBundle;
    }

    /**
     * Gets a Winner from a Bundle
     * @param b, Bundle data is retrieved from
     * @return Winner from b's data
     */
    public static Winner fromBundle(Bundle b){
        final boolean banditWin = b.getBoolean(BANDITWIN);
        final boolean cowboyWin = b.getBoolean(COWBOYWIN);
        final String date = b.getString(DATE);

        Winner x = new Winner();
        x.banditWin = banditWin;
        x.cowboyWin = cowboyWin;
        x.date = date;
        return x;
    }
}
