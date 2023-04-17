package edu.vassar.cmpu203.high_noon_heist.model;

import java.util.Random;

/**
 * Bandit class
 */
public class Bandit extends Player
{

    public boolean robbed = false;

    /**
     * Constructor for objects of class Bandit
     */
    public Bandit(String name)
    {
        super(name);
    }

    /**
     * Allows a bandit to get information from a Location
     *
     * @param l, Location they're going to
     * @param a, doesn't do anything but there to satisfy superclass
     * @return
     */
    public String observation(Location l, int a){
        //int a doesn't do anything, there to satisfy polymorphism requirement
        if (robbed)
            return "";
        Player retPlayer;
        Random rnd = new Random();
        int b = rnd.nextInt(2);
        if (b == 0){
            if ((this.loc).equals("bank"))
                return  "" + (l.bank).size();
            else if ((this.loc).equals("saloon"))
                return "" + (l.saloon).size();
            else
                return "" + (l.ranch).size();
        }
        else
            retPlayer = l.randPlayer(this.name, this.loc);
        if (retPlayer == null)
            return null;
        return (l.randPlayer(this.name, this.loc)).name;
    }

    /**
     * Gives a number showing what role a Player is
     * @return 1, which bandits are defined as
     */
    public int role(){
        return 1;
    }

    /**
     * Allows a bandit to take money from a Location
     * @param l, Location they're getting money from
     * @param place, name of the Location they're getting money from
     * @return an integer on how much they've stolen
     */
    public int rob(Location l, String place){
        this.robbed = true;
        if (place.equals("bank")) {
            this.loc = "bank";
            l.bank.add(this);
        }
        else if (place.equals("saloon")) {
            l.saloon.add(this);
            this.loc = "saloon";
        }
        else {
            l.ranch.add(this);
            this.loc = "ranch";
        }
        return l.getValue(place);
    }

    public String displayRole(){
        return "Your role is Bandit";
    }
}

