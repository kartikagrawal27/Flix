package com.kartikagrawal.flix;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jgabrielfreitas.core.BlurImageView;

public class MovieDetailActivity extends AppCompatActivity {


    private BlurImageView blurredPosterImageView;
    private ImageView moviePosterImageView;
    private TextView movieNameTextView;
    private TextView movieDirectorTextView;
    private TextView movieYearTextView;
    private TextView movieSynopsisTextView;

    private Intent homeActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        homeActivityIntent = getIntent();

        blurredPosterImageView = findViewById(R.id.blurred_poster_image_view);
        moviePosterImageView = findViewById(R.id.movie_poster_image_view);
        movieNameTextView = findViewById(R.id.movie_name_text_view);
        movieDirectorTextView = findViewById(R.id.movie_director_text_view);
        movieYearTextView = findViewById(R.id.movie_year_text_view);
        movieSynopsisTextView = findViewById(R.id.movie_synopsis_text_view);

//        Glide.with(this)
//                .load(intent.getStringExtra("movie_poster"))
//                .centerCrop()
//                .into(blurredPosterImageView);

//        blurredPosterImageView.setBlur(15);

        Glide.with(this)
                .load(homeActivityIntent.getStringExtra("movie_poster"))
                .centerCrop()
                .into(moviePosterImageView);

        movieNameTextView.setText(homeActivityIntent.getStringExtra("movie_name"));
        movieDirectorTextView.setText(homeActivityIntent.getStringExtra("movie_director"));
        movieYearTextView.setText(homeActivityIntent.getStringExtra("movie_year"));
        movieSynopsisTextView.setText(homeActivityIntent.getStringExtra("movie_synopsis"));







    }
}
