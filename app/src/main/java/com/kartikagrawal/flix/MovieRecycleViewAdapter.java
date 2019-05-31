package com.kartikagrawal.flix;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieRecycleViewAdapter extends RecyclerView.Adapter<MovieRecycleViewAdapter.ViewHolder>{


    private Context context;
    private ArrayList<String> posterURIs = new ArrayList<>();
    private ArrayList<String> movieNames = new ArrayList<>();
    private ArrayList<String> movieDirectors = new ArrayList<>();
    private ArrayList<String> movieYears = new ArrayList<>();
    private ArrayList<String> movieSynopsiss = new ArrayList<>();

    public MovieRecycleViewAdapter(Context context, ArrayList<String> posterURIs, ArrayList<String> movieNames, ArrayList<String> movieDirectors, ArrayList<String> movieYears, ArrayList<String> movieSynopsiss) {
        this.context = context;
        this.posterURIs = posterURIs;
        this.movieNames = movieNames;
        this.movieDirectors = movieDirectors;
        this.movieYears = movieYears;
        this.movieSynopsiss = movieSynopsiss;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.grid_cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(context)
                .load(posterURIs.get(i))
                .apply(requestOptions)
                .into(viewHolder.moviePoster);

        viewHolder.movieName.setText(movieNames.get(i));
        viewHolder.movieDirector.setText(movieDirectors.get(i));
        viewHolder.movieYear.setText(movieYears.get(i));
        viewHolder.movieSynopsis.setText(movieSynopsiss.get(i));
    }

    @Override
    public int getItemCount() {
        return posterURIs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView moviePoster;
        private TextView movieName;
        private TextView movieDirector;
        private TextView movieYear;
        private TextView movieSynopsis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.moviePoster = itemView.findViewById(R.id.movie_poster);
            this.movieName = itemView.findViewById(R.id.movie_name);;
            this.movieDirector = itemView.findViewById(R.id.movie_director);;
            this.movieYear = itemView.findViewById(R.id.movie_year);;
            this.movieSynopsis = itemView.findViewById(R.id.movie_synopsis);;
        }
    }
}
