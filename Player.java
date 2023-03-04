
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    boolean alive = true; 
    String name;
    String loc;
    int votes;
    
    public Player(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void observe(Location l, String loc){
        this.loc = loc;
        if (loc.equals("bank")){
            (l.bank).add(this);
            System.out.println();
        }
        else if (loc.equals("saloon"))
            (l.saloon).add(this);
        else
            (l.ranch).add(this);
    }
    
    public void vote(int votes){
        this.votes = votes;
    }
    
    void clearVotes(){
        this.votes = 0;
    }
}
