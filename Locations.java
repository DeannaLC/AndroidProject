import java.util.ArrayList;
import java.util.Random;

public class Locations{
    ArrayList<Player> players = new ArrayList();
    int value;
    
    /*
    public void addLoc(Player p){
        if (name.equals("bank")){
            bank.add(p);
        }
    }
    */
    
    public void setValue(String name){
        Random rand = new Random();
        int val = 0;
        if (name.equals("bank")){
            val = 2000 - rand.nextInt(501);
        }
        else if (name.equals("saloon")){
            val = 1500 - rand.nextInt(501);
        }
        else
            val = 1000 - rand.nextInt(501);
        this.value = val;
    }
}
