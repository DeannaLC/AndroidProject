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
import edu.vassar.cmpu203.high_noon_heist.view.IViewObservation;
import edu.vassar.cmpu203.high_noon_heist.view.IVote;
import edu.vassar.cmpu203.high_noon_heist.view.MainView;
import edu.vassar.cmpu203.high_noon_heist.view.PlayerListActionFragment;
import edu.vassar.cmpu203.high_noon_heist.view.ResultScreenFragment;
import edu.vassar.cmpu203.high_noon_heist.view.ViewObservationFragment;
import edu.vassar.cmpu203.high_noon_heist.view.VoteFragment;

public class MainActivity extends AppCompatActivity implements IConfigGame.Listener, IAddPlayers.Listener, IPlayerListAction.Listener, IActionSelect.Listener, IViewObservation.Listener, IVote.Listener {

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
    //1 for action, 2 for observation, 3 for daytime
    private int gamePhase;
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
            this.moneyLim = savedInstanceState.getInt(CURMONEY);
            this.playerCount = savedInstanceState.getInt(PLAYERCOUNT);
            this.banditCount = savedInstanceState.getInt(BANDITCOUNT);
            /*this.playersList = (PlayerList) savedInstanceState.getSerializable(PLAYERSLIST);
            this.banditVals = savedInstanceState.getIntegerArrayList(BANDITVALS);
            this.current = (Player) savedInstanceState.getSerializable(CURRENT);
            this.canAct = (PlayerList) savedInstanceState.getSerializable(CANACT);
            this.loc = (Location) savedInstanceState.getSerializable(LOCATION);

            this.playersList = PlayerList.fromBundle(savedInstanceState);*/
            /*if (savedInstanceState.getString("role") == "bandit")
                this.current = Bandit.fromBundle(savedInstanceState);
            else
                this.current = Cowboy.fromBundle(savedInstanceState);*/

        }

    }

    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(DAYLIM, this.dayLim);
        outState.putInt(CURDAY, this.curDay);
        outState.putInt(CURMONEY, this.curMoney);
        outState.putInt(MONEYLIM, this.moneyLim);
        outState.putInt(PLAYERCOUNT, this.playerCount);
        outState.putInt(BANDITCOUNT, this.banditCount);
        /*outState.putSerializable(PLAYERSLIST, this.playersList);
        outState.putIntegerArrayList(BANDITVALS, this.banditVals);
        outState.putSerializable(CURRENT, this.current);
        outState.putSerializable(CANACT, this.canAct);
        outState.putSerializable(LOCATION, this.loc);*/
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
        //this.mainView.displayFragment(new VoteFragment(this, this.playersList), true, "votes");
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

        config.showConfig(this);
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
        addPlayers.showRole(this);
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
        this.canAct.removePlayer(current);
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
                this.playersList.removePlayer(ret);
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

}