package edu.gatech.ruddha.dbtest;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Sanath on 1/29/2018.
 */

public class DatabaseHandler {

    private static HashMap<String, Superhero> elements;

    private DatabaseBackend db;

    private Context context;

    public DatabaseHandler(Context context) {
        this.context = context;
        db = new DatabaseBackend(this.context);
        elements = new HashMap<>();
        populate();
    }

    /**
     * Populates the hashmap with the backend database
     */
    private static void populate() {
        elements = elements;
    }

    /**
     * Clears the existing database
     */
    public void clearDatabase() {
        db.clearTables();
    }

    /**
     * Adds superhero to the backend
     *
     * @param superhero the Superhero to be added
     */
    boolean put(Superhero superhero) {
      boolean success = db.addSuperhero(superhero);
        if (success) {
            populate();
        }
        return success;
    }

    /**
     * Gets the superhero with the name
     *
     * @param name The name of the superhero to be got
     * @return Superhero with the desired name
     */
    Superhero get(String name) {
        return null;
    }

    /**
     * Public method to get secret ID of superhero
     *
     * @param name the name of the superhero whose secret identity is queried
     * @param password the password of above superhero.
     * @return the secret ID if correct credentials or null if wrong
     * @throws NullPointerException if name or password are null
     */
    public String attemptGetSecretID(String name, String password) {
        return db.attemptGetSecretID(name, password);
    }

    /**
     * method to reset the attempts of logins for all users. For debugging purposes only.
     */
    public void resetLogins() {
        db.resetLogins();
    }
}
