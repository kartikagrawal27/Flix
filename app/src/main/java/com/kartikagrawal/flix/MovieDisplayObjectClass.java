package com.kartikagrawal.flix;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MovieDisplayObjectClass {

    //Load from MovieResultsClass
    protected ArrayList<String> movieNames;
    protected ArrayList<String> movieYears;
    protected ArrayList<String> movieIds;
    protected ArrayList<String> movieImageURIs;
    protected ArrayList<String> movieDirectors;
    protected ArrayList<String> movieSynopsiss;
    protected ArrayList<String> movieRateds;
    protected ArrayList<String> movieIMDbRatings;
    protected ArrayList<String> movieMetascore;

    public MovieDisplayObjectClass() {
        this.movieNames = new ArrayList<>();
        this.movieYears = new ArrayList<>();
        this.movieIds = new ArrayList<>();
        this.movieImageURIs = new ArrayList<>();
        this.movieDirectors = new ArrayList<>();
        this.movieSynopsiss = new ArrayList<>();
        this.movieRateds = new ArrayList<>();
        this.movieIMDbRatings = new ArrayList<>();
        this.movieMetascore = new ArrayList<>();
    }

    public MovieDisplayObjectClass(ArrayList<MovieSearchResultClass> movies) {

        this.movieNames = new ArrayList<>();
        this.movieYears = new ArrayList<>();
        this.movieIds = new ArrayList<>();
        this.movieImageURIs = new ArrayList<>();
        this.movieDirectors = new ArrayList<>();
        this.movieSynopsiss = new ArrayList<>();
        this.movieRateds = new ArrayList<>();
        this.movieIMDbRatings = new ArrayList<>();
        this.movieMetascore = new ArrayList<>();


        for (MovieSearchResultClass movie : movies) {
            movieNames.add(movie.movieTitle);
            movieYears.add(movie.movieYear);
            movieIds.add(movie.movieId);
            movieImageURIs.add(movie.moviePoster);
        }
    }
}
