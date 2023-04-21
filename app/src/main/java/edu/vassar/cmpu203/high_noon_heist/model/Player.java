package edu.vassar.cmpu203.high_noon_heist.model;

/**
 * Superclass for Bandit and Cowboy subclasses
 */
public class Player {
    boolean alive = true;
    String name;
    String loc;
    int votes;

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

    public void observe(Location l, String loc) {
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

    //public void rob(){}

    public void vote(int votes){
        this.votes = votes;
    }
    
    void clearVotes(){
        this.votes = 0;
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

    public void addVote(){
        this.votes = this.votes + 1;
    }

    public void subVote(){
        if (this.votes != 0)
            this.votes = this.votes - 1;
    }
}
