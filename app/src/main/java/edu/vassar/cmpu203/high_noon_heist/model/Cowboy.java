package edu.vassar.cmpu203.high_noon_heist.model;

import java.util.Random;
import java.util.ArrayList;

/**
 * Write a description of class Cowboy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Cowboy extends Player
{

    /**
     * Constructor for objects of class Cowboy
     */
    public Cowboy(String name)
    {
        super(name);
    }
    
    public String observation(Location l, int a){
        String b;
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

    public int role(){
        return 0;
    }

    public String displayRole(){
        return "Your role is Cowboy";
    }
}
