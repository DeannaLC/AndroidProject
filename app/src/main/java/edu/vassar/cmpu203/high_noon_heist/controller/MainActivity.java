package edu.vassar.cmpu203.high_noon_heist.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;
import edu.vassar.cmpu203.high_noon_heist.view.AddPlayersFragment;
import edu.vassar.cmpu203.high_noon_heist.view.AddPlayersView;
import edu.vassar.cmpu203.high_noon_heist.view.ConfigGameFragment;
import edu.vassar.cmpu203.high_noon_heist.view.IAddPlayers;
import edu.vassar.cmpu203.high_noon_heist.view.IAddPlayersView;
import edu.vassar.cmpu203.high_noon_heist.view.IConfigGame;
import edu.vassar.cmpu203.high_noon_heist.view.IMainView;
import edu.vassar.cmpu203.high_noon_heist.view.IShowRole;
import edu.vassar.cmpu203.high_noon_heist.view.MainView;

public class MainActivity extends AppCompatActivity implements IConfigGame.Listener, IAddPlayers.Listener {

    int curDay = 0;
    int dayLim;
    int curMoney = 0;
    int moneyLim;
    int playerCount;
    int banditCount;
    private PlayerList playersList;
    private IAddPlayersView addPlayersView;
    private List banditVals;

    private IMainView mainView;

    public MainActivity(int dayLim, int moneyLim, int playerCount, int banditCount)
    {
        this.dayLim = dayLim;
        this.moneyLim = moneyLim;
        this.playerCount = playerCount;
        this.banditCount = banditCount;

    }

    public List retBanditVals()
    {
        return this.banditVals;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.playersList = new PlayerList();

        this.mainView = new MainView(this);
        this.setContentView(mainView.getRootView());

        this.mainView.displayFragment(new ConfigGameFragment(this), true, "configGame");
        //this.mainView.displayFragment(new AddPlayersFragment(this), true, "addPlayers");


        //this.addPlayersView = new AddPlayersView(this, this);
        //this.setContentView(addPlayersView.getRootView());

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
        addPlayers.showNames(this.playersList);
    }

    public String showRole(IShowRole role){
        Player p = (Player) this.playersList.players.get(playersList.players.size() - 1);
        return p.displayRole();
    }

    public void onSetCount(int total, int bandits){
        this.playerCount = total;
        this.draw();
    }

    public String toString(){
        return this.playerCount + " players, " + this.banditCount + " bandits, " + this.dayLim + " days, " + this.moneyLim + "$ to win";
    }
}