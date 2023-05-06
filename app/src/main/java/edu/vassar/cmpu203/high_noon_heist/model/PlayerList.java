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
 * Class that contains list of players
 */
public class PlayerList implements Serializable {
    public ArrayList players = new ArrayList<Player>();
    public ArrayList bandits = new ArrayList<Bandit>();
    public ArrayList cowboys = new ArrayList<Cowboy>();

    private static final String PLAYERS = "players";
    private static final String BANDITS = "bandits";
    private static final String COWBOYS = "cowboys";
    
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
            if (p.role() == 1)
                ret.bandits.add(p);
            else
                ret.cowboys.add(p);
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

    public ArrayList voteVals(){
        Player cur;
        ArrayList votes = new ArrayList();
        for (int i = 0; i < this.players.size(); i = i + 1){
            cur = (Player) this.players.get(i);
            votes.add(cur.getVotes());
        }
        return votes;
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

    /*public String voteValsStr(){
        Player cur;
        String ret = "";
        for (int i = 0; i < this.players.size(); i = i + 1){
            cur = (Player) this.players.get(i);
            ret = ret + " " + cur.getVotes();
        }
        return ret;
    }*/

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

    public void removeByName(String name){
        Player removing = this.findPlayer(name);
        this.players.remove(removing);
        this.bandits.remove(removing);
        this.cowboys.remove(removing);
    }

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

    public static PlayerList fromBundle(@NonNull Bundle b){
        final PlayerList playerList = new PlayerList();
        for (Parcelable playerParcelable : b.getParcelableArray(PLAYERS)){
            /*Player p = Player.fromBundle((Bundle) playerParcelable);
            playerList.players.add(p);
            if (p.role() == 1){
                playerList.bandits.add(p);
            }
            else
                playerList.cowboys.add(p);*/
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

}
