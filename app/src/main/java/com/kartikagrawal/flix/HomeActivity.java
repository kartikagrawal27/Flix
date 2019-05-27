package com.kartikagrawal.flix;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        boolean first_timer = intent.getBooleanExtra("firsttimer", false);

        if(first_timer){
            Toast.makeText(this, "Hello "+user.getDisplayName()+"!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Welcome Back "+user.getDisplayName()+"!", Toast.LENGTH_LONG).show();
        }
    }

    public void signOut(View view) {
        AuthUI.getInstance()
                .signOut(HomeActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
