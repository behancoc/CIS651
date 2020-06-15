package com.bhancock.finalprojectapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhancock.finalprojectapplication.HomeActivity;
import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.model.Trip;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class TripViewAdapter extends RecyclerView.Adapter<TripViewAdapter.TripViewHolder> {
    private Context mContext;
    private ArrayList<Trip> trips;

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view;
        TextView tripLocationTitle;
        TextView tripDetails;
        RelativeLayout tripDetailsRelativeLayout;
        RelativeLayout tripLikeCountRelativeLayout;
        View itemView;

        public TripViewHolder(View view) {
            super(view);
            this.image_view = (ImageView) view.findViewById(R.id.image_view);
            this.tripLocationTitle = (TextView) view.findViewById(R.id.trip_location_title);
            this.tripDetails = (TextView) view.findViewById(R.id.trip_details);;
            this.tripDetailsRelativeLayout = (RelativeLayout) view.findViewById(R.id.trip_details_line_item);
            this.tripLikeCountRelativeLayout = (RelativeLayout) view.findViewById(R.id.trip_likes_line_item);
            this.itemView = view;
        }
    }
    public TripViewAdapter(Context context, ArrayList<Trip> listOfTrips) {
        this.mContext = context;
        this.trips = listOfTrips;
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_list, parent, false);

        TripViewHolder tripViewHolder = new TripViewHolder(view);
        return tripViewHolder;
    }
    @Override
    public void onBindViewHolder(final TripViewHolder holder, final int position) {
        try{
            Glide.with(holder.itemView)
                    .load(getImage(trips.get(position).getMapImage()))
                    .fitCenter()
                    .into(holder.image_view);
            holder.tripLocationTitle.setText(trips.get(position).getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.putExtra("dataSet", trips.get(position));
                    mContext.startActivity(intent);
                }
            });
        }catch (Exception e){e.printStackTrace();}
    }
    private int getImage(String imageName) {
        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());
        return drawableResourceId;
    }
    @Override
    public int getItemCount() {
        return trips.size();
    }
}