package edu.gatech.ruddha.util;

import org.junit.Test;

/**
 * Created by aniruddhadas on 03/02/18.
 */
public class EncryptionTest {
  private String TAG = "EncryptionTest";
  @Test
    public void encryptionDecryption() {
      String test = "wontilolo";
      String encrypted = Encryption.encode(test);
      System.out.println("Encrypted: " + encrypted);
      //Log.i(TAG, "Encrypted: " + encrypted);
      assert(Encryption.decode(encrypted).equals(test));
    }
}