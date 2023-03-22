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
    Location l = new Location();
    
    public Controller(int dayLim, int playerCount, int moneyLim){
        this.dayLim = dayLim;
        this.playerCount = playerCount;
        this.moneyLim = moneyLim;
    }

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

    public int addPlayer(String name, ArrayList bands, int cur){
        return this.pl.addPlayer(name, bands, cur);
    }
    
    int checkWin(PlayerList p){
        if (curDay == dayLim)
            return 3;
        else if ((p.bandits).size() > ((p.players).size()))
            return 2;
        else if (money >= moneyLim)
            return 2;
        else if ((p.bandits).size() == 0)
            return 3;
        else
            return 1;
    }
    
    void addMoney(int money){
        this.money = this.money + money;
    }
}