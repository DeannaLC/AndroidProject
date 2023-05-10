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

    /**
     * Gives player's name
     * @return name field
     */
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

    /**
     * Identifier for Player's role
     * @return 3 showing player, won't ever be reached in play
     */
    public int role(){
        return 3;
    }

    /**
     * Sets number of votes for a player
     * @param votes, number of votes to have
     */
    public void vote(int votes){
        this.votes = votes;
    }

    /**
     * Shows location a Player is in
     * @return loc field
     */
    public String viewLoc(){
        return this.loc;
    }

    /**
     * General observation function
     *
     * @param loc, Location they're added to
     * @param a, choice on what they see if they're a Cowboy
     * @return empty string since Player won't use this
     */
    public String observation(Location loc, int a){
        return "";
    }

    /**
     * General rob function
     * @param l, Location they'll be added to and rob from
     * @param place they're going to
     * @return 0 since this case isn't used
     */
    public int rob(Location l, String place){
        return 0;
    };

    /**
     * String identifier for a role
     * @return "hi" since not reached in game
     */
    public String displayRole(){
        return "hi";
    }

    /**
     * Gives number of votes a player has
     * @return votes field
     */
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

    /**
     * Turns Player object into a bundle
     * @return empty Bundle since not used
     */
    public Bundle toBundle(){
        return new Bundle();
    }

    /**
     * Sets player's votes back to 0
     */
    public void resetVotes(){
        this.votes = 0;
    }

    /**
     * Updates location a player is at
     * @param loc, new loc field
     */
    public void updateLoc(String loc){
        this.loc = loc;
    }

    /**
     * Checks to see if a bundle is a Cowboy or Bandit
     * @param b, bundle being checked
     * @return true if bandit, false if cowboy
     */
    public static boolean checkBundleRole(Bundle b){
        return b.getString(ROLE).equals("bandit");
    }
}
