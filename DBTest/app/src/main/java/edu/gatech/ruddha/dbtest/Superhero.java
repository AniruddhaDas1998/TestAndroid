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

  public Superhero(String name, String secretIdentity, String password) {
    this(name, secretIdentity, password, 21, "Planet Earth");
  }

  public Superhero(String name, String secretIdentity, String password, int age,
                   String areaOfOperation) {
    this.name = name;
    this.secretIdentity = secretIdentity;
    // this.encryptionKey = (byte) (Math.random() * 256); // assigns a random encryption key
    // this.password = encode(password);
    this.password = password;
    this.age = age;
    this.areaOfOperation = areaOfOperation;
  }
}
