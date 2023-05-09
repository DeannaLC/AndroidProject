package edu.vassar.cmpu203.high_noon_heist.model;

import android.os.Bundle;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;

/**
 * Cowboy class, able to make observations
 */
public class Cowboy extends Player implements Serializable
{

    /**
     * Constructor for objects of class Cowboy
     */
    public Cowboy(String name)
    {
        super(name);
    }

    /**
     * Allows a cowboy to get information from a location
     * @param l, Location they're getting info from
     * @param a, 0 if they get amount of people, 1 if they get a random other name
     * @return
     */
    public String observation(Location l, int a){
        Player retPlayer;
        if (a == 0){
            if ((this.loc).equals("bank"))
                return  "" + (l.bank).size();
            else if ((this.loc).equals("saloon"))
                return "" + (l.saloon).size();
            else
                return "" + (l.ranch).size();
        }
        else {
            retPlayer = l.randPlayer(this.name, this.loc);
            if (retPlayer == null)
                return null;
            return (l.randPlayer(this.name, this.loc)).name;
        }
    }

    /**
     * Identifier for Cowboy
     * @return 0 to show Player is a Cowboy
     */
    public int role(){
        return 0;
    }

    public String displayRole(){
        return "Your role is Cowboy";
    }

    public Bundle toBundle(){
        final Bundle b = new Bundle();
        b.putString(NAME, this.name);
        b.putString(LOCATION, this.loc);
        b.putInt(VOTES, this.votes);
        b.putString(ROLE, "cowboy");
        return b;
    }

    public static Cowboy fromBundle(@NonNull Bundle b){
        final String name = b.getString(NAME);
        final String loc = b.getString(LOCATION);
        final int votes = b.getInt(VOTES);

        Cowboy ret = new Cowboy(name);
        ret.loc = loc;
        ret.votes = votes;

        return ret;
    }
}
