package com.kartikagrawal.flix;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements MovieRecycleViewAdapter.OnGridListener {

    //searchbar
    private EditText search_entry_edit_text;
    private ImageButton search_button;

    //no_movies
    private TextView no_movies;

    //recyclerview
    private RecyclerView recyclerView;
    private static final int NUM_COLUMNS = 3;

    //api class object
    private OpenMovieAPIClass openMovieAPIClass;

    //recycle view adapter
    private MovieRecycleViewAdapter movieRecycleViewAdapter;

    //moviesList
    private MovieDisplayObjectClass movieDisplayObject;

    //movies search result format
    ArrayList<MovieSearchResultClass> movieSearchResultClassArrayList;

    //REST Listeners
    VolleySearchCallbackInterface searchListener;
    VolleyExtrasCallbackInterface extrasListener;

    //
    private ProgressBar progressBar;

    //Firebase db
//    FirebaseFirestore db;


    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Resuming", "activity resume");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        db = FirebaseFirestore.getInstance();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        search_entry_edit_text = view.findViewById(R.id.movie_search__edit_text);
        search_button = view.findViewById(R.id.search_image_button);
        progressBar = view.findViewById(R.id.movies_progress_bar);
        no_movies = view.findViewById(R.id.no_movies_found_text_view);
        no_movies.setEnabled(false);

        //Onclick listener
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                openMovieAPIClass = new OpenMovieAPIClass(getContext());
//                Toast.makeText(getContext(), "Searched", Toast.LENGTH_SHORT).show();
                String search_term = search_entry_edit_text.getText().toString();

                //Dismiss the keyboard
                View focusView = getActivity().getWindow().getCurrentFocus();
                if (focusView != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                }
                updateMovieList(search_term);
            }
        });

        final MovieRecycleViewAdapter.OnGridListener listenerObject = this;

        extrasListener = new VolleyExtrasCallbackInterface() {
            @Override
            public void onSuccessResponse(HashMap<String, String> result) {
                movieDisplayObject.movieDirectors.add(result.get("director"));
                movieDisplayObject.movieSynopsiss.add(result.get("plot"));

                if(movieDisplayObject.movieSynopsiss.size() == movieDisplayObject.movieNames.size()){

                    //All queries have completed

                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Loaded all data", Toast.LENGTH_SHORT).show();

                    movieRecycleViewAdapter = new MovieRecycleViewAdapter(getContext(), movieDisplayObject.movieImageURIs, movieDisplayObject.movieIds,listenerObject);
                    recyclerView.setAdapter(movieRecycleViewAdapter);
                }
            }
        };

        //Listener
        searchListener = new VolleySearchCallbackInterface() {

            @Override
            public void onSuccessResponse(ArrayList<MovieSearchResultClass> result) {

                if(result.size()==0){
                    //Display no movies found
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    no_movies.setVisibility(View.VISIBLE);
                    return;
                }

                no_movies.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                //object of type Array<MovieSearchResultClass>
                movieSearchResultClassArrayList = result;

                //Object of MovieDisplayObjectClass
                movieDisplayObject = new MovieDisplayObjectClass(result);

                //get director and synopsis info
                getExtras(movieSearchResultClassArrayList, extrasListener);
            }
        };


        recyclerView = view.findViewById(R.id.movies_recycler_view);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), NUM_COLUMNS);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(NUM_COLUMNS, 40, true));

        return view;
    }

    private void updateMovieList(String search_term) {

        openMovieAPIClass.searchMoviesForRecyclerView(search_term, getContext(), searchListener);
    }

    private void getExtras(ArrayList<MovieSearchResultClass> moviesArrayList, VolleyExtrasCallbackInterface extrasListener) {

        for(MovieSearchResultClass movie : moviesArrayList){
            openMovieAPIClass.searchById(movie.movieId, "full", extrasListener);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //I am not sure what code to type in here

    }

    @Override
    public void onGridClick(int position) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("movie_poster", movieDisplayObject.movieImageURIs.get(position));
        intent.putExtra("movie_name", movieDisplayObject.movieNames.get(position));
        intent.putExtra("movie_director", movieDisplayObject.movieDirectors.get(position));
        intent.putExtra("movie_year", movieDisplayObject.movieYears.get(position));
        intent.putExtra("movie_synopsis", movieDisplayObject.movieSynopsiss.get(position));

        startActivity(intent);


    }
}
