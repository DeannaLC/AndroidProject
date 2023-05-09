package edu.vassar.cmpu203.high_noon_heist.model;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Superclass for Bandit and Cowboy subclasses
 */
public class Player implements Serializable {
    String name;
    String loc;
    int votes;
    protected static final String VOTES = "votes";
    protected static final String LOCATION = "loc";
    protected static final String NAME = "name";
    protected static final String ROLE = "role";


    /**
     * General Player constructor
     * @param name of Player
     */
    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Puts a player in a location for them to observe
     * @param l, location object they're added to
     * @param loc, name of location they're going to
     */
    public void observe(Location l, String loc){
        this.loc = loc;
        if (loc.equals("bank")){
            (l.bank).add(this);
        }
        else if (loc.equals("saloon"))
            (l.saloon).add(this);
        else
            (l.ranch).add(this);
    }

    public int role(){
        return 3;
    }

    public void vote(int votes){
        this.votes = votes;
    }

    public String viewLoc(){
        return this.loc;
    }

    public String observation(Location loc, int a){
        return "";
    }

    public int rob(Location l, String place){
        return 0;
    };
    public String displayRole(){
        return "hi";
    }

    public int getVotes(){
        return this.votes;
    }

    /**
     * Adds 1 vote
     */
    public void addVote(){
        this.votes = this.votes + 1;
    }

    /**
     * Removes 1 vote if over 0
     */
    public void subVote(){
        if (this.votes != 0)
            this.votes = this.votes - 1;
    }

    public Bundle toBundle(){
        return new Bundle();
    }

    /**
     * Sets player's votes back to 0
     */
    public void resetVotes(){
        this.votes = 0;
    }

    public void updateLoc(String loc){
        this.loc = loc;
    }

    public static Player fromBundle(Bundle b){
        return new Player("");
    }

    public static boolean checkBundleRole(Bundle b){
        return b.getString(ROLE).equals("bandit");
    }
}
