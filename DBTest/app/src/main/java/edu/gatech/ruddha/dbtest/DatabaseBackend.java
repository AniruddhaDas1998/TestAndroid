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
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TESTDB";

    // Contacts table name
    private static final String TABLE_SUPERHEROES = "superheroes";

    // Contacts Table Columns names for both patient and physician
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_SECRETID = "secretIdentity";
    private static final String KEY_ATTEMPTS = "attempts";


    private final String TAG = "DatabaseBackend";

    /**
     * Constructor for database backend
     *
     * @param context context of current execution
     */
    public DatabaseBackend(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mcontext = context;
        //clearTables();
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
    }
    /**
     * Private method to create a Database. Creates a single table to hold superhero data
     */
    private void createDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        /* **IMPORTANT** If you change the columns of a sqlite table, you have to uncomment
        the next lines of code relevant to that table and run the app to drop the existing table
        and create a new one. If you are getting a sqlite error, try dropping both tables once,
        then comment the lines again.
         */

        // Creating Database
        String CREATE_PATIENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SUPERHEROES + "("
                + KEY_NAME + " TEXT PRIMARY KEY," +
                KEY_PASSWORD + " TEXT," + KEY_SECRETID + " TEXT," +
                KEY_ATTEMPTS + " INTEGER"  + ")";
        db.execSQL(CREATE_PATIENTS_TABLE);
    }
    /**
     * Private method to clear the created table and remake it.
     */
    public void clearTables() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPERHEROES);
        createDB();
    }
    /**
     * Public method to add a superhero
     *
     * @param superhero The superhero to add to the database
     * @return whether the add was successful
     * @throws NullPointerException if superhero is null
     */
    public boolean addSuperhero(Superhero superhero) {
        String name = superhero.name;
        String password = superhero.password;
        password = Encryption.encode(password)
        String secretID = superhero.secretIdentity;
        if (checkExists(name, TABLE_SUPERHEROES)) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        // Storing values for patient
        ContentValues newHero = new ContentValues();
        newHero.put(KEY_NAME, name);
        newHero.put(KEY_PASSWORD, password);
        newHero.put(KEY_SECRETID, secretID);
        newHero.put(KEY_ATTEMPTS, 0);
        // Inserting Row
        db.insert(TABLE_SUPERHEROES, null, newHero);
        db.close();
        return true;
    }

    /**
     * Private method to see if name exists as a key in tableSuperheroes
     *
     * @param name the name to check for in the database
     * @param tableSuperheroes the superheroTable to check in for this name
     * @return whether name is a key in tableSuperheroes
     */
    private boolean checkExists(String name, String tableSuperheroes) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + tableSuperheroes;
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
    public String attemptGetSecretID(String name, String password) {
        if (name == null || password == null) {
            throw new NullPointerException("You have entered a null name or password!");
        }
        password = Encryption.encode(password);
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SUPERHEROES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        String secretID = null;
        if (cursor.moveToFirst()) {
            do {
                if (name.equals(cursor.getString(0))) {
                    //Log.d(TAG, "attempts so far " + cursor.getString(3))
                    if (Integer.parseInt(cursor.getString(3)) > 3) {
                        throw new NoSuchElementException("Too many attempts");
                    }
                    if (password.equals(cursor.getString(1))) {
                        secretID = cursor.getString(2);
                        String command = "UPDATE " + TABLE_SUPERHEROES + " SET "
                                + KEY_ATTEMPTS + " = 0 WHERE "
                                + KEY_NAME + "=?";
                        db.execSQL(command, new String[] {name});
                    } else {
                        String command = "UPDATE " + TABLE_SUPERHEROES + " SET "
                                + KEY_ATTEMPTS + " = " + KEY_ATTEMPTS + " +1 WHERE "
                                + KEY_NAME + "=?";
                        db.execSQL(command, new String[] {name});
                    }
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return secretID;
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
                        + KEY_NAME + "=?";
                db.execSQL(command, new String[] {name});
            } while (cursor.moveToNext());
        }
        db.close();
    }
    
    public HashMap<String, Superhero> getHashDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SUPERHEROES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        HashMap<String, Superhero> output = new HashMap<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Superhero sh = new Superhero(cursor.getString(0),
                        Encryption.decode(cursor.getString(1)),
                        cursor.getString(2));
                output.put(cursor.getString(0), sh);
            } while (cursor.moveToNext());
        }
        return output;
    }
}
