import java.util.Random;

public class Player {
    
    String name;
    boolean alive = true;

    public Player(String name){
        this.name = name;
    }
    
    public void watch(Locations loc){
        (loc.players).add(this);
    }
    
    public int watchCount(Locations loc){
        return (loc.players).size();
    }
    
    public Player watchPerson(Locations loc){
        Random rand = new Random();
        int rnd = rand.nextInt((loc.players).size());
        return (loc.players).get(rnd);
    }

}
