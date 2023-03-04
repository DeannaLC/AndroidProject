import java.util.Random;
import java.util.ArrayList;

/**
 * Write a description of class Cowboy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Cowboy extends Player
{

    /**
     * Constructor for objects of class Cowboy
     */
    public Cowboy(String name)
    {
        super(name);
    }
    
    public String observation(Location l, int a){
        String b;
        if (a == 0){
            if ((this.loc).equals("bank"))
                return  "" + (l.bank).size();
            else if ((this.loc).equals("saloon"))
                return "" + (l.saloon).size();
            else
                return "" + (l.ranch).size();
        }
        else
            return (l.randPlayer(this.name, this.loc)).name;
    }
    
    public static void main(String args[]){
        Cowboy p = new Cowboy("simon");
        
        Location l = new Location();
        l.addPlayers();
        
        p.observe(l, "bank");
        
        System.out.println(p.observation(l, 0));
        System.out.println(p.observation(l, 1));
        System.out.println();
    }
}
