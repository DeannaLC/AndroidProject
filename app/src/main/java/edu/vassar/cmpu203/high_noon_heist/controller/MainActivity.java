package edu.vassar.cmpu203.high_noon_heist.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.vassar.cmpu203.high_noon_heist.model.Bandit;
import edu.vassar.cmpu203.high_noon_heist.model.Cowboy;
import edu.vassar.cmpu203.high_noon_heist.model.Leaderboard;
import edu.vassar.cmpu203.high_noon_heist.model.Location;
import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;
import edu.vassar.cmpu203.high_noon_heist.model.Winner;
import edu.vassar.cmpu203.high_noon_heist.persistence.IPersistenceFacade;
import edu.vassar.cmpu203.high_noon_heist.persistence.LocalStorageFacade;
import edu.vassar.cmpu203.high_noon_heist.view.ActionSelectFragment;
import edu.vassar.cmpu203.high_noon_heist.view.AddPlayersFragment;
import edu.vassar.cmpu203.high_noon_heist.view.ConfigGameFragment;
import edu.vassar.cmpu203.high_noon_heist.view.HighNoonHeistFragFactory;
import edu.vassar.cmpu203.high_noon_heist.view.IActionSelect;
import edu.vassar.cmpu203.high_noon_heist.view.IAddPlayers;
import edu.vassar.cmpu203.high_noon_heist.view.IConfigGame;
import edu.vassar.cmpu203.high_noon_heist.view.ILeaderboard;
import edu.vassar.cmpu203.high_noon_heist.view.IMainView;
import edu.vassar.cmpu203.high_noon_heist.view.IPlayerListAction;
import edu.vassar.cmpu203.high_noon_heist.view.IResults;
import edu.vassar.cmpu203.high_noon_heist.view.IStart;
import edu.vassar.cmpu203.high_noon_heist.view.IViewObservation;
import edu.vassar.cmpu203.high_noon_heist.view.IVote;
import edu.vassar.cmpu203.high_noon_heist.view.LeaderboardFragment;
import edu.vassar.cmpu203.high_noon_heist.view.MainView;
import edu.vassar.cmpu203.high_noon_heist.view.PlayerListActionFragment;
import edu.vassar.cmpu203.high_noon_heist.view.ResultScreenFragment;
import edu.vassar.cmpu203.high_noon_heist.view.StartFragment;
import edu.vassar.cmpu203.high_noon_heist.view.ViewObservationFragment;
import edu.vassar.cmpu203.high_noon_heist.view.VoteFragment;

/**
 * Controller class, runs the entire game
 */
public class MainActivity extends AppCompatActivity implements IStart.Listener, ILeaderboard.Listener, IConfigGame.Listener, IAddPlayers.Listener, IPlayerListAction.Listener, IActionSelect.Listener,  IResults.Listener, IViewObservation.Listener, IVote.Listener {

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
    public boolean testMode = false;
    Leaderboard leaderboard;
    private IPersistenceFacade persistenceFacade;

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
    private static final String LEADERBOARD = "leaderboard";

    public MainActivity(){}

    /**
     * Displays starting screen, loads in fields from bundle and file if they exist
     * @param savedInstanceState, bundle retrieve data from
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportFragmentManager().setFragmentFactory(new HighNoonHeistFragFactory(this));
        super.onCreate(savedInstanceState);

        this.persistenceFacade = new LocalStorageFacade(this.getFilesDir());
        this.leaderboard = this.persistenceFacade.retrieveLeaderboard();
        if (this.leaderboard == null)
            this.leaderboard = new Leaderboard();
        this.mainView = new MainView(this);
        this.setContentView(mainView.getRootView());

        if (savedInstanceState == null) {
            this.playersList = new PlayerList();
            this.mainView.displayFragment(new StartFragment(this), true, "start");
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
            this.leaderboard = Leaderboard.fromBundle(savedInstanceState.getBundle(LEADERBOARD));
        }
    }

    /**
     * Displays config fragment
     */
    public void onBegin(){
        this.mainView.displayFragment(new ConfigGameFragment(this), true, "configGame");
    }

    /**
     * Displays leaderboard fragment
     */
    public void onLeaderboardCheck(){
        this.mainView.displayFragment(new LeaderboardFragment(this), true, "leaders");
    }

    /**
     * Makes loc, playersList, and canAct fields share the same player objects upon recreation from bundles
     * @param b, bundle to retrieve data from
     */
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

