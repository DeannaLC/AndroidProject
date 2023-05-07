package edu.vassar.cmpu203.high_noon_heist.persistence;

import androidx.annotation.NonNull;

import edu.vassar.cmpu203.high_noon_heist.model.Leaderboard;

public interface IPersistenceFacade {
    void saveLeaderboard(@NonNull Leaderboard leaderboard);

    Leaderboard retrieveLeaderboard();
}
