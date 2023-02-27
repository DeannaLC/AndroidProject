import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

public class Players{
    ArrayList players = new ArrayList();
    ArrayList bandits = new ArrayList();
    ArrayList bank = new ArrayList();
    int size = 0;
    
    public void addPlayer(String name, ArrayList bands){
        size = size + 1;
        if (bands.indexOf(this.size) == -1){
            players.add(new Cowboy(name));
            System.out.println("Your role is cowboy");
        }
        else{
            Bandit band = new Bandit(name);
            players.add(band);
            bandits.add(band);
            System.out.println("Your role is bandit");
        }
    }
    
    public void displayPlayers(){
        for (int i = 0; i < (this.players).size(); i = i + 1){
            System.out.println(players.get(i));
        }
        
        
    }

    public ArrayList draw(GameRunner g, int bandits){
        ArrayList ret = new ArrayList();
        Random rand = new Random();
        int d = 0;
        boolean repeat;
        for (int i = bandits; i > 0; i = i - 1){
            repeat = true;
            while (repeat) {
                d = rand.nextInt(g.players);
                if (ret.indexOf(d) == -1) {
                    ret.add(d);
                    repeat = false;
                }
            }
        }
        return ret;
    }
}
