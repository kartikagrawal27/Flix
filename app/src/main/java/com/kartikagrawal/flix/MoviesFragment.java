package com.kartikagrawal.flix;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment{

    //searchbar
    private EditText search_entry_edit_text;
    private ImageButton search_button;

    //recyclerview
    private RecyclerView rView;
    private static final int NUM_COLUMNS = 2;

    //metadata
    private ArrayList<String> moviePosterURIs;
    private ArrayList<String> movieNames;
    private ArrayList<String> movieDirectors;
    private ArrayList<String> movieYears;
    private ArrayList<String> movieSynopsiss;

    //api class object
    private OpenMovieAPIClass openMovieAPIClass;

    //recycle view adapter
    private MovieRecycleViewAdapter movieRecycleViewAdapter;

    //moviesList
    private MovieDisplayObjectClass movieDisplayObject;

    //
    ArrayList<MovieSearchResultClass> movieSearchResultClassArrayList;

    //
    VolleySearchCallbackInterface searchListener;
    VolleyExtrasCallbackInterface extrasListener;


    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        search_entry_edit_text = view.findViewById(R.id.movie_search__edit_text);
        search_button = view.findViewById(R.id.search_image_button);

        openMovieAPIClass = new OpenMovieAPIClass(getContext());

        moviePosterURIs = new ArrayList<>();
        movieNames = new ArrayList<>();
        movieDirectors = new ArrayList<>();
        movieYears = new ArrayList<>();
        movieSynopsiss = new ArrayList<>();

        //Onclick listener
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Searched", Toast.LENGTH_SHORT).show();
                String search_term = search_entry_edit_text.getText().toString();
                updateMovieList(search_term);
            }
        });

        extrasListener = new VolleyExtrasCallbackInterface() {
            @Override
            public void onSuccessResponse(HashMap<String, String> result) {
                movieDisplayObject.movieDirectors.add(result.get("director"));
                movieDisplayObject.movieSynopsiss.add(result.get("plot"));
                if(movieDisplayObject.movieSynopsiss.size() == movieDisplayObject.movieNames.size()){

                    //All queries have completed
                    Toast.makeText(getContext(), "Loaded all data", Toast.LENGTH_SHORT).show();
                }
            }
        };

        //Listener
        searchListener = new VolleySearchCallbackInterface() {
            @Override
            public void onSuccessResponse(ArrayList<MovieSearchResultClass> result) {
                //object of type Array<MovieSearchResultClass>
                movieSearchResultClassArrayList = result;

                //Object of MovieDisplayObjectClass
                movieDisplayObject = new MovieDisplayObjectClass(result, getContext());

                //
                getExtras(movieSearchResultClassArrayList, extrasListener);
            }
        };

        //initRecyclerView
        initRecyclerView(view);

        return view;
    }

    private void updateMovieList(String search_term) {

        openMovieAPIClass.searchMoviesForRecyclerView(search_term, getContext(), searchListener);
    }

    private void getExtras(ArrayList<MovieSearchResultClass> moviesArrayList, VolleyExtrasCallbackInterface extrasListener) {

        for(MovieSearchResultClass movie : moviesArrayList){
            openMovieAPIClass.searchById(movie.movieId, "short", extrasListener);
        }
    }

    private void initRecyclerView(View view) {
        rView = view.findViewById(R.id.movies_recycler_view);
        movieRecycleViewAdapter = new MovieRecycleViewAdapter(getContext(), moviePosterURIs, movieNames, movieDirectors, movieYears, movieSynopsiss);
        rView.setAdapter(movieRecycleViewAdapter);
        rView.setLayoutManager(new GridLayoutManager(this.getContext(), NUM_COLUMNS));
    }
}
