package com.kartikagrawal.flix;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
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

    private String IMAGE_NOT_FOUND_URL;

    private RequestOptions requestOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        this.homeActivityIntent = getIntent();

        this.requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        this.IMAGE_NOT_FOUND_URL = "https://s3-ap-southeast-1.amazonaws.com/silverscreen-photos/1534489151m000001.jpg";

        String imageToLoadURL = homeActivityIntent.getStringExtra("movie_poster");

        blurredPosterImageView = findViewById(R.id.blurred_poster_image_view);
        moviePosterImageView = findViewById(R.id.movie_poster_image_view);
        movieNameTextView = findViewById(R.id.movie_name_text_view);
        movieDirectorTextView = findViewById(R.id.movie_director_text_view);
        movieYearTextView = findViewById(R.id.movie_year_text_view);
        movieSynopsisTextView = findViewById(R.id.movie_synopsis_text_view);


        if(!imageToLoadURL.equals("N/A")){
            if(imageToLoadURL.substring(0, 5).equals("https")) {
                Glide.with(getApplication())
                        .load(imageToLoadURL)
                        .apply(requestOptions)
                        .centerCrop()
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        viewHolder.moviePoster.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_background));
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        valueAnimator.start();
                                return false;
                            }
                        })
                        .into(moviePosterImageView);
            }
            else{
                updateToDefault();
            }
        }
        else{
            updateToDefault();
        }

        movieNameTextView.setText(homeActivityIntent.getStringExtra("movie_name"));
        movieDirectorTextView.setText(homeActivityIntent.getStringExtra("movie_director"));
        movieYearTextView.setText(homeActivityIntent.getStringExtra("movie_year"));
        movieSynopsisTextView.setText(homeActivityIntent.getStringExtra("movie_synopsis"));

        movieSynopsisTextView.setMovementMethod(new ScrollingMovementMethod());

    }

    private void updateToDefault() {


        Glide.with(getApplication())
                .load(IMAGE_NOT_FOUND_URL)
                .apply(requestOptions)
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        viewHolder.moviePoster.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_background));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        valueAnimator.start();
                        return false;
                    }
                })
                .into(moviePosterImageView);
    }
}
