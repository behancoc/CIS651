package com.example.homework2app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework2app.ui.masterdetail.MasterDetailFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>
        implements Filterable {

    private List<Map<String, ?>> movieData;
    private List<Map<String, ?>>filteredMovieData;
    private MasterDetailFragment.OnItemSelectedListener clickListener = null;

    private static final String TAG = MyRecyclerAdapter.class.getSimpleName();

    public MyRecyclerAdapter(List<Map<String, ?>> list) {
        movieData = filteredMovieData = list;
    }

    public void setOnItemClickListener(MasterDetailFragment.OnItemSelectedListener listener) {
        clickListener = listener;
    }

    public interface OnListItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieName;
        public TextView movieYear;
        public ImageView posterImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_name);
            movieYear = itemView.findViewById(R.id.movie_year);
            posterImage = itemView.findViewById(R.id.poster_photo);
        }
    }

    public Map getItem(int i) {
        return filteredMovieData.get(i);
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
                .inflate(R.layout.recycler_view_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, final int position) {
        holder.movieName.setText(filteredMovieData.get(position).get("name").toString());
        holder.movieYear.setText(filteredMovieData.get(position).get("year").toString());
        holder.posterImage.setImageResource(Integer.parseInt(filteredMovieData.get(position).get("image").toString()));
        ViewCompat.setTransitionName(holder.posterImage, filteredMovieData.get(position).get("name").toString());
        holder.posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick callback!!!!!!!!!!!!!");
                Log.d(TAG, clickListener.toString());
                if(clickListener != null) {
                    clickListener.onListItemSelected(view,
                            Integer.parseInt(filteredMovieData.get(position).get("image").toString()),
                            filteredMovieData.get(position).get("name").toString(),
                            filteredMovieData.get(position).get("year").toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredMovieData.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
