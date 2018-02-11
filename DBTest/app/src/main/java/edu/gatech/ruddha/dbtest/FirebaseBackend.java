package edu.gatech.ruddha.dbtest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sanath on 2/10/2018.
 */

public class FirebaseBackend {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private static String TAG = "FirebaseBackend";

    FirebaseBackend() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public void addUser(final Context context, User user) {
        final User ogUser = user;
        String email = user.getUserId().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email += "@temp.com";
        }
        if (user.getPassword().length() < 7) {
            Toast.makeText(context, "Password too short!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (user.getUserId().isEmpty()) {
            Toast.makeText(context, "Username cannot be empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, user.getPassword().trim())
          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  if (!task.isSuccessful()) {
                      if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                          Toast.makeText(context, ogUser.getUserId() + " already exists",
                            Toast.LENGTH_SHORT).show();
                      } else {
                          throw new NullPointerException(task.getException().getMessage());
                      }
                  } else {
                      Toast.makeText(context, ogUser.getUserId() + " added!",
                        Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(context, MainPageActivity.class);
                      FirebaseUser firebaseUser = mAuth.getCurrentUser();
                      updateCurrentUser(firebaseUser.getUid(), ogUser);
                      intent.putExtra("UID", firebaseUser.getUid());
                      intent.putExtra("username", ogUser.getUserId());
                      intent.putExtra("password", ogUser.getPassword());
                      intent.putExtra("contactInfo", ogUser.getContactInfo());
                      context.startActivity(intent);
                  }
              }
          });
    }


    public void attemptLogin(final Context context, String username, String password) {
        username = username.trim();
        password = password.trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            username += "@temp.com";
        }
        mAuth.signInWithEmailAndPassword(username, password)
          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()) {
                      // Sign in success, update UI with the signed-in user's information
                      FirebaseUser user = mAuth.getCurrentUser();
                      Log.d(TAG, user.getEmail() + " " + user.getDisplayName());
                      Toast.makeText(context, "Authentication successful!",
                        Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(context, MainPageActivity.class);
                      intent.putExtra("UID", user.getUid());
                      context.startActivity(intent);
                  } else {
                      // If sign in fails, display a message to the user.
                      Toast.makeText(context, "Authentication failed!",
                        Toast.LENGTH_SHORT).show();
                  }
              }
          });
    }

    public void updateCurrentUser(String UID, User user) {
        myRef = database.getReference();
        myRef.child("users").child(UID).setValue(user);
    }

    public void logout() {
        mAuth.signOut();
    }

    public void resetLogins() {

    }

}
