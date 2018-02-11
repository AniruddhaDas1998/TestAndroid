package edu.gatech.ruddha.dbtest;

import java.util.List;

/**
 * Created by Sanath on 2/8/2018.
 */

public class User extends AccountHolder {
    private String gender;
    private List<Integer> dateOfBirth;
    // TODO: Not sure how to implement MedicalInfo
    private boolean isVeteran;
    // TODO: Not sure how to implement ShelterHistory
    User(String userID, String password, boolean lockedOut, String contactInfo, String gender,
         List<Integer> dateOfBirth, boolean isVeteran) {
        super(userID, password, lockedOut, contactInfo);
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.isVeteran = isVeteran;
    }
    User(String userID, String contactInfo, String password) {
        this(userID, password, false, contactInfo, "Male", null, false);
    }
    public User() {

    }

    public String getGender() {
        return gender;
    }

    public List<Integer> getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isVeteran() {
        return isVeteran;
    }
}