    /**
     * Saves data to a file and bundle when a fragment is recreated
     * @param outState, bundle to save data to
     */
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        this.persistenceFacade.saveLeaderboard(this.leaderboard);
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
        outState.putBundle(LEADERBOARD, this.leaderboard.toBundle());
        outState.putBundle(LOCATION, this.loc.toBundle());
    }

    /**
     * Creates a copy of the PlayerList field
     * @return new PlayerList with the same Player objects in it
     */
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
     * Changes to player select once all players are added
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
     * Randomly determines which players will be bandits unless test mode is enabled
     */
    public void draw(){
        ArrayList ret = new ArrayList();
        if (testMode){
            ret.add(0);
            ret.add(3);
            this.banditVals = ret;
            return;
        }
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

    /**
     * Shows role of the most recent added Player
     * @return String showing role
     */
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
     * Changes screen depending on if a win condition is met once a viewing is confirmed
     * Switches from action to either result or observation, observation to result or voting
     */
    @Override
    public void onActionDone(){
        this.canAct.removePlayer(current);
        if (this.canAct.players.size() > 0)
            this.mainView.displayFragment(new PlayerListActionFragment(this), true, "listAction");
        else {
            if (this.gamePhase == 1) {
                if (this.checkWin() == 2) {
                    this.mainView.displayFragment(new ResultScreenFragment(this), true, "listAction");
                    return;
                }
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
                else {
                    this.mainView.displayFragment(new ResultScreenFragment(this), true, "results");
                }
            }
        }
    }

    public void observeAt(String place, Player player){
        player.observe(this.loc, place);
    }

    public String showObservation(int number){
        return this.current.observation(this.loc, number);
    }

    /**
     * Called when a bandit steals from somewhere, adds to curMoney count
     * @param place, where is being stolen from
     * @return amount that is stolen
     */
    public int stealFrom(String place){
        int val = current.rob(this.loc, place);
        this.curMoney = this.curMoney + val;
        return val;
    }

    /**
     * Checks whether the game has been won, adds winner to leaderboard field if someone wins
     * @return 3 if cowboys win, 2 if bandits win, 1 if game is still in play
     */
    public int checkWin(){
        Winner winner = new Winner();
        int ret;
        if (curDay == dayLim)
            ret = 3;
        else if ((playersList.bandits).size() >= ((playersList.cowboys).size()))
            ret = 2;
        else if (curMoney >= moneyLim)
            ret = 2;
        else if ((playersList.bandits).size() == 0)
            ret = 3;
        else
            ret = 1;
        if (ret == 2){
            winner.setBanditWin();
            this.leaderboard.addWinner(winner);
            return ret;
        }
        else if (ret == 3){
            winner.setCowboyWin();
            this.leaderboard.addWinner(winner);
            return ret;
        }
        return ret;
    }

    /**
     *
     * @return 3 for cowboy win, 2 for bandit win, 1 which will not be reached
     */
    public int getWin() {
        this.persistenceFacade.saveLeaderboard(this.leaderboard);
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

    /**
     * Determines whether a Player is removed from the game once votes are submitted
     * @return Player if one is removed, null otherwise
     */
    public Player onSubmitVotes(){
        boolean repeats = this.playersList.checkTie();
        Player ret;
        if (repeats == false){
            if (this.playersList.canRemove()) {
                ret = this.playersList.mostVotes();
                //this.playersList.removeByName(ret.getName());
                this.playersList.removePlayer(ret);
                return ret;
            }
        }
        this.playersList.resetAllVotes();
        return null;
    }

    public PlayerList getCanAct(){
        return this.canAct;
    }

    /**
     * Switches fragment once voting is done
     * Goes back to action fragment if game is still in play, switches to results if someone wins
     */
    public void onVotingDone(){
        this.gamePhase = 1;
        canAct = this.playersList.copyPlayers();
        if (this.checkWin() == 1) {
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

    /**
     * Resets game settings once the game finishes
     */
    public void onGameDone(){
        this.curDay = 0;
        this.curMoney = 0;
        this.playersList = new PlayerList();
        this.loc.clearLocs();
        this.gamePhase = 0;

        this.mainView.displayFragment(new StartFragment(this), true, "start");
    }

    public boolean getTestMode(){
        return this.testMode;
    }

    public void onViewed(){
        this.mainView.displayFragment(new StartFragment(this), true, "start");
    }

    /**
     * Clears the leaderboard field and in the file
     * @param leaderDisplay, fragment that will show cleared leaderboard
     */
    public void onLeaderboardCleared(ILeaderboard leaderDisplay){
        this.leaderboard = new Leaderboard();
        this.persistenceFacade.saveLeaderboard(this.leaderboard);
        leaderDisplay.showDisplay();
    }
    public Leaderboard getLeaderboard(){
        return this.leaderboard;
    }
}