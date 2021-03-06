package edu.gatech.ruddha.dbtest;

/**
 * Created by Sanath on 2/8/2018.
 */

public class User extends AccountHolder {
    private String gender;
    private int[] dateOfBirth;
    // TODO: Not sure how to implement MedicalInfo
    private boolean isVeteran;
    // TODO: Not sure how to implement ShelterHistory
    User(String userID, String password, boolean lockedOut, String contactInfo, String gender,
         int[] dateOfBirth, boolean isVeteran) {
        super(userID, password, lockedOut, contactInfo);
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.isVeteran = isVeteran;
    }
    User(String userID, String contactInfo, String password) {
        this(userID, password, false, contactInfo, "Male", null, false);
    }
}
