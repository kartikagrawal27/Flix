package com.kartikagrawal.flix;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    List<AuthUI.IdpConfig> providers;
    private static final int RC_SIGN_IN = 9000;
    private static final String TAG = "Mylogs - MainActivity";
    private FirebaseUser user;

    private DocumentReference documentReference;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();

        Log.d(TAG, "onCreate() called");

        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(TAG, "User already logged in, opening home activity");
            Intent home_intent = new Intent(this, HomeActivity.class);
            startActivity(home_intent);
            finish();
        } else {
            Log.d(TAG, "Initializing providers");
            providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );

            showSignInOption();
        }
    }

    private void showSignInOption() {


        Log.d(TAG, "Displaying options");
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
        if (RC_SIGN_IN == requestCode) {
            if (resultCode == RESULT_OK) {

                user = FirebaseAuth.getInstance().getCurrentUser();
                documentReference = FirebaseFirestore.getInstance().document("users/" + user.getUid());
                Log.d(TAG, "onActivityResult() called");
                boolean first_timer = true;

                //Firebase add user id to firebase db
                Map<String, Object> uploadNewUser = new HashMap<>();
                uploadNewUser.put("favorites", new ArrayList<>());

                documentReference.set(uploadNewUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplication(), "Added user to db", Toast.LENGTH_SHORT);
                    }
                });

                Intent home_intent = new Intent(this, HomeActivity.class);
                home_intent.putExtra("firsttimer", first_timer);
                startActivity(home_intent);
                finish();
            }
        }
    }
}
