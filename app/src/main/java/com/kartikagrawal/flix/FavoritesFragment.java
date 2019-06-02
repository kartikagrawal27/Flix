package com.kartikagrawal.flix;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements MovieRecycleViewAdapter.OnGridListener{


    private FirebaseUser user;
    private FirebaseFirestore db;
    private String userId;

    private DocumentReference documentReference;

    private TextView noFavTextView;
    private RecyclerView recyclerView;

    private ArrayList<String> favMovieIds;

    //api class object
    private OpenMovieAPIClass openMovieAPIClass;

    private ArrayList<String> favMovieNames;
    private ArrayList<String> favMoviePosters;
    private ArrayList<String> favMovieYears;
    private ArrayList<String> favMovieDirectors;
    private ArrayList<String> favMoviePlots;

    private int NUM_COLUMNS = 3;

    VolleyExtrasCallbackInterface extrasListener;

    private ProgressBar progressBar;

    private MovieRecycleViewAdapter movieRecycleViewAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        this.db = FirebaseFirestore.getInstance();

        this.noFavTextView = view.findViewById(R.id.no_favs_found_text_view);

        this.recyclerView = view.findViewById(R.id.fav_recycler_view);
        this.progressBar = view.findViewById(R.id.load_favs_progress_bar);

        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.userId = user.getUid();
        this.documentReference = FirebaseFirestore.getInstance().document("users/" + this.userId);

        favMovieIds = new ArrayList<>();

        favMoviePosters = new ArrayList<>();
        favMovieNames = new ArrayList<>();
        favMovieDirectors = new ArrayList<>();
        favMovieYears = new ArrayList<>();
        favMoviePlots = new ArrayList<>();

        final MovieRecycleViewAdapter.OnGridListener listenerObject = this;

        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), NUM_COLUMNS);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(NUM_COLUMNS, 40, true));

        extrasListener = new VolleyExtrasCallbackInterface() {
            @Override
            public void onSuccessResponse(HashMap<String, String> result) {
                favMoviePosters.add(result.get("poster"));
                favMovieNames.add(result.get("name"));
                favMovieYears.add(result.get("year"));
                favMovieDirectors.add(result.get("director"));
                favMoviePlots.add(result.get("plot"));

                if(favMoviePosters.size() == favMovieIds.size()){

                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    //All queries have completed
//                    Toast.makeText(getContext(), "Loaded all favs", Toast.LENGTH_SHORT).show();

                    movieRecycleViewAdapter = new MovieRecycleViewAdapter(getContext(), favMoviePosters, favMovieIds, listenerObject);
                    recyclerView.setAdapter(movieRecycleViewAdapter);

                }
            }
        };

        this.documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    favMovieIds = (ArrayList<String>) documentSnapshot.get("favorites");
                    if(favMovieIds.size()!=0){
                        progressBar.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
//                        Toast.makeText(getContext(), "Got favs!", Toast.LENGTH_SHORT).show();
                        //Populate the datastructure here here
                        for (int i=0; i<favMovieIds.size(); i++){
                            openMovieAPIClass = new OpenMovieAPIClass(getContext());
                            openMovieAPIClass.searchById(favMovieIds.get(i), "full", extrasListener);
                        }

                        //Popuplate the recycler view here
                        //MovieRecycleViewAdapter movieRecycleViewAdapter = new MovieRecycleViewAdapter(getContext());

                    }
                    else{
//                        noFavTextView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to retrieve favorites", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

    @Override
    public void onGridClick(int position) {

        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("movie_poster", favMoviePosters.get(position));
        intent.putExtra("movie_name", favMovieNames.get(position));
        intent.putExtra("movie_director", favMovieDirectors.get(position));
        intent.putExtra("movie_year", favMovieYears.get(position));
        intent.putExtra("movie_synopsis", favMoviePlots.get(position));

        startActivity(intent);
    }
}
