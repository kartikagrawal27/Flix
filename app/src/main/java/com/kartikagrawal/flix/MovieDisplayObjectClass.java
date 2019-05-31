package com.kartikagrawal.flix;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MovieDisplayObjectClass{

    //Load from MovieResultsClass
    protected ArrayList<String> movieNames;
    protected ArrayList<String> movieYears;
    protected ArrayList<String> movieIds;
    protected ArrayList<String> movieImageURIs;

    //load with another API call
    protected ArrayList<String> movieDirectors;
    protected ArrayList<String> movieSynopsiss;
    protected Context context;
    HashMap<String, String> extras;


    public MovieDisplayObjectClass(ArrayList<MovieSearchResultClass> movies, final Context context) {

        this.context = context;
        OpenMovieAPIClass openAPI = new OpenMovieAPIClass(context);


        movieNames = new ArrayList<>();
        movieYears = new ArrayList<>();
        movieIds =  new ArrayList<>();
        movieImageURIs = new ArrayList<>();
        movieDirectors = new ArrayList<>();
        movieSynopsiss = new ArrayList<>();

//        VolleyCallbackInterface listener = new VolleyCallbackInterface() {
//            @Override
//            public void onSuccessResponse(MovieDisplayObjectClass result) {
//                Toast.makeText(context, "Results loaded", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSuccessResponse(ArrayList<MovieSearchResultClass> result) {
//
//            }
//
//            @Override
//            public void onSuccessResponse(HashMap<String, String> result) {
//                Toast.makeText(context, "More results loaded", Toast.LENGTH_SHORT).show();
//                extras = result;
//                movieDirectors.add(extras.get("director"));
//                movieSynopsiss.add(extras.get("plot"));
//                if(movieDirectors.size()==movieNames.size()){
//                    MoviesFragment.updateFinalList(movieNames, movieDirectors);
//                }
//            }
//        };

        for(MovieSearchResultClass movie: movies){
            movieNames.add(movie.movieTitle);
            movieYears.add(movie.movieYear);
            movieIds.add(movie.movieId);
            movieImageURIs.add(movie.moviePoster);
//            openAPI.searchById(movie.movieId, "short", listener);
        }
    }

}
