package edu.vassar.cmpu203.high_noon_heist.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Write a description of class Locations here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Location
{
    ArrayList<Player> bank = new ArrayList<Player>();
    ArrayList<Player> saloon = new ArrayList<Player>();
    ArrayList<Player> ranch = new ArrayList<Player>();

    /**
     * Constructor for objects of class Locations
     */
    public Location(){}
    
    /*public void addPlayers(){
        bank.add(new Player("jeff"));
        bank.add(new Player("geoff"));
        bank.add(new Player("jeffrey"));
        bank.add(new Player("jebediah"));
    }*/
    
    public void clearLocs(){
        this.bank = new ArrayList<Player>();
        this.saloon = new ArrayList<Player>();
        this.ranch = new ArrayList<Player>();
    }

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
    
    public int getValue(String place){
        Random rnd = new Random();
        if (place.equals("bank"))
            return 2000 - rnd.nextInt(501);
        else if (place.equals("saloon"))
            return 1500 - rnd.nextInt(501);
        else
            return 1000 - rnd.nextInt(501);
    }
}
