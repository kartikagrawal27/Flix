package com.kartikagrawal.flix;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    private EditText search_entry_edit_text;
    private ImageButton search_button;
    private static final int NUM_COLUMNS = 2;

    private ArrayList<String> moviePosterURIs = new ArrayList<>();
    private ArrayList<String> movieNames = new ArrayList<>();
    private ArrayList<String> movieDirectors = new ArrayList<>();
    private ArrayList<String> movieYears = new ArrayList<>();
    private ArrayList<String> movieSynopsiss = new ArrayList<>();


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

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGrid();
            }
        });

        return view;
    }

    private void initializeGrid() {

        /* TO-DO
        Create a API class for Open Movie DB and use that here to query data
        */

        initRecyclerView();
    }

    private void initRecyclerView() {
        View  view = getView();
        RecyclerView recyclerView = view.findViewById(R.id.movie_grid_recycler_view);
        MovieRecycleViewAdapter movieRecycleViewAdapter = new MovieRecycleViewAdapter(getContext(), moviePosterURIs, movieNames, movieDirectors, movieYears, movieSynopsiss);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUM_COLUMNS));
    }


}
