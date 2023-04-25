package edu.vassar.cmpu203.high_noon_heist.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Iterator;

/**
 * Class that contains list of players
 */
public class PlayerList{
    public ArrayList players = new ArrayList<Player>();
    public ArrayList bandits = new ArrayList<Bandit>();
    public ArrayList cowboys = new ArrayList<Cowboy>();
    
    public PlayerList(){}

    public void addCowboy(String name){
        Cowboy c = new Cowboy(name);
        players.add(c);
        cowboys.add(c);
    }

    public void addBandit(String name){
        Bandit b = new Bandit(name);
        players.add(b);
        bandits.add(b);
    }

    
    public PlayerList copyPlayers(){
        PlayerList ret = new PlayerList();
        Player p;
        for (int i = 0; i < players.size(); i = i + 1){
            p = (Player) (players.get(i));
            ret.players.add(p);
        }
        return ret;
    }
    
    public String toString(){
        String playersRes = "";//"Players: ";
        Player p;
        for (int i = 0; i < players.size(); i++){
            p = (Player) players.get(i);
            playersRes += p.name;
            playersRes += "\n";
            //if (i != (players.size() - 1))
            //    playersRes += ", ";
        }
        return playersRes;
    }

    /**
     * Finds a Player by their name
     * @param person, name of the Player
     * @return Player with name inputted, null if not found
     */
    public Player findPlayer(String person){
        Player h;
        for (int i = 0; i < players.size(); i = i + 1){
            h = (Player) players.get(i);
            if (h.getName().toLowerCase().equals(person.toLowerCase()))
                return h;
        }
        return null;
    }

    /**
     * Removes a Player from the game
     * @param p, Player being removed
     */
    public void removePlayer(Player p){
        this.players.remove(p);
        this.bandits.remove(p);
        this.cowboys.remove(p);
    }

    /*
    public int[] voteVals(){
        Player cur;
        int[] votes = new int[this.players.size()];
        for (int i = 0; i < this.players.size(); i = i + 1){
            cur = (Player) this.players.get(i);
            votes[i] = (cur.getVotes());
        }
        return votes;
    }
    */

    public String voteValsStr(){
        Player cur;
        String ret = "";
        for (int i = 0; i < this.players.size(); i = i + 1){
            cur = (Player) this.players.get(i);
            ret = ret + " " + cur.getVotes();
        }
        return ret;
    }

    public int tallyVotes(){
        int count = 0;
        Player cur;
        for (int i = 0; i < this.players.size(); i = i + 1) {
            cur = (Player) this.players.get(i);
            count = count + cur.getVotes();
        }
        return count;
    }

    public Player mostVotes(){
        Player ret = new Player("");
        Player cur;
        int max = -1;
        for (int i = 0; i < this.players.size(); i = i + 1){
            cur = (Player) this.players.get(i);
            if (cur.getVotes() > max){
                ret = cur;
                max = cur.getVotes();
            }
        }
        return ret;
    }

    public boolean checkTie(){
        int check = this.mostVotes().getVotes();
        int tally = 0;
        int cur;
        ArrayList count = this.voteVals();
        for (int i = 0; i < count.size(); i = i + 1){
            cur = (int) count.get(i);
            if (cur == check)
                tally = tally + 1;
            if (tally > 1)
                return true;
        }
        return false;
    }

    public boolean canRemove(){
        return this.tallyVotes() > this.players.size() / 2;
    }

}
