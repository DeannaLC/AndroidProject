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
    
    public void actions(Controller c){
        PlayerList listCopy = c.pl.copy();
        Scanner read = new Scanner(System.in);
        String name;
        String place;
        Player inPlay;
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
                        System.out.println("You stole " + dollars + "$ from " + place);
                    }
                    else {
                        inPlay.observe(c.l, place);
                        System.out.println("You chose to hang out at " + place);
                    }
                }
            }
            listCopy.players.remove(inPlay);
        }
    }

    
    public static void main(String args[]){
        TextUI t = new TextUI();
        Controller c = new Controller(0, 0, 0);
        t.configurePlayers(c);
        Location l = new Location();
        t.actions(c);
        System.out.println();
    }
}
