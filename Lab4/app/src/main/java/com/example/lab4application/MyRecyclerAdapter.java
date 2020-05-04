package com.example.lab4application;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>
        implements Filterable {

    private List<Map<String, ?>> movieData;
    private List<Map<String, ?>>filteredMovieData;
    private OnListItemClickListener onListItemClickListener = null;

    public MyRecyclerAdapter(List<Map<String, ?>> list) {
        movieData = filteredMovieData = list;
    }

    public interface OnListItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredMovieData = movieData;
                } else {
                    List<Map<String,?>> filteredList = new ArrayList<>();
                    for(Map movie: movieData) {
                        if (movie.get("name").toString().
                                toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                    filteredMovieData = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMovieData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredMovieData = (ArrayList<Map<String,?>>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(onListItemClickListener !=null) {
                   onListItemClickListener.onItemClick(view, viewHolder.getAdapterPosition());
               }
           }
       });

       view.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               if(onListItemClickListener != null) {
                   onListItemClickListener.onItemLongClick(view, viewHolder.getAdapterPosition());
                   return true;
               } else { return false; }
           }
       });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, int position) {
        holder.movieName.setText(filteredMovieData.get(position).get("name").toString());
        holder.movieYear.setText(filteredMovieData.get(position).get("year").toString());
        holder.posterImage.setImageResource(Integer.parseInt(filteredMovieData.get(position).get("image").toString()));
    }

    @Override
    public int getItemCount() {
        return filteredMovieData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieName;
        public TextView movieYear;
        public ImageView posterImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = (TextView) itemView.findViewById(R.id.movie_name);
            movieYear = (TextView) itemView.findViewById(R.id.movie_year);
            posterImage = (ImageView) itemView.findViewById(R.id.poster_photo);
        }
    }
}
