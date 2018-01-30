package edu.gatech.ruddha.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

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
        // DO NOTHING
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        //onCreate(db);
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
                KEY_PASSWORD + " TEXT," + KEY_SECRETID + " TEXT" + ")";
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
        String secretID = superhero.secretIdentity;
        /*if (checkExists(email, TABLE_PHYSICIANS)) {
            return false;
        }*/
        SQLiteDatabase db = this.getWritableDatabase();
        // Storing values for patient
        ContentValues newHero = new ContentValues();
        newHero.put(KEY_NAME, name);
        newHero.put(KEY_PASSWORD, password);
        newHero.put(KEY_SECRETID, secretID);
        // Inserting Row
        db.insert(TABLE_SUPERHEROES, null, newHero);
        db.close();
        return true;
    }
    /**
     * Public method to get secret ID of superhero. Only returns ID if the name
     * and password are correct
     *
     * @param name the name of the superhero whose secret identity is queried
     * @param password the password of above superhero.
     * @return the secret ID if correct credentials or null if wrong
     * @throws NullPointerException if name or password are null
     */
    public String attemptGetSecretID(String name, String password) {
        if (name == null || password == null) {
            throw new NullPointerException("You have entered a null name or password!");
        }
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SUPERHEROES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        String secretID = null;
        if (cursor.moveToFirst()) {
            do {
                if (name.equals(cursor.getString(0)) && password.equals(cursor.getString(1))) {
                    secretID = cursor.getString(2);
                }
            } while (cursor.moveToNext());
        }
        return secretID;
    }
}
