package edu.gatech.ruddha.dbtest;

import android.content.Context;

import java.util.HashMap;
import java.util.NoSuchElementException;

import edu.gatech.ruddha.util.TooManyAttemptsException;

/**
 * Created by Sanath on 1/29/2018.
 */

public class DatabaseHandler {

    private static HashMap<String, AccountHolder> holderElems;

    private DatabaseBackend db;

    private Context context;

    public DatabaseHandler(Context context) {
        this.context = context;
        db = new DatabaseBackend(this.context);
        holderElems = new HashMap<>();
        populate();
    }

    /**
     * Populates the hashmap with the backend database
     */
    private void populate() {
        holderElems = db.getHashDatabase();
    }

    /**
     * Clears the existing database
     */
    public void clearDatabase() {
        db.clearTables();
    }

    /**
     * Adds accountholder to the backend
     *
     * @param accountHolder the Superhero to be added
     */
    boolean putUser(AccountHolder accountHolder) {
        boolean success = db.addUser((User) accountHolder);
        if (success) {
            holderElems.put(accountHolder.getUserId(), accountHolder);
        }
        return success;
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
                throw new TooManyAttemptsException();
            }
        } else {
            return null;
        }
        try {
            return db.attemptLogin(userID, password);
        } catch (TooManyAttemptsException e) {
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

    public String viewDatabase() {
        return db.viewDatabase();
    }
}
