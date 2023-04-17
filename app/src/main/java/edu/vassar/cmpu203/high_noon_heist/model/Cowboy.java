package edu.vassar.cmpu203.high_noon_heist.model;

import java.util.Random;
import java.util.ArrayList;

public class Cowboy extends Player
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
}
