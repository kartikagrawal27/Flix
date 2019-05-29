package com.kartikagrawal.flix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsClass {

    @JsonProperty("Search")
    public ArrayList<MovieResultsClass> movies;

    @JsonProperty("totalResults")
    public String results;

    @JsonProperty("Response")
    public String response;

}

