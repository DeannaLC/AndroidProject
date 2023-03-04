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
    
    public void configurePlayers(Controller c, PlayerList p){
        Scanner read = new Scanner(System.in);
        System.out.println("How many players?");
        int amt = read.nextInt();
        System.out.println();
        System.out.println("How many ingame days?");
        int days = read.nextInt();
        System.out.println();
        System.out.println("How much money to win?");
        int money = read.nextInt();
        System.out.println("\nHow many bandits?");
        int bands = read.nextInt();
        c = new Controller(days, amt, money);
        ArrayList a = p.draw(bands, amt);
        System.out.println();
        String name = "";
        for (int i = 0; i < amt; i = i + 1){
            System.out.println("Add player's name");
            Scanner play = new Scanner(System.in);
            name = play.nextLine();
            p.addPlayer(name, a, i);
        }
        System.out.println(c.dayLim + " days, " + c.moneyLim + " dollars to win");
    }
    
    public void actions(PlayerList p, Location l, Controller c){
        
    }
    
    public static void main(String args[]){
        TextUI t = new TextUI();
        t.configurePlayers(new Controller(0, 0, 0), new PlayerList());
    }
}
