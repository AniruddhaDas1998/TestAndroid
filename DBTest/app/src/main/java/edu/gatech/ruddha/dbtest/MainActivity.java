package edu.gatech.ruddha.dbtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.NoSuchElementException;

import edu.gatech.ruddha.util.PersonNotInDatabaseException;
import edu.gatech.ruddha.util.TooManyAttemptsException;
import edu.gatech.ruddha.util.WrongPasswordException;

public class MainActivity extends AppCompatActivity {

    EditText nameText;
    EditText passwordText;
    EditText secretText;
    Button getSI;
    Button addHero;
    Button reset;
    Button clear;
    Button showDB;

    DatabaseHandler dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dh = new DatabaseHandler(this);

        /*
         * Grab the dialog widgets so we can get info for later
         */

        nameText = (EditText) findViewById(R.id.editText_name);
        passwordText = (EditText) findViewById(R.id.editText_password);
        secretText = (EditText) findViewById(R.id.editText_secretID);
        getSI  = (Button) findViewById(R.id.button_getSI);
        addHero = (Button) findViewById(R.id.button_addHero);
        reset = (Button) findViewById(R.id.button_reset);
        clear = (Button) findViewById(R.id.button_clear);
        showDB = (Button) findViewById(R.id.button_showDB);

        showDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MainActivity", dh.viewDatabase());
                Toast.makeText(getApplicationContext(), "Check Logcat!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dh.resetLogins();
                Toast.makeText(getApplicationContext(), "Attempts cleared!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        getSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String password = passwordText.getText().toString();
                String text = "";
                try {
                    secretText.setText(dh.attemptGetContactInfoHolder(name, password));
                } catch (TooManyAttemptsException e) {
                    text = "Too many log-in attempts!";
                    secretText.setText("<LOCKED OUT>");
                } catch (PersonNotInDatabaseException e) {
                    text = "Username not found";
                    secretText.setText("");
                } catch (WrongPasswordException e) {
                    text = "Wrong password!";
                    secretText.setText("****");
                }
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(getApplicationContext(), text, duration).show();
            }
        });

        addHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String password = passwordText.getText().toString();
                String secretIdentity = secretText.getText().toString();
                if(dh.putUser(new User(name, secretIdentity, password))) {
                    Context context = getApplicationContext();
                    CharSequence text = "Added " + name + "!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = name + " already in database";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dh.clearDatabase();
                Context context = getApplicationContext();
                CharSequence text = "Database Cleared!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
}
