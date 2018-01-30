package edu.gatech.ruddha.dbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText nameText;
    EditText passwordText;
    EditText secretText;
    Button getSI;
    Button addHero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
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
                secretText.setText("");
            }
        });
    }

}
