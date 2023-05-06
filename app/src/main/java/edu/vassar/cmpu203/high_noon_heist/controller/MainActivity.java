package edu.vassar.cmpu203.high_noon_heist.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.vassar.cmpu203.high_noon_heist.model.Bandit;
import edu.vassar.cmpu203.high_noon_heist.model.Cowboy;
import edu.vassar.cmpu203.high_noon_heist.model.Location;
import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;
import edu.vassar.cmpu203.high_noon_heist.view.ActionSelectFragment;
import edu.vassar.cmpu203.high_noon_heist.view.AddPlayersFragment;
import edu.vassar.cmpu203.high_noon_heist.view.ConfigGameFragment;
import edu.vassar.cmpu203.high_noon_heist.view.HighNoonHeistFragFactory;
import edu.vassar.cmpu203.high_noon_heist.view.IActionSelect;
import edu.vassar.cmpu203.high_noon_heist.view.IAddPlayers;
import edu.vassar.cmpu203.high_noon_heist.view.IConfigGame;
import edu.vassar.cmpu203.high_noon_heist.view.IMainView;
import edu.vassar.cmpu203.high_noon_heist.view.IPlayerListAction;
import edu.vassar.cmpu203.high_noon_heist.view.IResults;
import edu.vassar.cmpu203.high_noon_heist.view.IViewObservation;
import edu.vassar.cmpu203.high_noon_heist.view.IVote;
import edu.vassar.cmpu203.high_noon_heist.view.MainView;
import edu.vassar.cmpu203.high_noon_heist.view.PlayerListActionFragment;
import edu.vassar.cmpu203.high_noon_heist.view.ResultScreenFragment;
import edu.vassar.cmpu203.high_noon_heist.view.ViewObservationFragment;
import edu.vassar.cmpu203.high_noon_heist.view.VoteFragment;

public class MainActivity extends AppCompatActivity implements IConfigGame.Listener, IAddPlayers.Listener, IPlayerListAction.Listener, IActionSelect.Listener,  IResults.Listener, IViewObservation.Listener, IVote.Listener {

    int curDay = 0;
    int dayLim;
    int curMoney = 0;
    int moneyLim;
    int playerCount;
    int banditCount;
    private PlayerList playersList;
    private ArrayList banditVals;
    private Player current;
    private PlayerList canAct;

    private Location loc = new Location();

    private IMainView mainView;
    //0 for config, 1 for action, 2 for observation, 3 for daytime
    private int gamePhase = 0;
    private static final String CURDAY = "curDay";
    private static final String DAYLIM = "dayLim";
    private static final String CURMONEY = "curMoney";
    private static final String MONEYLIM = "moneyLim";
    private static final String PLAYERCOUNT = "playerCount";
    private static final String BANDITCOUNT = "banditCount";
    private static final String PLAYERSLIST = "playersList";
    private static final String BANDITVALS = "banditVals";
    private static final String CURRENT = "current";
    private static final String CANACT = "canAct";
    private static final String LOCATION = "loc";
    private static final String GAMEPHASE = "gamePhase";

    public MainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportFragmentManager().setFragmentFactory(new HighNoonHeistFragFactory(this));
        super.onCreate(savedInstanceState);

        this.mainView = new MainView(this);
        this.setContentView(mainView.getRootView());

