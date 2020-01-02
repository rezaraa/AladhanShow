package com.rezara.aladhanshow.movie.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rezara.aladhanshow.R;

public class MovieSearchItemViewHolder extends RecyclerView.ViewHolder {
    TextView txtMovieTitle,txtMovieRate,txtMovieDesc;
    ImageView movieImage;
    CardView cardMovie;
    public MovieSearchItemViewHolder(@NonNull View itemView) {
        super(itemView);
        movieImage = itemView.findViewById(R.id.movieImage);
        cardMovie = itemView.findViewById(R.id.cardMovie);
        txtMovieTitle = itemView.findViewById(R.id.txtMovieTitle);
        txtMovieRate = itemView.findViewById(R.id.txtMovieRate);
        txtMovieDesc = itemView.findViewById(R.id.txtMovieDesc);
    }
}
