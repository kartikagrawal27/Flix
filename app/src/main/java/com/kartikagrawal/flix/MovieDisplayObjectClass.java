package com.kartikagrawal.flix;

import java.util.ArrayList;

public class MovieDisplayObjectClass {

    //Load from MovieResultsClass
    protected ArrayList<String> movieNames;
    protected ArrayList<String> movieYears;
    protected ArrayList<String> movieIds;
    protected ArrayList<String> movieImageURIs;
    protected ArrayList<String> movieDirectors;
    protected ArrayList<String> movieSynopsiss;

    public MovieDisplayObjectClass() {

    }

    public MovieDisplayObjectClass(ArrayList<MovieSearchResultClass> movies) {

        this.movieNames = new ArrayList<>();
        this.movieYears = new ArrayList<>();
        this.movieIds = new ArrayList<>();
        this.movieImageURIs = new ArrayList<>();
        this.movieDirectors = new ArrayList<>();
        this.movieSynopsiss = new ArrayList<>();

        for (MovieSearchResultClass movie : movies) {
            movieNames.add(movie.movieTitle);
            movieYears.add(movie.movieYear);
            movieIds.add(movie.movieId);
            movieImageURIs.add(movie.moviePoster);
        }
    }
}
