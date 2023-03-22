import java.util.ArrayList;
import java.util.Random;

public class Controller{
    int curDay = 0;
    int dayLim;
    int playerCount;
    int money = 0;
    int moneyLim;
    boolean inGame;
    PlayerList pl = new PlayerList();
    Location loc = new Location();
    
    public Controller(int dayLim, int playerCount, int moneyLim){
        this.dayLim = dayLim;
        this.playerCount = playerCount;
        this.moneyLim = moneyLim;
    }

    /**
     * Random function for deciding which players get what roles
     *
     * @param bandits, number of bandits
     * @param amt, total number of players
     * @return ArrayList of ints for indices of players who will be bandits
     */
    public ArrayList draw(int bandits, int amt){
        ArrayList ret = new ArrayList();
        Random rand = new Random();
        int d = 0;
        boolean repeat;
        for (int i = bandits; i > 0; i = i - 1){
            repeat = true;
            while (repeat) {
                d = rand.nextInt(amt);
                if (ret.indexOf(d) == -1) {
                    ret.add(d);
                    repeat = false;
                }
            }
        }
        return ret;
    }

    public PlayerList copy(){
        return this.pl.copy();
    }

    public int addPlayer(String name, ArrayList bands, int cur){
        return this.pl.addPlayer(name, bands, cur);
    }

    /**
     * Checks whether the game has been won
     *
     * @return 1 if game is still in play, 2 if the bandits have won, 3 if cowboys have won
     */
    int checkWin(){
        if (curDay == dayLim)
            return 3;
        else if ((pl.bandits).size() > ((pl.players).size()))
            return 2;
        else if (money >= moneyLim)
            return 2;
        else if ((pl.bandits).size() == 0)
            return 3;
        else
            return 1;
    }
}