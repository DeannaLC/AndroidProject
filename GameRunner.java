import java.util.ArrayList;
import java.util.Scanner;

public class GameRunner{
    int players;
    int dayLim;
    int curDay = 1;
    int moneyLim;
    int curMoney = 0;
    boolean inPlay = true;

    public GameRunner(int players, int dayLim, int moneyLim){
        this.players = players;
        this.dayLim = dayLim;
        this.moneyLim = moneyLim;
    }
    
    public static void main(String args[]){
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
    	GameRunner c = new GameRunner(amt, days, money);
    	System.out.println();
    	Players a = new Players();
    	ArrayList roles = a.draw(c, bands);
    	String name = "";
    	for (; amt > 0; amt = amt - 1){
        	System.out.println("Add player's name");
        	Scanner play = new Scanner(System.in);
        	name = play.nextLine();
        	a.addPlayer(name, roles);
    	}
    	System.out.println();
    }
}

