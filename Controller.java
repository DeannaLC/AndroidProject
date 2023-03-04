public class Controller{
    int curDay = 0;
    int dayLim;
    int playerCount;
    int money = 0;
    int moneyLim;
    boolean inGame;
    
    public Controller(int dayLim, int playerCount, int moneyLim){
        this.dayLim = dayLim;
        this.playerCount = playerCount;
        this.moneyLim = moneyLim;
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
