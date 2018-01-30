package edu.gatech.ruddha.dbtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameText;
    EditText passwordText;
    EditText secretText;
    Button getSI;
    Button addHero;

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

        getSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String password = passwordText.getText().toString();
                String secretID = dh.attemptGetSecretID(name, password);
                secretText.setText(secretID != null ? secretID : "Hidden");
            }
        });

        addHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String password = passwordText.getText().toString();
                String secretIdentity = secretText.getText().toString();
                if(dh.put(new Superhero(name, secretIdentity, password))) {
                    Context context = getApplicationContext();
                    CharSequence text = "Added " + name + "!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }

}
