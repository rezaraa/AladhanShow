package com.rezara.aladhanshow.movie.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rezara.aladhanshow.R;
import com.rezara.aladhanshow.pojo.SearchResult;

import java.util.ArrayList;
import java.util.List;
public class MovieSearchItemAdapter extends RecyclerView.Adapter<MovieSearchItemViewHolder> {
    List<SearchResult.ResultsBean> resultItems = new ArrayList<>();
    AdapterItemClicked itemCLicked = null;
    @NonNull
    @Override
    public MovieSearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_search_item, parent, false);
        return new MovieSearchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSearchItemViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2"
                +resultItems.get(position).getPoster_path())
                .into(holder.movieImage).clearOnDetach();
        holder.txtMovieTitle.setText(resultItems.get(position).getTitle());
        holder.txtMovieRate.setText(String.valueOf(resultItems.get(position).getVote_average()));
        holder.txtMovieDesc.setText(resultItems.get(position).getOverview());
        holder.cardMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCLicked.itemClicked(resultItems.get(position).getId());
            }
        });
    }
    public void setAdapterItemClicked(AdapterItemClicked itemClicked){
        this.itemCLicked =itemClicked;
    }
    public void setResultItems(List<SearchResult.ResultsBean> resultItems) {
        this.resultItems = resultItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (resultItems == null)
            return 0;
        else
            return resultItems.size();
    }
   public  interface AdapterItemClicked{
        public void itemClicked(int Id);
    }
}
