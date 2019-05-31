package com.kartikagrawal.flix;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class OpenMovieAPIClass {

    //Constants
    private static final String SCHEME = "http";
    private static final String AUTHORITY = "www.omdbapi.com";
    private static final String API_PARAMETER = "apikey";
    private static final String API_KEY = "9c966c4b";
    private static final String PAGE_PARAMETER = "page";
    private static final String PAGE_VALUE = "2";
    private static final String SEARCH_PARAMETER = "s";
    private static final String ID_PARAMETER = "i";
    private static final String PLOT_PARAMETER = "plot";

    private Context context;
    private static RequestQueue requestQueue;

    private int number_of_requests_to_make = 0;

    public OpenMovieAPIClass(Context context) {
        //Empty constructor
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(this.context);
    }

    protected static void searchMoviesForRecyclerView(String searchTerm, final Context context, final VolleySearchCallbackInterface callback) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendQueryParameter(API_PARAMETER, API_KEY)
                .appendQueryParameter(SEARCH_PARAMETER, searchTerm)
                .appendQueryParameter(PAGE_PARAMETER, PAGE_VALUE);

        String url = builder.build().toString();

        StringRequest sr = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<MovieSearchResultClass> movieResults = getMovieListFromJson(response);
                        callback.onSuccessResponse(movieResults);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                });

        requestQueue.add(sr);
    }

    private static ArrayList<MovieSearchResultClass> getMovieListFromJson(String response) {
        ObjectMapper mapper = new ObjectMapper();
        SearchResultsClass searchResults = null;
        try {
            searchResults = (SearchResultsClass) mapper.readValue(response, SearchResultsClass.class);
        } catch (IOException e) {
            Log.e("jacksonClient", "error: " + e.toString());
        }

        if(searchResults != null){
            return searchResults.movies;
        }
        else{
            return new ArrayList<>();
        }
    }

    protected static void searchById(String id, String plotLength, final VolleyExtrasCallbackInterface callback){

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendQueryParameter(API_PARAMETER, API_KEY)
                .appendQueryParameter(ID_PARAMETER, id)
                .appendQueryParameter(PLOT_PARAMETER, plotLength);

        String url = builder.build().toString();
        StringRequest sr = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        HashMap<String, String> details = getMovieDetails(response);
                        callback.onSuccessResponse(details);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                });

        requestQueue.add(sr);
    }

    private static HashMap<String, String> getMovieDetails(String response) {
        HashMap<String, String> details = new HashMap<String, String>();

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String,String> map = null;
        try {
           map  = mapper.readValue(response, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        details.put("director",  map.get("Director"));
        details.put("plot", map.get("Plot"));
        return details;
    }

}
