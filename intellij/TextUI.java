package edu.vassar.cmpu203.high_noon_heist.view;

import java.util.Scanner;
import java.util.ArrayList;

import edu.vassar.cmpu203.high_noon_heist.controller.Controller;
import edu.vassar.cmpu203.high_noon_heist.model.Bandit;
import edu.vassar.cmpu203.high_noon_heist.model.Cowboy;
import edu.vassar.cmpu203.high_noon_heist.model.Location;
import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

/**
 * Write a description of class TextUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextUI
{/*
    public TextUI(){}

    /**
     * Error handler for taking in scanner inputs
     *
     * @param Scanner s for reading inputs
     * @param int x which corresponds to the scanner
     * @return A valid value of x

    public int configIntErrorHandler(Scanner s, int x){
        boolean go = true;
        while (go){
            try{
                x = s.nextInt();
                break;
            }
            catch (Exception e){
                System.out.println("Not a valid input, try again");
                s.next();
            }
        }
        return x;
    }


    /**
     * Text handler for setting up the game
     *
     * @param Controller c, which contains game data and runs the game

    public void configurePlayers(Controller control){
        Scanner read = new Scanner(System.in);
        int amt = 0;
        boolean go = true;
        System.out.println("How many players?");
        amt = this.configIntErrorHandler(read, amt);
        control.playerCount = amt;
        System.out.println();
        int days = 0;
        System.out.println("How many ingame days?");
        days = this.configIntErrorHandler(read, days);
        control.dayLim = days;
        System.out.println();
        int money = 0;
        System.out.println("How much money to win?");
        money = this.configIntErrorHandler(read, money);
        control.moneyLim = money;
        boolean valid = true;
        int bands = 0;
        System.out.println();
        System.out.println("How many bandits?");
        while (valid) {
            bands = this.configIntErrorHandler(read, bands);
            if (control.playerCount % 2 == 0){
                if (bands < (amt / 2))
                    break;
            }
            else{
                if (bands <= (amt / 2))
                    break;
            }
            System.out.println("Too many bandits, must be less than half the total players");
        }
        ArrayList a = control.draw(bands, amt);
        System.out.println();
        String name = "";
        int z;
        for (int i = 0; i < amt; i = i + 1){
            System.out.println("Add player's name");
            Scanner play = new Scanner(System.in);
            name = play.nextLine();
            z = control.addPlayer(name, a, i);
            if (z == 0)
                System.out.println("Your role is Cowboy");
            else
                System.out.println("Your role is Bandit");
            }
        System.out.println(control.playerCount + " total players, " + control.dayLim + " days, " + control.moneyLim + " dollars to win");
    }

    /**
     * Textual representation for players taking actions
     *
     * @param Controller c which contains game data
     * @return ArrayList<Bandit> of bandits who have robbed

    public ArrayList<Bandit> actions(Controller control){
        PlayerList listCopy = control.copy();
        Scanner read = new Scanner(System.in);
        String name;
        String place = "";
        Player inPlay;
        ArrayList<Bandit> ret = new ArrayList<Bandit>();
        while (listCopy.players.size() > 0) {
            System.out.println(listCopy.toString());
            System.out.println("Select your name");
            name = read.nextLine();
            boolean go = true;
            inPlay = listCopy.findPlayer(name);
            int dollars = 0;
            if (inPlay != null) {
                if (inPlay.role() == 0) {
                    while (go) {
                        System.out.println("Choose a place to watch: Bank, Saloon, Ranch");
                        place = read.nextLine().toLowerCase();
                        if (place.equals("bank") || place.equals("saloon") || place.equals("ranch"))
                            break;
                    }
                    inPlay.observe(control.loc, place);
                    inPlay.loc = place;
                    System.out.println("You chose to watch the " + place + " for the night");
                } else {
                    Bandit p1 = new Bandit("");
                    if (inPlay instanceof Bandit)
                        p1 = (Bandit) inPlay;
                    while (go) {
                        System.out.println("Choose a place: Bank, Saloon, Ranch");
                        place = read.nextLine().toLowerCase();
                        if (place.equals("bank") || place.equals("saloon") || place.equals("ranch"))
                            break;
                    }
                    while (go) {
                        System.out.println("Choose to watch or rob");
                        name = read.nextLine();
                        if ((name.toLowerCase()).equals("rob")) {
                            dollars = p1.rob(control.loc, place);
                            control.money = control.money + dollars;
                            ret.add(p1);
                            p1.loc = place;
                            System.out.println("You stole " + dollars + "$ from " + place);
                            break;
                        } else if (name.toLowerCase().equals("watch")){
                            inPlay.observe(control.loc, place);
                            inPlay.loc = place;
                            System.out.println("You chose to hang out at " + place);
                            break;
                        }
                    }
                }
            }
            listCopy.players.remove(inPlay);
        }
        return ret;
    }

    public void observations(Controller control, ArrayList<Bandit> robList) {
        PlayerList listCopy = control.pl.copy();
        Scanner read = new Scanner(System.in);
        String name;
        String choice;
        Player inPlay;
        String obs;
        while (listCopy.players.size() > 0) {
            System.out.println(listCopy.toString());
            System.out.println("Select your name");
            name = read.nextLine();
            inPlay = listCopy.findPlayer(name);
            Cowboy cow;
            String bandRet;
            Bandit band;
            boolean go = true;
            if (inPlay != null) {
                if (inPlay instanceof Cowboy) {
                    cow = (Cowboy) inPlay;
                    while (go){
                        System.out.println("Players or a name?");
                        choice = read.nextLine();
                        if (choice.toLowerCase().equals("players")){
                            System.out.println(cow.observation(control.loc, 0) + " at " + cow.loc);
                            break;
                        }
                        else if (choice.toLowerCase().equals("name")){
                            if (cow.observation(control.loc, 1) == null) {
                                System.out.println("No other players at " + cow.loc);
                                break;
                            }
                            else {
                                System.out.println(cow.observation(control.loc, 1) + " is at " + cow.loc);
                                break;
                            }
                        }
                    }
                    listCopy.players.remove(cow);
                }
                else if (inPlay instanceof Bandit){
                    band = (Bandit) inPlay;
                    bandRet = band.observation(control.loc);
                    if (robList.indexOf(band) == -1) {
                        if (bandRet == null)
                            System.out.println("No other players at the " + band.loc);
                        else
                            System.out.println(bandRet + " at " + band.loc);
                    }
                    else
                        System.out.println("You wait the night at " + band.loc);
                    listCopy.players.remove(band);
                }
            }
        }
    }

    
    public static void main(String args[]){
        TextUI tui = new TextUI();
        Controller control = new Controller(0, 0, 0);
        tui.configurePlayers(control);
        Location l = new Location();
        ArrayList<Bandit> robbers = tui.actions(control);
        tui.observations(control, robbers);
        System.out.println("Total money: $" + control.money);
        if (control.checkWin() == 2)
            System.out.println("Bandits win!");
    }
    */
}
