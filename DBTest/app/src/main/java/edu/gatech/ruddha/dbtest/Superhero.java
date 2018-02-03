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

  private byte encryptionKey;

  public Superhero(String name, String secretIdentity, String password) {
    this(name, secretIdentity, password, 21, "Planet Earth");
  }

  /**
   * Takes in a string, encodes it and returns encoded string
   *
   * @param password String to be encoded
   * @return encoded string
   */
  private String encode(String password) {
    char[] encodedString = new char[password.length()];
    for (int i = 0; i < password.length(); i++) {
      encodedString[i] = (char) ((byte)password.charAt(i) ^ encryptionKey);
    }
    return new String(encodedString);
  }

  /**
   * Takes in a string, decodes it and returns decoded string
   *
   * @param password String to be decoded
   * @return decoded string
   */
  public String decode(String password) {
    char[] decodedString = new char[password.length()];
    for (int i = 0; i < password.length(); i++) {
      decodedString[i] = (char) ((byte)password.charAt(i) ^ encryptionKey);
    }
    return new String(decodedString);
  }

 // public boolean isCorrect()

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
