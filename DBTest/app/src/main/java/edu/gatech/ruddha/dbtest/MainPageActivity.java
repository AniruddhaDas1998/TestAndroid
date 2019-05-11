package edu.gatech.ruddha.dbtest;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainPageActivity extends AppCompatActivity {
    DatabaseHandler dh;
    AccountHolder currentPerson;

    Button logoutButton;
    Button loadButton;
    EditText usernameET;
    EditText passwordET;
    EditText contactET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        usernameET = (EditText) findViewById(R.id.editText_userid);
        passwordET = (EditText) findViewById(R.id.editText_password);
        contactET = (EditText) findViewById(R.id.editText_contact);
        logoutButton = (Button) findViewById(R.id.button_logout);
        loadButton = (Button) findViewById(R.id.button_load);

        dh = new DatabaseHandler(this);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dh.logout();
                Intent intent = new Intent(MainPageActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountHolder currentUser = dh.getCurrentLoggedIn();
                usernameET.setText(currentUser == null ? "NULL" : currentUser.getUserId());
                passwordET.setText(currentUser == null ? "NULL" : currentUser.getPassword());
                contactET.setText(currentUser == null ? "NULL" : currentUser.getContactInfo());
            }
        });
    }
}
