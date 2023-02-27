import java.util.ArrayList;

public class Bandit extends Player{
    public Bandit(String name){
        super(name);
    }

    public ArrayList viewBandits(Players p){
        return p.bandits;
    }
    
    public void rob(GameRunner g, Locations l){
        g.curMoney = g.curMoney + l.value;
        (l.players).add(this);
    }
    
}
