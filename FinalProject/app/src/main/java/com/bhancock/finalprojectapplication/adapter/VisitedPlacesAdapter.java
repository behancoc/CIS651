package com.bhancock.finalprojectapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.model.VisitedPlaces;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VisitedPlacesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<VisitedPlaces> mVisitedPlaces = new ArrayList<>();


    public VisitedPlacesAdapter(Context context, List<VisitedPlaces> visitedPlaces) {
        mContext = context;
        mVisitedPlaces = visitedPlaces;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_list_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Set the name of the 'NicePlace'
        ((ViewHolder)holder).mName.setText(mVisitedPlaces.get(position).getTitle());

        // Set the image
        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(mVisitedPlaces.get(position).getImageUrl())
                .into(((ViewHolder)holder).mImage);
    }

    @Override
    public int getItemCount() {
        return mVisitedPlaces.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImage;
        private TextView mName;

        public ViewHolder(View view) {
            super(view);
            mImage = view.findViewById(R.id.image);
            mName = view.findViewById(R.id.location_name);
        }
    }



}
