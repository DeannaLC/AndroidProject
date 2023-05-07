package edu.vassar.cmpu203.high_noon_heist.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.vassar.cmpu203.high_noon_heist.model.Leaderboard;

public class LocalStorageFacade implements IPersistenceFacade{
    private final File directory;
    private static final String FILENAME = "leaderboard";

    public LocalStorageFacade(@NonNull final File directory){
        this.directory = directory;
    }

    @Override
    public void saveLeaderboard(@NonNull Leaderboard leaderboard) {

        File outFile = new File(this.directory, FILENAME);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(leaderboard);

        } catch (FileNotFoundException e) {
            final String emsg = String.format("Couldn't find file %s", outFile.getAbsolutePath());
            Log.e("NextGenPos", emsg);
            e.printStackTrace();
        } catch (IOException e) {
            final String emsg = String.format("I/O error writing to %s", outFile);
            Log.e("NextGenPos", emsg);
            e.printStackTrace();
        }
    }

    public Leaderboard retrieveLeaderboard(){
        Leaderboard leaderboard = null; // null to begin with for negative outcome

        File inFile  = new File(this.directory, FILENAME);
        if (inFile.isFile()) { // must check that the file actually exists
            try {
                FileInputStream fileInStream = new FileInputStream(inFile); // get a stream to read from
                ObjectInputStream objectInStream = new ObjectInputStream(fileInStream); // lets us read objects instead of just numbers
                leaderboard = (Leaderboard) objectInStream.readObject(); // must downcast from Object
            }
            catch (IOException e) {
                final String emsg = String.format("I/O error reading from %s", inFile);
                Log.e("NextGenPos", emsg);
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                final String emsg = String.format("Can't find class of object from %s", inFile);
                Log.e("NextGenPos", emsg);
                e.printStackTrace();
            }
        }
        return leaderboard;
    }

}
