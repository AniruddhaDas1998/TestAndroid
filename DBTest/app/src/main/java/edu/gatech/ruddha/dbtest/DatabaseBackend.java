package edu.gatech.ruddha.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.NoSuchElementException;

import edu.gatech.ruddha.util.Encryption;

/**
 * Class representation for backend of Database
 *
 * @author Sanath Nagaraj
 * @version 6.9
 */
public class DatabaseBackend extends SQLiteOpenHelper {
    private Context mcontext;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "TESTDB";

    // Contacts table name
    private static final String TABLE_SUPERHEROES = "superheroes"; // TODO: Remove
    private static final String TABLE_USER = "userTable";

    // Contacts Table Columns names for both patient and physician
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ATTEMPTS = "attempts";
    private static final String KEY_CONTACT_INFO = "contactInfo";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_DOB = "dob";
    private static final String KEY_VETSTATUS = "veteran";


    private final String TAG = "DatabaseBackend";
    /**
     * Constructor for database backend
     *
     * @param context context of current execution
     */
    public DatabaseBackend(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mcontext = context;
        createDB();
    }

    /**
     * onCreate method to create db and satisfy superclass constraints. Check superclass
     * documentation for further information
     *
     * @param db is the SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    /**
     * onUpgrade method to create db and satisfy superclass constraints. Check superclass
     * documentation for further information.
     *
     * @param db is the SQLiteDatabase
     * @param oldVersion is the old version of the table
     * @param newVersion is the new version of the table
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If you need to add a column
        if (newVersion > oldVersion) {
            clearTables();
            createDB();
        }
    }
    /**
     * Private method to create a Database. Creates a single table to hold superhero data
     */
    private void createDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Creating Database
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SUPERHEROES + "("
                + KEY_USERNAME + " TEXT PRIMARY KEY," +
                KEY_PASSWORD + " TEXT," +
                KEY_ATTEMPTS + " INTEGER," +
                KEY_CONTACT_INFO + " TEXT," +
                KEY_GENDER + " TEXT," +
                KEY_DOB + " TEXT," +
                KEY_VETSTATUS + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }
    /**
     * Private method to clear the created table and remake it.
     */
    public void clearTables() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPERHEROES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        createDB();
    }
    /**
     * Public method to add a superhero
     *
     * @param user The user to add to the database
     * @return whether the add was successful
     * @throws NullPointerException if superhero is null
     */
    public boolean addUser(User user) {
        String name = user.getUserId();
        String password = Encryption.encode(user.getPassword());
        String contactInfo = user.getContactInfo();

        if (checkExists(name, TABLE_USER)) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        // Storing values for patient
        ContentValues newUser = new ContentValues();
        newUser.put(KEY_USERNAME, name);
        newUser.put(KEY_PASSWORD, password);
        newUser.put(KEY_ATTEMPTS, 0);
        newUser.put(KEY_CONTACT_INFO, contactInfo);
        newUser.put(KEY_GENDER, "TBD");
        newUser.put(KEY_DOB, "TBD");
        newUser.put(KEY_VETSTATUS, "TBD");
        // Inserting Row
        db.insert(TABLE_USER, null, newUser);
        db.close();
        return true;
    }

    /**
     * Private method to see if name exists as a key in tableSuperheroes
     *
     * @param name the name to check for in the database
     * @param tableName the superheroTable to check in for this name
     * @return whether name is a key in tableSuperheroes
     */
    private boolean checkExists(String name, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + tableName;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if (name.equals(cursor.getString(0))) {
                    db.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return false;
    }

    /**
     * Public method to get secret ID of superhero. Only returns ID if the name
     * and password are correct
     *
     * @param name the name of the superhero whose secret identity is queried
     * @param password the password of above superhero.
     * @return the secret ID if correct credentials or null if wrong
     * @throws NullPointerException if name or password are null
     * @throws NoSuchElementException if locked out
     */
    public String attemptLogin(String name, String password) {
        if (name == null || password == null) {
            throw new NullPointerException("You have entered a null username or password!");
        }
        password = Encryption.encode(password);
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        String contactInfo = null;
        if (cursor.moveToFirst()) {
            do {
                if (name.equals(cursor.getString(0))) {
                    //Log.d(TAG, "attempts so far " + cursor.getString(3))
                    if (Integer.parseInt(cursor.getString(3)) > 3) {
                        throw new NoSuchElementException("Too many attempts"); // TODO: Update
                    }
                    if (password.equals(cursor.getString(1))) {
                        contactInfo = cursor.getString(3);
                        String command = "UPDATE " + TABLE_USER + " SET "
                                + KEY_ATTEMPTS + " = 0 WHERE "
                                + KEY_USERNAME + "=?";
                        db.execSQL(command, new String[] {name});
                    } else {
                        String command = "UPDATE " + TABLE_USER + " SET "
                                + KEY_ATTEMPTS + " = " + KEY_ATTEMPTS + " +1 WHERE "
                                + KEY_USERNAME + "=?";
                        db.execSQL(command, new String[] {name});
                    }
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return contactInfo;
    }

    public String viewDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SUPERHEROES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String output = "";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                output = output + cursor.getString(0) + " | "
                        + cursor.getString(1) + " | "
                        + cursor.getString(2) + " | "
                        + cursor.getString(3) + "\n";
            } while (cursor.moveToNext());
        }
        return output;
    }
    /**
     * method to reset the attempts of logins for all users. For debugging purposes only.
     */
    public void resetLogins() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SUPERHEROES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String command = "UPDATE " + TABLE_SUPERHEROES + " SET "
                        + KEY_ATTEMPTS + " = 0 WHERE "
                        + KEY_USERNAME + "=?";
                db.execSQL(command, new String[] {name});
            } while (cursor.moveToNext());
        }
        db.close();
    }

    public HashMap<String, AccountHolder> getHashDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        HashMap<String, AccountHolder> output = new HashMap<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User(cursor.getString(0),
                        Encryption.decode(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)) > 3,
                        cursor.getString(3),
                        null, null, false);
                output.put(cursor.getString(0), user);
            } while (cursor.moveToNext());
        }
        return output;
    }
}
