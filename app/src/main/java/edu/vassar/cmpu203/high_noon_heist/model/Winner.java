package edu.vassar.cmpu203.high_noon_heist.model;

import android.os.Bundle;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Winner implements Serializable {
    boolean banditWin = false;
    boolean cowboyWin = false;
    final static String BANDITWIN = "banditWin";
    final static String COWBOYWIN = "cowboyWin";
    final static String DATE = "date";
    String date;
    Date winDate;

    public Winner(){
        this.winDate = Calendar.getInstance().getTime();
    }

    public void setBanditWin(){
        date = this.winDate.toString();
        this.banditWin = true;
    }

    public void setCowboyWin(){
        date = this.winDate.toString();
        this.cowboyWin = true;
    }

    public String toString(){
        if (this.banditWin)
            return "Bandits won on " + this.date;
        return "Cowboys won on " + this.date;
    }

    public Bundle toBundle(){
        final Bundle retBundle = new Bundle();
        retBundle.putBoolean(BANDITWIN, this.banditWin);
        retBundle.putBoolean(COWBOYWIN, this.cowboyWin);
        retBundle.putString(DATE, this.date);

        return retBundle;
    }

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
