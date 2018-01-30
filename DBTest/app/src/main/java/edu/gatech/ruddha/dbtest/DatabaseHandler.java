package edu.gatech.ruddha.dbtest;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Sanath on 1/29/2018.
 */

public class DatabaseHandler {

  private HashMap<String, Superhero> elements;

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

  }

  /**
   * Adds superhero to the backend
   *
   * @param superhero the Superhero to be added
   */
  void put(Superhero superhero) {
      db.addSuperhero(superhero);
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
}
