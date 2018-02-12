package edu.gatech.ruddha.dbtest;

import java.util.Arrays;
import java.util.LinkedList;
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
    private String favColor;
    User(String userID, String password, boolean lockedOut, String contactInfo, String gender,
         List<Integer> dateOfBirth, boolean isVeteran, String favColor) {
        super(userID, password, lockedOut, contactInfo);
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.isVeteran = isVeteran;
        this.favColor = favColor;
    }
    User(String userID, String password, boolean lockedOut, String contactInfo, String gender,
         List<Integer> dateOfBirth, boolean isVeteran) {
        super(userID, password, lockedOut, contactInfo);
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.isVeteran = isVeteran;
        this.favColor = null;
    }
    User(String userID, String contactInfo, String password) {
        this(userID, password, false, contactInfo, null, Arrays.asList(8, 5, 98),
                false, null);
    }
    public User() {

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Integer> getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(List<Integer> dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isVeteran() {
        return isVeteran;
    }

    public void setVeteran(boolean veteran) {
        isVeteran = veteran;
    }

    public String getFavColor() {
        return favColor;
    }

    public void setFavColor(String favColor) {
        this.favColor = favColor;
    }
}
