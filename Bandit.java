import java.util.Random;

/**
 * Write a description of class Bandit here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bandit extends Player
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Bandit
     */
    public Bandit(String name)
    {
        super(name);
    }

    public String observation(Location l){
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
            return (l.randPlayer(this.name, this.loc)).name;
    }
    
    public int rob(Location l){
        return 0;
    }
}