        if (savedInstanceState == null) {
            this.playersList = new PlayerList();
            this.mainView.displayFragment(new ConfigGameFragment(this), true, "configGame");
        }
        else{
            this.curDay = savedInstanceState.getInt(CURDAY);
            this.dayLim = savedInstanceState.getInt(DAYLIM);
            this.curMoney = savedInstanceState.getInt(CURMONEY);
            this.moneyLim = savedInstanceState.getInt(MONEYLIM);
            this.playerCount = savedInstanceState.getInt(PLAYERCOUNT);
            this.banditCount = savedInstanceState.getInt(BANDITCOUNT);
            this.playersList = PlayerList.fromBundle(savedInstanceState.getBundle(PLAYERSLIST));
            this.banditVals = savedInstanceState.getIntegerArrayList(BANDITVALS);
            this.gamePhase = savedInstanceState.getInt(GAMEPHASE);
            Bundle pBundle = savedInstanceState.getBundle(CURRENT);
            Player cur = new Player("");
            if (pBundle != null) {
                if (Player.checkBundleRole(pBundle))
                    cur = Bandit.fromBundle(pBundle);
                else
                    cur = Cowboy.fromBundle(pBundle);
            }
            this.current = this.playersList.findPlayer(cur.getName());
            if (savedInstanceState.getBundle(CANACT) != null)
                this.sharePlayers(savedInstanceState);
        }

    }

    public void sharePlayers(Bundle b){
        Location l = Location.fromBundle(b.getBundle(LOCATION));
        PlayerList actors = PlayerList.fromBundle(b.getBundle(CANACT));

        Location shareLoc = new Location();
        PlayerList shareActors = new PlayerList();

        Player cur;
        for (int i = 0; i < this.playersList.players.size(); i = i + 1){
            cur = (Player) this.playersList.players.get(i);
            if (actors.findPlayer(cur.getName()) != null) {
                shareActors.players.add(cur);
                if (cur.role() == 1)
                    shareActors.bandits.add(cur);
                else
                    shareActors.cowboys.add(cur);
            }
        }
        for (int i = 0; i < this.playersList.players.size(); i = i + 1){
            cur = (Player) this.playersList.players.get(i);
            if (l.inLocation(cur.getName()).equals("bank"))
                shareLoc.addTo(cur, "bank");
            else if (l.inLocation(cur.getName()).equals("saloon"))
                shareLoc.addTo(cur, "saloon");
            else if (l.inLocation(cur.getName()).equals("ranch"))
                shareLoc.addTo(cur, "ranch");
        }

        this.loc = shareLoc;
        this.canAct = shareActors;
    }

    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(DAYLIM, this.dayLim);
        outState.putInt(CURDAY, this.curDay);
        outState.putInt(CURMONEY, this.curMoney);
        outState.putInt(MONEYLIM, this.moneyLim);
        outState.putInt(PLAYERCOUNT, this.playerCount);
        outState.putInt(BANDITCOUNT, this.banditCount);
        outState.putInt(GAMEPHASE, this.gamePhase);
        if (this.playersList != null)
            outState.putBundle(PLAYERSLIST, this.playersList.toBundle());
        if (this.current != null)
            outState.putBundle(CURRENT, this.current.toBundle());
        outState.putIntegerArrayList(BANDITVALS, this.banditVals);
        if (this.canAct != null)
            outState.putBundle(CANACT, this.canAct.toBundle());
        outState.putBundle(LOCATION, this.loc.toBundle());
    }

    public PlayerList getPlayerListCopy(){
        return this.playersList.copyPlayers();
    }

    public int getMoney(){
        return this.curMoney;
    }

    /**
     * Checks if current amount of players meets total players
     * @return Boolean on if player cap is reached
     */
    public boolean checkPlayerCap(){
        return this.playerCount == this.playersList.players.size();
    }

    /**
     * Changes to player select once all players are set
     */
    @Override
    public void onPlayersSet(){
        this.canAct = this.getPlayerListCopy();
        this.mainView.displayFragment(new PlayerListActionFragment(this), true, "listAction");
    }

    public Player findPlayer(String name){
        return this.playersList.findPlayer(name);
    }

    /**
     * Randomly determines which players will be bandits
     */
    public void draw(){
        ArrayList ret = new ArrayList();
        Random rand = new Random();
        int d = 0;
        boolean repeat;
        for (int i = banditCount; i > 0; i = i - 1){
            repeat = true;
            while (repeat) {
                d = rand.nextInt(playerCount);
                if (ret.indexOf(d) == -1) {
                    ret.add(d);
                    repeat = false;
                }
            }
        }
        this.banditVals = ret;
    }

    /**
     * Sets game options
     * @param total amount of players
     * @param bandits, number of bandits
     * @param dayLim, total number of days
     * @param moneyLim, money to exceed
     * @param config, game fragment that displays
     */
    @Override
    public void onSetOptions(int total, int bandits, int dayLim, int moneyLim, IConfigGame config){
        this.playerCount = total;
        this.banditCount = bandits;
        this.dayLim = dayLim;
        this.moneyLim = moneyLim;
        this.draw();

        config.showConfig();
    }

    /**
     * Moves to add players once options are confirmed
     */
    public void onOptionsSet(){
        AddPlayersFragment addPlayersFrag = new AddPlayersFragment(this);
        this.gamePhase = 1;
        this.mainView.displayFragment(addPlayersFrag, false, "addPlayers");
    }

    /**
     * Handles adding a Player
     * @param name of the Player being added
     * @param addPlayers displays data
     */
    @Override
    public void onAddedPlayer(@NonNull String name, IAddPlayers addPlayers) {
        //temp for testing adding player screen alone
        if (this.playersList.players.size() == 0)
            this.draw();
        if (banditVals.indexOf(this.playersList.players.size()) == -1)
            playersList.addCowboy(name);
        else
            playersList.addBandit(name);
        addPlayers.showRole();
    }

    public String showRole(){
        Player p = (Player) this.playersList.players.get(playersList.players.size() - 1);
        return p.displayRole();
    }

    public void setCurrentPlayer(Player current){
        this.current = current;
    }


    public String toString(){
        return this.playerCount + " players, " + this.banditCount + " bandits, " + this.dayLim + " days, " + this.moneyLim + "$ to win";
    }

    public PlayerList getPlayers(){
        return this.playersList;
    }

    /**
     * Takes players to action or observation screen when a Player is selected
     * @param name of Player being selected
     */
    @Override
    public void playerSelected(String name){
        Player cur = this.findPlayer(name);
        this.setCurrentPlayer(cur);
        if (this.gamePhase == 1) {
            ActionSelectFragment action = new ActionSelectFragment(this);
            this.mainView.displayFragment(action, true, "act");
        }
        else if (this.gamePhase == 2){
            ViewObservationFragment observation = new ViewObservationFragment(this);
            this.mainView.displayFragment(observation, true, "observe");
        }
    }

    @Override
    public int checkPhase(){
        return this.gamePhase;
    }

    /**
     * Changes screen once a viewing is confirmed
     */
    @Override
    public void onActionDone(){
        this.canAct.removeByName(current.getName());
        if (this.canAct.players.size() > 0)
            this.mainView.displayFragment(new PlayerListActionFragment(this), true, "listAction");
        else {
            if (this.gamePhase == 1) {
                this.gamePhase = 2;
                canAct = this.getPlayerListCopy();
                this.mainView.displayFragment(new PlayerListActionFragment(this), true, "listAction");
                return;
            }
            else {
                this.curDay = this.curDay + 1;
                if (this.checkWin() == 1) {
                    this.loc.clearLocs();
                    this.mainView.displayFragment(new VoteFragment(this), true, "vote");
                    return;
                }
                else
                    this.mainView.displayFragment(new ResultScreenFragment(this), true, "results");
            }
        }
    }

    /*public void setRobbedInPL(){
        String name = this.current.getName();
        Bandit update = (Bandit) this.playersList.findPlayer(name);
        update.robbed = true;
    }

    public void setLocInPL(){
        String name = this.current.getName();
        Player update = this.playersList.findPlayer(name);
        update.updateLoc(current.viewLoc());
    }*/

    public void observeAt(String place, Player player){
        player.observe(this.loc, place);
    }

    public String showObservation(int number){
        return this.current.observation(this.loc, number);
    }

    public int stealFrom(String place){
        int val = current.rob(this.loc, place);
        this.curMoney = this.curMoney + val;
        return val;
    }

    /**
     * Checks whether the game has been won
     * @return 3 if cowboys win, 2 if bandits win, 1 if game is still in play
     */
    public int checkWin(){
        if (curDay == dayLim)
            return 3;
        else if ((playersList.bandits).size() >= ((playersList.cowboys).size()))
            return 2;
        else if (curMoney >= moneyLim)
            return 2;
        else if ((playersList.bandits).size() == 0)
            return 3;
        else
            return 1;
    }

    public void addVote(Player p){
        p.addVote();
    }

    public void subVote(Player p){
        p.subVote();
    }

    public Player onSubmitVotes(){
        ArrayList vals = this.playersList.voteVals();
        boolean repeats = this.playersList.checkTie();
        Player ret;
        if (repeats == false){
            if (this.playersList.canRemove()) {
                ret = this.playersList.mostVotes();
                this.playersList.removeByName(ret.getName());
                return ret;
            }
        }
        return null;
    }

    public PlayerList getCanAct(){
        return this.canAct;
    }

    public void onVotingDone(){
        this.gamePhase = 1;
        canAct = this.playersList.copyPlayers();
        if (this.checkWin() == 1) {
            //this.mainView.displayFragment(new PlayerListActionFragment(this, this.canAct), true, "select");
            this.mainView.displayFragment(new PlayerListActionFragment(this), true, "select");
            return;
        }
        else
            this.mainView.displayFragment(new ResultScreenFragment(this), true, "results");
    }

    public int getCurDay(){
        return this.curDay;
    }

    public Player getCurrent(){
        return this.current;
    }

    public String doViewLoc(){
        return this.current.viewLoc();
    }

    public void onGameDone(){
        this.curDay = 0;
        this.curMoney = 0;
        this.playersList = new PlayerList();
        this.loc.clearLocs();
        this.gamePhase = 0;

        this.mainView.displayFragment(new ConfigGameFragment(this), true, "start");
    }

}