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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class MovieRecycleViewAdapter extends RecyclerView.Adapter<MovieRecycleViewAdapter.ViewHolder>{


    private Context context;
    private ArrayList<String> posterURIs = new ArrayList<>();
    private OnGridListener onGridListener;
    private FirebaseFirestore db;
    private DocumentReference dRef;
    private FirebaseUser user;
    private String userID;
    private ArrayList<String> favorites;
    private ArrayList<String> movieIds;


    MovieDisplayObjectClass movieDisplayObjectClass;

    //Tester adapter
    public MovieRecycleViewAdapter(Context context, ArrayList<String> posterURIs, ArrayList<String> movieIds, OnGridListener onGridListener) {
        this.context = context;
        this.posterURIs = posterURIs;
        this.movieIds = movieIds;
        this.onGridListener = onGridListener;
        this.favorites = new ArrayList<>();

        this.db = FirebaseFirestore.getInstance();

        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.userID = user.getUid();
        dRef = FirebaseFirestore.getInstance().document("users/" + this.userID);

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

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
//                        valueAnimator.start();
                        return false;
                    }
                })
                .into(viewHolder.moviePoster);


        dRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    favorites = (ArrayList<String>)  documentSnapshot.get("favorites");
                }
                if(favorites.contains(movieIds.get(i))){
                    viewHolder.isFavorited = true;
                }
                else{
                    viewHolder.isFavorited = false;
                }

                //Set initial value for ImageButton
                if(viewHolder.isFavorited==true){
                    viewHolder.fav_star.setImageResource(R.drawable.ic__favorited_star);
                }
                else {
                    viewHolder.fav_star.setImageResource(R.drawable.ic__unfavorited_star);
                }
            }
        });

        viewHolder.fav_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.isFavorited){
                    viewHolder.isFavorited = false;
//                    Toast.makeText(context, "Remove from favorites", Toast.LENGTH_SHORT).show();
                    viewHolder.fav_star.setImageResource(R.drawable.ic__unfavorited_star);

                    favorites.remove(movieIds.get(i));
                    HashMap<String, ArrayList<String>>dataToPut = new HashMap<>();
                    dataToPut.put("favorites", favorites);

                    dRef.set(dataToPut).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{

                    viewHolder.isFavorited = true;
//                    Toast.makeText(context, "Add to favorites", Toast.LENGTH_SHORT).show();
                    viewHolder.fav_star.setImageResource(R.drawable.ic__favorited_star);

                    favorites.add(movieIds.get(i));
                    HashMap<String, ArrayList<String>>dataToPut = new HashMap<>();
                    dataToPut.put("favorites", favorites);

                    dRef.set(dataToPut).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.posterURIs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView moviePoster;
        private OnGridListener onGridListener;
        private ImageButton fav_star;

        private boolean isFavorited;
        private String unfavoritedResource = "@drawable/ic_unfavorited_star";
        private String favoritedResource = "@drawable/ic_favorited_star";

        public ViewHolder(@NonNull View itemView, OnGridListener onGridListener) {
            super(itemView);

            this.moviePoster = itemView.findViewById(R.id.movie_poster);
            this.onGridListener = onGridListener;
            this.fav_star = itemView.findViewById(R.id.star_button);

            itemView.setOnClickListener(this);
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
