package edu.gatech.ruddha.dbtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

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


    public AccountHolder attemptLogin(String username, String password) {
        return null;
    }

    public void resetLogins() {

    }

}
