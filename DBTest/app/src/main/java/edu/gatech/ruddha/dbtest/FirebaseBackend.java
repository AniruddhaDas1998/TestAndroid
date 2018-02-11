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

/**
 * Created by Sanath on 2/10/2018.
 */

public class FirebaseBackend {

    private FirebaseAuth mAuth;

    private static String TAG = "FirebaseBackend";

    private boolean isSuccess;
    // private boolean changed;

    FirebaseBackend() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void addUser(final Context context, User user) {
        final String ogName = user.getUserId();
        String email = user.getUserId();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email += "@temp.com";
        }
        // TODO: Check password length and blank inputs
        mAuth.createUserWithEmailAndPassword(email, user.getPassword())
          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  if (!task.isSuccessful()) {
                      if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                          Toast.makeText(context, ogName + " already exists",
                            Toast.LENGTH_SHORT).show();
                      } else {
                          throw new NullPointerException(task.getException().getMessage());
                      }
                  } else {
                      Toast.makeText(context, ogName + " added!",
                        Toast.LENGTH_SHORT).show();
                  }
              }
          });
    }


    public void attemptLogin(final Context context, String username, String password) {
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
                      context.startActivity(intent);
                      mAuth.signOut();
                  } else {
                      // If sign in fails, display a message to the user.
                      Toast.makeText(context, "Authentication failed!",
                        Toast.LENGTH_SHORT).show();
                  }
              }
          });
    }

    public void resetLogins() {

    }

}
