package com.kartikagrawal.flix;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<AuthUI.IdpConfig> providers;
    private static final int RC_SIGN_IN = 9000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        showSignInOption();
    }

    private void showSignInOption(){

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RC_SIGN_IN==requestCode){

            IdpResponse repsonse = IdpResponse.fromResultIntent(data);
            if(resultCode==RESULT_OK){

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_LONG).show();

            }
        }
    }
}
