package com.kartikagrawal.flix;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

public class MovieDisplayClass {

    //Load from MovieResultsClass
    private ArrayList<String> movieNames;
    private ArrayList<String> movieYears;
    private ArrayList<String> movieId;
    private ArrayList<String> movieImageURIs;

    //load with another API call
    private ArrayList<String> movieDirectors;
    private ArrayList<String> movieSynopsiss;
    private Context context;


    public MovieDisplayClass(ArrayList<MovieResultsClass> movies, Context context) {

        this.context = context;
        OpenMovieAPIClass openAPI = new OpenMovieAPIClass(context);
        HashMap<String, String> extras;

        for(MovieResultsClass movie: movies){
            movieNames.add(movie.movieTitle);
            movieYears.add(movie.movieYear);
            movieId.add(movie.movieId);
            movieImageURIs.add(movie.moviePoster);

            extras = openAPI.searchById(movie.movieId, "short");
            movieDirectors.add(extras.get("director"));
            movieSynopsiss.add(extras.get("plot"));

        }
    }
}
