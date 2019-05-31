package com.kartikagrawal.flix;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

    //no_movies
    private TextView no_movies;

    //recyclerview
    private RecyclerView rView;
    private static final int NUM_COLUMNS = 3;

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
    public void onResume() {
        super.onResume();
        Log.d("Resuming", "activity resume");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        search_entry_edit_text = view.findViewById(R.id.movie_search__edit_text);
        search_button = view.findViewById(R.id.search_image_button);

        openMovieAPIClass = new OpenMovieAPIClass(getContext());

        no_movies = view.findViewById(R.id.no_movies_found_text_view);
        no_movies.setEnabled(false);

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

                    movieRecycleViewAdapter = new MovieRecycleViewAdapter(getContext(), movieDisplayObject.movieImageURIs);
                    rView.setAdapter(movieRecycleViewAdapter);

                }
            }
        };

        //Listener
        searchListener = new VolleySearchCallbackInterface() {
            @Override
            public void onSuccessResponse(ArrayList<MovieSearchResultClass> result) {

                if(result.size()==0){
                    //Display no movies found
                    rView.setVisibility(View.INVISIBLE);
                    no_movies.setVisibility(View.VISIBLE);
                    return;
                }

                no_movies.setVisibility(View.INVISIBLE);
                rView.setVisibility(View.VISIBLE);
                //object of type Array<MovieSearchResultClass>
                movieSearchResultClassArrayList = result;

                //Object of MovieDisplayObjectClass
                movieDisplayObject = new MovieDisplayObjectClass(result);

                //get director and synopsis info
                getExtras(movieSearchResultClassArrayList, extrasListener);
            }
        };

        if(movieDisplayObject==null){
            //initRecyclerView
            rView = view.findViewById(R.id.movies_recycler_view);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), NUM_COLUMNS);
            rView.setLayoutManager(manager);
            rView.addItemDecoration(new GridSpacingItemDecoration(NUM_COLUMNS, 40, true));
        }


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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //I am not sure what code to type in here

    }

}
