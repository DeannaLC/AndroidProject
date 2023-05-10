package edu.vassar.cmpu203.high_noon_heist.model;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class for storing Players who go to different locations
 * Can show amount of players at different locations, specific names, and how much they steal
 */
public class Location implements Serializable
{
    ArrayList<Player> bank = new ArrayList<Player>();
    ArrayList<Player> saloon = new ArrayList<Player>();
    ArrayList<Player> ranch = new ArrayList<Player>();

    private static final String BANK = "bank";
    private static final String SALOON = "saloon";
    private static final String RANCH = "ranch";

    /**
     * Constructor for objects of class Locations
     */
    public Location(){}

    /**
     * Clears players in all the places
     */
    public void clearLocs(){
        this.bank = new ArrayList<Player>();
        this.saloon = new ArrayList<Player>();
        this.ranch = new ArrayList<Player>();
    }

    /**
     * Gets a random Player from one of the location
     * @param name of Player checking
     * @param place, the location a Player is picked from
     * @return
     */
    public Player randPlayer(String name, String place){
        boolean repeat = true;
        Random rnd = new Random();
        int a;
        if (place.equals("bank")){
            while (repeat){
                if (this.bank.size() == 1)
                    return null;
                a = rnd.nextInt((this.bank).size());
                Player ret = ((this.bank).get(a));
                if (ret.name != name)
                    return ret;
            }
        }
        else if (place.equals("saloon")){
            while (repeat){
                if (this.saloon.size() == 1)
                    return null;
                a = rnd.nextInt((this.saloon).size());
                Player ret = ((this.saloon).get(a));
                if (ret.name != name)
                    return ret;
            }
        }
        else{
            while (repeat){
                if (this.ranch.size() == 1)
                    return null;
                a = rnd.nextInt((this.ranch).size());
                Player ret = ((this.ranch).get(a));
                if (ret.name != name)
                    return ret;
            }
        }
        //satisfy return value, will never reach this
        return new Player("");
    }

    /**
     * Gets the amount being stolen from a place
     * @param place, where money is stolen from
     * @return the amount stolen
     */
    public int getValue(String place){
        Random rnd = new Random();
        if (place.equals("bank"))
            return 2000 - rnd.nextInt(501);
        else if (place.equals("saloon"))
            return 1500 - rnd.nextInt(501);
        else
            return 1000 - rnd.nextInt(501);
    }

    /**
     * Puts Location object into a bundle
     * @return Bundle with location data
     */
    public Bundle toBundle(){
        Bundle b = new Bundle();
        final Bundle[] bankBundle = new Bundle[this.bank.size()];
        final Bundle[] saloonBundle = new Bundle[this.saloon.size()];
        final Bundle[] ranchBundle = new Bundle[this.ranch.size()];

        int i = 0;
        for (Player p : this.bank) {
            bankBundle[i++] = p.toBundle();
        }
        i = 0;

        for (Player p : this.saloon){
            saloonBundle[i++] = p.toBundle();
        }
        i = 0;

        for (Player p : this.ranch){
            ranchBundle[i++] = p.toBundle();
        }

        b.putParcelableArray(BANK, bankBundle);
        b.putParcelableArray(SALOON, saloonBundle);
        b.putParcelableArray(RANCH, ranchBundle);

        return b;
    }

    /**
     * Puts a Player in a location
     * @param p, Player being added
     * @param place they're added to
     */
    public void addTo(Player p, String place){
        if (place.equals("bank"))
            this.bank.add(p);
        else if (place.equals("saloon"))
            this.saloon.add(p);
        else
            this.ranch.add(p);
    }

    /**
     * Gets a location object from a bundle
     * @param b, bundle data is retrieved from
     * @return Location from bundle
     */
    public static Location fromBundle(@NonNull Bundle b){
        Location ret = new Location();
        for (Parcelable bankPerson : b.getParcelableArray(BANK)) {
            Bundle x = (Bundle) bankPerson;
            if (x.getString("role").equals("cowboy"))
                ret.bank.add(Cowboy.fromBundle((Bundle) bankPerson));
            else
                ret.bank.add(Bandit.fromBundle((Bundle) bankPerson));
        }
        for (Parcelable saloonPerson : b.getParcelableArray(SALOON)) {
            Bundle x = (Bundle) saloonPerson;
            if (x.getString("role").equals("cowboy"))
                ret.saloon.add(Cowboy.fromBundle((Bundle) saloonPerson));
            else
                ret.saloon.add(Bandit.fromBundle((Bundle) saloonPerson));
        }
        for (Parcelable ranchPerson : b.getParcelableArray(RANCH)) {
            Bundle x = (Bundle) ranchPerson;
            if (x.getString("role").equals("cowboy"))
                ret.ranch.add(Cowboy.fromBundle((Bundle) ranchPerson));
            else
                ret.ranch.add(Bandit.fromBundle((Bundle) ranchPerson));
        }
        return ret;
    }

    /**
     * Gives location a player is in
     * @param name of player being checked
     * @return String of location they're at
     */
    public String inLocation(String name){
        for (int i = 0; i < this.bank.size(); i = i + 1) {
            if (name.equals(this.bank.get(i).getName()))
                return "bank";
        }
        for (int i = 0; i < this.saloon.size(); i = i + 1) {
            if (name.equals(this.saloon.get(i).getName()))
                return "saloon";
        }
        for (int i = 0; i < this.ranch.size(); i = i + 1) {
            if (name.equals(this.ranch.get(i).getName()))
                return "ranch";
        }
        return "none";
    }

}
