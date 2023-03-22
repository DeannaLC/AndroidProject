import java.util.Random;

/**
 * Write a description of class Bandit here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bandit extends Player
{

    public boolean robbed = false;

    /**
     * Constructor for objects of class Bandit
     */
    public Bandit(String name)
    {
        super(name);
    }

    public String observation(Location l){
        Player retPlayer;
        Random rnd = new Random();
        int a = rnd.nextInt(2);
        if (a == 0){
            if ((this.loc).equals("bank"))
                return  "" + (l.bank).size();
            else if ((this.loc).equals("saloon"))
                return "" + (l.saloon).size();
            else
                return "" + (l.ranch).size();
        }
        else
            retPlayer = l.randPlayer(this.name, this.loc);
            if (retPlayer == null)
                return null;
            return (l.randPlayer(this.name, this.loc)).name;
    }

    public int role(){
        return 1;
    }
    
    public int rob(Location l, String place){
        if (place.equals("bank"))
            l.bank.add(this);
        else if (place.equals("saloon"))
            l.saloon.add(this);
        else
            l.ranch.add(this);
        return l.getValue(place);
    }
}

