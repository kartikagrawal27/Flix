package com.kartikagrawal.flix;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationView nav_menu;
    private FrameLayout fragment_container;

    private FavoritesFragment fav_fragment;
    private MoviesFragment movies_fragment;
    private ProfileFragment profile_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Get view objects
        fragment_container = findViewById(R.id.fragmentContainer);
        nav_menu = findViewById(R.id.nav_menu);

        //Get user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Get first timer status
        Intent intent = getIntent();
        boolean first_timer = intent.getBooleanExtra("firsttimer", false);

        //Make Toast
        if(first_timer){
            Toast.makeText(this, "Hello "+user.getDisplayName()+"!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Welcome Back "+user.getDisplayName()+"!", Toast.LENGTH_LONG).show();
        }

        //Initialize fragments
        fav_fragment = new FavoritesFragment();
        movies_fragment = new MoviesFragment();
        profile_fragment = new ProfileFragment();

        if(findViewById(R.id.fragmentContainer)!=null){

            if(savedInstanceState!=null){
                return;
            }
            loadFragment(movies_fragment);
            nav_menu.setSelectedItemId(R.id.nav_movies);
        }

        nav_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()) {
                    case R.id.nav_favorites:{
                        loadFragment(fav_fragment);
                        return true;
                    }

                    case R.id.nav_movies:{
                        loadFragment(movies_fragment);
                        return true;
                    }

                    case R.id.nav_profile:{
                        loadFragment(profile_fragment);
                        return true;
                    }

                    default:
                        return false;
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, null);
        fragmentTransaction.commit();
    }


    public void signOut(View view) {

    }
}
