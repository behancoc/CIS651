package com.bhancock.finalprojectapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.model.Trip;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class TripViewAdapter extends FirestoreRecyclerAdapter<Trip, TripViewAdapter.TripViewHolder> {
    private Context mContext;
    private ArrayList<Trip> trips;

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTripLocationTitle;
        TextView textViewTripDetails;
        TextView textViewTripLikeCount;
        RelativeLayout tripDetailsRelativeLayout;
        RelativeLayout tripLikeCountRelativeLayout;
        View itemView;

        public TripViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.image_view);
            this.textViewTripLocationTitle = (TextView) view.findViewById(R.id.trip_location_title);
            this.textViewTripDetails = (TextView) view.findViewById(R.id.trip_details);;
            this.textViewTripLikeCount = (TextView) view.findViewById(R.id.like_count);
            this.tripDetailsRelativeLayout = (RelativeLayout) view.findViewById(R.id.trip_details_line_item);
            this.tripLikeCountRelativeLayout = (RelativeLayout) view.findViewById(R.id.trip_likes_line_item);
            this.itemView = view;
        }
    }

    public TripViewAdapter(@NonNull FirestoreRecyclerOptions<Trip> options) {
        super(options);
    }

    public TripViewAdapter(@NonNull FirestoreRecyclerOptions<Trip> options, Context context) {
        super(options);
    }


    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_list, parent, false);

        TripViewHolder tripViewHolder = new TripViewHolder(view);
        return tripViewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull TripViewHolder holder, final int position, @NonNull Trip model) {
//        try{
//            Glide.with(holder.itemView)
//                    .load(getImage(trips.get(position).getMapImage()))
//                    .fitCenter()
//                    .into(holder.image_view);
//            holder.textViewTripLocationTitle.setText(trips.get(position).getTitle());
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, HomeActivity.class);
//                    intent.putExtra("dataSet", trips.get(position));
//                    mContext.startActivity(intent);
//                }
//            });
//        } catch (Exception e){e.printStackTrace();}

        holder.textViewTripLocationTitle.setText(model.getTripTitle());
        holder.textViewTripDetails.setText(model.getTripDetails());
        holder.textViewTripLikeCount.setText(String.valueOf(model.getTripLikes()));
    }

    private int getImage(String imageName) {
        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());
        return drawableResourceId;
    }
}