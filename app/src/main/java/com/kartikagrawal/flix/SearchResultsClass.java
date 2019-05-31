package com.kartikagrawal.flix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SearchResultsClass {

    @JsonProperty("Search")
    public ArrayList<MovieSearchResultClass> movies;

    @JsonProperty("totalResults")
    public String results;

    @JsonProperty("Response")
    public String response;

}

