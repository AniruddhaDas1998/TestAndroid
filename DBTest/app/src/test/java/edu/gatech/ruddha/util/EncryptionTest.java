package edu.gatech.ruddha.util;

import org.junit.Test;

/**
 * Created by aniruddhadas on 03/02/18.
 */
public class EncryptionTest {
  private String TAG = "EncryptionTest";
  @Test
    public void encryptionDecryption() {
      String test = "ranveer";
      String encrypted = Encryption.encode(test);
      System.out.println("Encrypted: " + encrypted);
      // Log.i(TAG, "Encrypted: " + encrypted);
      String decrypt = Encryption.decode(encrypted);
      assert(Encryption.decode(encrypted).equals(test));
    }
}