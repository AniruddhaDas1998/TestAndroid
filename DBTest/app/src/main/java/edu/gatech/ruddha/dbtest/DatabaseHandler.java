package edu.gatech.ruddha.dbtest;

import android.content.Context;

import java.util.HashMap;
import java.util.NoSuchElementException;

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
    private void populate() {
        elements = db.getHashDatabase();
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
            elements.put(superhero.name, superhero);
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
        return elements.get(name);
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
        if (elements.containsKey(name)) {
            Superhero ret = elements.get(name);
            if (ret.password.equals(password) && !ret.lockedOut) {
                return ret.secretIdentity;
            } else if (ret.lockedOut) {
                throw new NoSuchElementException("Account is locked out");
            }
        } else {
            return null;
        }
        try {
            return db.attemptGetSecretID(name, password);
        } catch (NoSuchElementException e) {
            elements.get(name).lockedOut = true;
            throw e;
        }
    }

    /**
     * method to reset the attempts of logins for all users. For debugging purposes only.
     */
    public void resetLogins() {
        db.resetLogins();
    }
}
