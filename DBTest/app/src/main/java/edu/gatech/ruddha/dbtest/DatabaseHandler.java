package edu.gatech.ruddha.dbtest;

import android.content.Context;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Created by Sanath on 1/29/2018.
 */

public class DatabaseHandler {

    private static HashMap<String, Superhero> elements;

    private static HashMap<String, AccountHolder> holderElems;

    private DatabaseBackend db;

    private Context context;

    public DatabaseHandler(Context context) {
        this.context = context;
        db = new DatabaseBackend(this.context);
        elements = new HashMap<>();
        holderElems = new HashMap<>();
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
     * Adds accountholder to the backend
     *
     * @param accountHolder the Superhero to be added
     */
    boolean putUser(AccountHolder accountHolder) {
        boolean success = db.addUser(accountHolder);
        if (success) {
            holderElems.put(accountHolder.getUserId(), accountHolder);
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
     * Gets the accountholder with the userID
     *
     * @param userID The name of the superhero to be got
     * @return Superhero with the desired name
     */
    AccountHolder getHolder(String userID) {
        return holderElems.get(userID);
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
     * Public method to get contactInfo of accountholder
     *
     * @param userID the userID of the AccountHolder whose contactInfo is queried
     * @param password the password of above superhero.
     * @return the contactInfo if correct credentials or null if wrong
     * @throws NullPointerException if name or password are null
     */
    public String attemptGetContactInfoHolder(String userID, String password) {
        if (holderElems.containsKey(userID)) {
            AccountHolder ret = holderElems.get(userID);
            if (ret.getPassword().equals(password) && !ret.isLockedOut()) {
                return ret.getContactInfo();
            } else if (ret.isLockedOut()) {
                throw new NoSuchElementException("Account is locked out");
            }
        } else {
            return null;
        }
        try {
            return db.attemptGetSecretID(userID, password);
        } catch (NoSuchElementException e) {
            holderElems.get(userID).setLocketOut(true);
            throw e;
        }
    }

    /**
     * method to reset the attempts of logins for all users. For debugging purposes only.
     */
    public void resetLogins() {
        db.resetLogins();
        populate();
    }
}
