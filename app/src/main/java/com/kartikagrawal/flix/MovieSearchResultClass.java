package com.kartikagrawal.flix;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieSearchResultClass {
    @JsonProperty("Title")
    public String movieTitle;

    @JsonProperty("Year")
    public String movieYear;

    @JsonProperty("imdbID")
    public String movieId;

    @JsonProperty("Type")
    public String movieType;

    @JsonProperty("Poster")
    public String moviePoster;
}
