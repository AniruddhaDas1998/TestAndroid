package edu.gatech.ruddha.dbtest;

import java.util.HashMap;

/**
 * Created by Sanath on 1/29/2018.
 */

public class DatabaseHandler {

  private HashMap<String, Superhero> elements;

  public DatabaseHandler() {
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
}
