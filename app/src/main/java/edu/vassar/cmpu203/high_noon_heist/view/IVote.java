package edu.vassar.cmpu203.high_noon_heist.view;

import edu.vassar.cmpu203.high_noon_heist.model.Player;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

public interface IVote {
    interface Listener{
        public void addVote(Player p);

        public void subVote(Player p);

        public Player findPlayer(String name);

        public Player onSubmitVotes();

        public int getCurDay();

        public void onVotingDone();

        public PlayerList getPlayers();

        public boolean getTestMode();
    }
}
