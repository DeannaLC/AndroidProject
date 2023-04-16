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

    /**
     * Takes in an ArrayList containing indices of bandits, a player's name, and the index of the player being added.
     * @param name of the new Player
     * @param bands, indices of bandits
     * @param cur, current index of Player being added
     * @return 0 if the new Player is a cowboy, 1 if the new Player is a bandit
     */

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

    public Player findPlayer(String person){
        Player h;
        for (int i = 0; i < players.size(); i = i + 1){
            h = (Player) players.get(i);
            if (h.getName().toLowerCase().equals(person.toLowerCase()))
                return h;
        }
        return null;
    }

    public void removePlayer(Player p){
        this.players.remove(p);
    }

}
