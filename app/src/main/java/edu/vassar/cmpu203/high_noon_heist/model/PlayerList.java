package edu.vassar.cmpu203.high_noon_heist.model;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Iterator;

/**
 * Class that contains lists of Players
 */
public class PlayerList implements Serializable {
    public ArrayList players = new ArrayList<Player>();
    public ArrayList bandits = new ArrayList<Bandit>();
    public ArrayList cowboys = new ArrayList<Cowboy>();

    private static final String PLAYERS = "players";
    private static final String BANDITS = "bandits";
    private static final String COWBOYS = "cowboys";
    
    public PlayerList(){}

    /**
     * Adds a cowboy to the PlayerList object
     * @param name of cowboy being added
     */
    public void addCowboy(String name){
        Cowboy c = new Cowboy(name);
        players.add(c);
        cowboys.add(c);
    }

    /**
     * Adds a bandit to the PlayerList object
     * @param name of bandit being added
     */
    public void addBandit(String name){
        Bandit b = new Bandit(name);
        players.add(b);
        bandits.add(b);
    }

    /**
     * Creates a new PlayerList containing the same Player objects
     * @return New PlayerList with same Player objects as original
     */
    public PlayerList copyPlayers(){
        PlayerList ret = new PlayerList();
        Player p;
        for (int i = 0; i < players.size(); i = i + 1){
            p = (Player) (players.get(i));
            ret.players.add(p);
            if (p.role() == 1)
                ret.bandits.add(p);
            else
                ret.cowboys.add(p);
        }
        return ret;
    }

    /**
     * Displays all the players in PlayerList object
     * @return String showing all the players
     */
    public String toString(){
        String playersRes = "";
        Player p;
        for (int i = 0; i < players.size(); i++){
            p = (Player) players.get(i);
            playersRes += p.name;
            playersRes += "\n";
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

    /**
     * Puts player's votes into an int array
     * @return int[] of players' votes
     */
    public int[] voteVals(){
        Player cur;
        int[] votes = new int[this.players.size()];
        for (int i = 0; i < this.players.size(); i = i + 1){
            cur = (Player) this.players.get(i);
            votes[i] = (cur.getVotes());
        }
        return votes;
    }

    /**
     * Tallies votes for all players
     * @return total number of votes
     */
    public int tallyVotes(){
        int count = 0;
        Player cur;
        for (int i = 0; i < this.players.size(); i = i + 1) {
            cur = (Player) this.players.get(i);
            count = count + cur.getVotes();
        }
        return count;
    }

    /**
     * Gives the Player with the most votes
     * @return Player with most votes
     */
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

    /**
     * Checks if there is a tie between the player with the most votes
     * @return true or false if there's a most votes tie
     */
    public boolean checkTie(){
        int check = this.mostVotes().getVotes();
        int tally = 0;
        int cur;
        int[] count = this.voteVals();
        for (int i = 0; i < count.length; i = i + 1){
            cur = (int) count[i];
            if (cur == check)
                tally = tally + 1;
            if (tally > 1)
                return true;
        }
        return false;
    }

    /**
     * Checks if a player can be removed based on if over half the players have voted
     * @return boolean if a player can be removed or not
     */
    public boolean canRemove(){
        return this.tallyVotes() > (this.players.size() / 2);
    }

    /**
     * Puts PlayerList object into a bundle
     * @return Bundle containing PlayerList data
     */
    public Bundle toBundle(){
        final Bundle b = new Bundle();
        final Bundle[] playerBundle = new Bundle[this.players.size()];
        final Bundle[] banditBundle = new Bundle[this.bandits.size()];
        final Bundle[] cowboyBundle = new Bundle[this.cowboys.size()];

        int bCursor = 0;
        int cCursor = 0;
        Player cur;
        for (int i = 0; i < this.players.size(); i = i + 1){
            cur = (Player) this.players.get(i);
            playerBundle[i] = cur.toBundle();
            if (cur.role() == 1){
                banditBundle[bCursor] = cur.toBundle();
                bCursor = bCursor + 1;
            }
            else{
                cowboyBundle[cCursor] = cur.toBundle();
                cCursor = cCursor + 1;
            }

        }
        b.putParcelableArray(PLAYERS, playerBundle);
        b.putParcelableArray(BANDITS, banditBundle);
        b.putParcelableArray(COWBOYS, cowboyBundle);

        return b;
    }

    /**
     * Gets a PlayerList from a bundle
     * @param b, bundle data is retrieved from
     * @return PlayerList from the Bundle data
     */
    public static PlayerList fromBundle(@NonNull Bundle b){
        final PlayerList playerList = new PlayerList();
        for (Parcelable playerParcelable : b.getParcelableArray(PLAYERS)){
            Bundle x = (Bundle) playerParcelable;
            if (x.getString("role").equals("bandit")){
                Bandit band = Bandit.fromBundle((Bundle) playerParcelable);
                playerList.players.add(band);
                playerList.bandits.add(band);
            }
            else{
                Cowboy cow = Cowboy.fromBundle((Bundle) playerParcelable);
                playerList.players.add(cow);
                playerList.cowboys.add(cow);
            }
        }
        return playerList;
    }

    /**
     * Sets votes of every player back to 0
     */
    public void resetAllVotes(){
        Player cur;
        for (int i = 0; i < this.players.size(); i = i + 1){
            cur = (Player) this.players.get(i);
            cur.resetVotes();
        }
    }
}
