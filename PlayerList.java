import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

public class PlayerList{
    ArrayList players = new ArrayList<Player>();
    ArrayList bandits = new ArrayList<Bandit>();
    ArrayList cowboys = new ArrayList<Cowboy>();
    
    public PlayerList(){}
    
    public void addPlayer(String name, ArrayList bands, int a){
        if (bands.indexOf(a) == -1){
            Cowboy cow = new Cowboy(name);
            players.add(cow);
            cowboys.add(cow);
            System.out.println("Your role is cowboy");
        }
        else{
            Bandit band = new Bandit(name);
            players.add(band);
            bandits.add(band);
            System.out.println("Your role is bandit");
        }
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
    
    public ArrayList playerNames(){
        ArrayList ret = new ArrayList<String>();
        Player p;
        for (int i = 0; i < players.size(); i = i + 1){
            p = (Player) (players.get(i));
            ret.add(p.getName());
        }
        return ret;
    }
    
    public String toString(ArrayList players){
        String playersRes = "Players: ";
        Player p;
        for (int i = 0; i < players.size(); i++){
            p = (Player) (players.get(i));
            playersRes += p.name;
            if (i != (players.size() - 1))
                playersRes += ", ";
        
        }
        return playersRes;
    }
    
    public static void main(String args[]){
        PlayerList p = new PlayerList();
        (p.players).add(new Player("jeff"));
        (p.players).add(new Player("jeoff"));
        (p.players).add(new Player("jerry"));
        System.out.println(p.toString(p.players));
    }
}
