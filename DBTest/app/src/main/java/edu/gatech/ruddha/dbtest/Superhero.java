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
    this.name = name;
    this.secretIdentity = secretIdentity;
    this.password = password;
    this.age = 21;
    this.areaOfOperation = "Planet Earth";
  }

  public Superhero(String name, String secretIdentity, String password, int age,
                   String areaOfOperation) {
    this.name = name;
    this.secretIdentity = secretIdentity;
    this.password = password;
    this.age = age;
    this.areaOfOperation = areaOfOperation;
  }
}
