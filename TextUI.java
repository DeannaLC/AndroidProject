import java.util.Scanner;
import java.util.ArrayList;

/**
 * Write a description of class TextUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextUI
{
    public TextUI(){}
    
    public void configurePlayers(Controller c){
        Scanner read = new Scanner(System.in);
        System.out.println("How many players?");
        int amt = read.nextInt();
        c.playerCount = amt;
        System.out.println();
        System.out.println("How many ingame days?");
        int days = read.nextInt();
        c.dayLim = days;
        System.out.println();
        System.out.println("How much money to win?");
        int money = read.nextInt();
        c.moneyLim = money;
        boolean valid = true;
        int bands = 0;
        while (valid) {
            System.out.println("\nHow many bandits?");
            bands = read.nextInt();
            if (c.playerCount % 2 == 0){
                if (bands < (c.playerCount / 2))
                    break;
            }
            else{
                if (bands <= (c.playerCount / 2))
                    break;
            }
            System.out.println("Too many bandits, must be less than half the total players");
        }
        ArrayList a = c.draw(bands, amt);
        System.out.println();
        String name = "";
        int z;
        for (int i = 0; i < amt; i = i + 1){
            System.out.println("Add player's name");
            Scanner play = new Scanner(System.in);
            name = play.nextLine();
            z = c.addPlayer(name, a, i);
            if (z == 0)
                System.out.println("Your role is Cowboy");
            else
                System.out.println("Your role is Bandit");
            }
        System.out.println(c.dayLim + " days, " + c.moneyLim + " dollars to win");
    }
    
    public ArrayList<Bandit> actions(Controller c){
        PlayerList listCopy = c.pl.copy();
        Scanner read = new Scanner(System.in);
        String name;
        String place;
        Player inPlay;
        ArrayList<Bandit> ret = new ArrayList<Bandit>();
        while (listCopy.players.size() > 0) {
            System.out.println(listCopy.toString());
            System.out.println("Select your name");
            name = read.nextLine();
            inPlay = listCopy.findPlayer(name);
            int dollars = 0;
            if (inPlay != null) {
                if (inPlay.role() == 0) {
                    System.out.println("Choose a place to watch: Bank, Saloon, Ranch");
                    place = read.nextLine();
                    place = place.toLowerCase();
                    inPlay.observe(c.l, place);
                    inPlay.loc = place;
                    System.out.println("You chose to watch the " + place + " for the night");
                } else {
                    Bandit p1 = new Bandit("");
                    if (inPlay instanceof Bandit)
                        p1 = (Bandit) inPlay;
                    System.out.println("Choose a place: Bank, Saloon, Ranch");
                    place = read.nextLine();
                    place = place.toLowerCase();
                    System.out.println("Choose to watch or rob");
                    name = read.nextLine();
                    if ((name.toLowerCase()).equals("rob")) {
                        dollars = p1.rob(c.l, place);
                        c.money = c.money + dollars;
                        ret.add(p1);
                        p1.loc = place;
                        System.out.println("You stole " + dollars + "$ from " + place);
                    }
                    else {
                        inPlay.observe(c.l, place);
                        inPlay.loc = place;
                        System.out.println("You chose to hang out at " + place);
                    }
                }
            }
            listCopy.players.remove(inPlay);
        }
        return ret;
    }

    public void observations(Controller c, ArrayList<Bandit> robList) {
        PlayerList listCopy = c.pl.copy();
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
            Bandit band;
            if (inPlay != null) {
                if (inPlay instanceof Cowboy) {
                    cow = (Cowboy) inPlay;
                    System.out.println("Players or a name?");
                    choice = read.nextLine();
                    if (choice.equals("players")){
                        System.out.println(cow.observation(c.l, 0));
                    }
                    else
                        System.out.println(cow.observation(c.l, 1));
                    listCopy.players.remove(cow);
                }
                else if (inPlay instanceof Bandit){
                    band = (Bandit) inPlay;
                    if (robList.indexOf(band) != -1)
                        System.out.println(band.observation(c.l));
                    else
                        System.out.println("You wait the night at " + band.loc);
                    listCopy.players.remove(band);
                }
            }
        }
    }

    
    public static void main(String args[]){
        TextUI t = new TextUI();
        Controller c = new Controller(0, 0, 0);
        t.configurePlayers(c);
        Location l = new Location();
        ArrayList<Bandit> robbers = t.actions(c);
        t.observations(c, robbers);
        System.out.println("Total money: " + c.money);
    }
}
