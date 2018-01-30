package edu.gatech.ruddha.dbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText nameText = (EditText) findViewById(R.id.editText_name);
    EditText passwordText = (EditText) findViewById(R.id.editText_password);
    EditText secretText = (EditText) findViewById(R.id.editText_secretID);
    Button getSI = (Button) findViewById(R.id.button_getSI);
    Button addHero = (Button) findViewById(R.id.button_addHero);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
