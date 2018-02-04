package edu.gatech.ruddha.dbtest;

/**
 * Created by aniruddhadas on 29/01/18.
 */

public class Superhero {
  public String name;
  public String secretIdentity;
  public String password;
  public int age;
  public String areaOfOperation;
  public boolean lockedOut;

  public Superhero(String name, String secretIdentity, String password) {
    this(name, secretIdentity, password, 21, "Planet Earth", false);
  }

  public Superhero(String name, String secretIdentity, String password, boolean lockedOut) {
    this(name, secretIdentity, password, 21, "Planet Earth", lockedOut);
  }

  public Superhero(String name, String secretIdentity, String password, int age,
                   String areaOfOperation, boolean lockedOut) {
    this.name = name;
    this.secretIdentity = secretIdentity;
    this.password = password;
    this.age = age;
    this.areaOfOperation = areaOfOperation;
    this.lockedOut = lockedOut;
  }
}
