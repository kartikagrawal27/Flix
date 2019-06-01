package com.kartikagrawal.flix;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class MovieRecycleViewAdapter extends RecyclerView.Adapter<MovieRecycleViewAdapter.ViewHolder>{


    private Context context;
    private ArrayList<String> posterURIs = new ArrayList<>();
    private OnGridListener onGridListener;
//    private ArrayList<String> movieNames = new ArrayList<>();
//    private ArrayList<String> movieDirectors = new ArrayList<>();
//    private ArrayList<String> movieYears = new ArrayList<>();
//    private ArrayList<String> movieSynopsiss = new ArrayList<>();
    MovieDisplayObjectClass movieDisplayObjectClass;

    public MovieRecycleViewAdapter(Context context, ArrayList<String> posterURIs, ArrayList<String> movieNames, ArrayList<String> movieDirectors, ArrayList<String> movieYears, ArrayList<String> movieSynopsiss) {
        this.context = context;
        this.posterURIs = posterURIs;
//        this.movieNames = movieNames;
//        this.movieDirectors = movieDirectors;
//        this.movieYears = movieYears;
//        this.movieSynopsiss = movieSynopsiss;
    }

    //Tester adapter
    public MovieRecycleViewAdapter(Context context, ArrayList<String> posterURIs, OnGridListener onGridListener) {
        this.context = context;
        this.posterURIs = posterURIs;
        this.onGridListener = onGridListener;
//        this.movieNames = movieNames;
//        this.movieDirectors = movieDirectors;
//        this.movieYears = movieYears;
//        this.movieSynopsiss = movieSynopsiss;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_item, viewGroup, false);

        int width = viewGroup.getMeasuredWidth()/3;
        view.setMinimumWidth(width);

        return new ViewHolder(view, onGridListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(750);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                viewHolder.moviePoster.setAlpha(alpha);
            }
        });

        Glide.with(context)
                .load(posterURIs.get(i))
                .apply(requestOptions)
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        valueAnimator.start();
                        return false;
                    }
                })
                .into(viewHolder.moviePoster);

//        viewHolder.movieName.setText(movieNames.get(i));
//        viewHolder.movieDirector.setText(movieDirectors.get(i));
////        viewHolder.movieYear.setText(movieYears.get(i));
//        viewHolder.movieSynopsis.setText(movieSynopsiss.get(i));
    }

    @Override
    public int getItemCount() {
        return this.posterURIs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView moviePoster;
        private OnGridListener onGridListener;
//        private TextView movieName;
//        private TextView movieDirector;
//        private TextView movieYear;
//        private TextView movieSynopsis;

        public ViewHolder(@NonNull View itemView, OnGridListener onGridListener) {
            super(itemView);

            this.moviePoster = itemView.findViewById(R.id.movie_poster);
            this.onGridListener = onGridListener;

            itemView.setOnClickListener(this);
//            this.movieName = itemView.findViewById(R.id.movie_name);;
//            this.movieDirector = itemView.findViewById(R.id.movie_director);;
////            this.movieYear = itemView.findViewById(R.id.movie_year);
//            this.movieSynopsis = itemView.findViewById(R.id.movie_synopsis);
        }

        @Override
        public void onClick(View v) {
            onGridListener.onGridClick(getAdapterPosition());
        }
    }

    public interface OnGridListener{
        void onGridClick(int position);
    }

}
