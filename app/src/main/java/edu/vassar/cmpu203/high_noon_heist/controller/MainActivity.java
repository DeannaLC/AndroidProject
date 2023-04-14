package edu.vassar.cmpu203.high_noon_heist.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.vassar.cmpu203.high_noon_heist.model.Cowboy;
import edu.vassar.cmpu203.high_noon_heist.model.Location;
import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;
import edu.vassar.cmpu203.high_noon_heist.view.ActionSelectFragment;
import edu.vassar.cmpu203.high_noon_heist.view.AddPlayersFragment;
import edu.vassar.cmpu203.high_noon_heist.view.ConfigGameFragment;
import edu.vassar.cmpu203.high_noon_heist.view.IActionSelect;
import edu.vassar.cmpu203.high_noon_heist.view.IAddPlayers;
import edu.vassar.cmpu203.high_noon_heist.view.IConfigGame;
import edu.vassar.cmpu203.high_noon_heist.view.IMainView;
import edu.vassar.cmpu203.high_noon_heist.view.IPlayerListAction;
import edu.vassar.cmpu203.high_noon_heist.view.IViewObservation;
import edu.vassar.cmpu203.high_noon_heist.view.MainView;
import edu.vassar.cmpu203.high_noon_heist.view.PlayerListActionFragment;
import edu.vassar.cmpu203.high_noon_heist.view.ResultScreenFragment;
import edu.vassar.cmpu203.high_noon_heist.view.ViewObservationFragment;

public class MainActivity extends AppCompatActivity implements IConfigGame.Listener, IAddPlayers.Listener, IPlayerListAction.Listener, IActionSelect.Listener, IViewObservation.Listener {

    int curDay = 0;
    int dayLim;
    int curMoney = 0;
    int moneyLim;
    int playerCount;
    int banditCount;
    private PlayerList playersList;
    private List banditVals;
    private Player current;
    private PlayerList canAct;

    private Location loc = new Location();

    private IMainView mainView;
    //1 for action, 2 for observation, 3 for daytime
    private int gamePhase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.playersList = new PlayerList();

        this.mainView = new MainView(this);
        this.setContentView(mainView.getRootView());

        this.mainView.displayFragment(new ConfigGameFragment(this), true, "configGame");
    }

    public PlayerList getPlayerListCopy(){
        return this.playersList.copyPlayers();
    }

    public int getMoney(){
        return this.curMoney;
    }

    public boolean checkPlayerCap(){
        return this.playerCount == this.playersList.players.size();
    }

    @Override
    public void onPlayersSet(){
        this.canAct = this.getPlayerListCopy();
        this.mainView.displayFragment(new PlayerListActionFragment(this, this.canAct), true, "listAction");
    }

    public Player findPlayer(String name){
        return this.playersList.findPlayer(name);
    }

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

    @Override
    public void onSetOptions(int total, int bandits, int dayLim, int moneyLim, IConfigGame config){
        this.playerCount = total;
        this.banditCount = bandits;
        this.dayLim = dayLim;
        this.moneyLim = moneyLim;
        this.draw();

        config.showConfig(this);
    }

    public void onOptionsSet(){
        AddPlayersFragment addPlayersFrag = new AddPlayersFragment(this);
        this.gamePhase = 1;
        this.mainView.displayFragment(addPlayersFrag, false, "addPlayers");
    }

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
    @Override
    public void playerSelected(String name){
        Player cur = this.findPlayer(name);
        this.setCurrentPlayer(cur);
        if (this.gamePhase == 1) {
            ActionSelectFragment action = new ActionSelectFragment(this.current, this);
            this.mainView.displayFragment(action, true, "act");
        }
        else if (this.gamePhase == 2){
            ViewObservationFragment observation = new ViewObservationFragment(this, this.current);
            this.mainView.displayFragment(observation, true, "observe");
        }
    }

    @Override
    public int checkPhase(){
        return this.gamePhase;
    }

    @Override
    public void onActionDone(){
        this.canAct.removePlayer(current);
        if (this.canAct.players.size() > 0)
            this.mainView.displayFragment(new PlayerListActionFragment(this, canAct), true, "listAction");
        else{
            if (this.gamePhase == 1) {
                this.gamePhase = 2;
                canAct = this.getPlayerListCopy();
                this.mainView.displayFragment(new PlayerListActionFragment(this, canAct), true, "listAction");
            }
            else
                this.mainView.displayFragment(new ResultScreenFragment(this), true, "results");

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

    public int checkWin(){
        if (curDay == dayLim)
            return 3;
        else if ((playersList.bandits).size() > ((playersList.cowboys).size()))
            return 2;
        else if (curMoney >= moneyLim)
            return 2;
        else if ((playersList.bandits).size() == 0)
            return 3;
        else
            return 1;
    }
}