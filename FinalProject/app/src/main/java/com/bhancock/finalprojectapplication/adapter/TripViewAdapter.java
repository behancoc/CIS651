package com.bhancock.finalprojectapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhancock.finalprojectapplication.HomeActivity;
import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.model.Trip;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class TripViewAdapter extends RecyclerView.Adapter<TripViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Trip> trips;
    private int width, height;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view;
        TextView text_title;
        TextView price;
        TextView discount;
        TextView cut_price;
        RatingBar rating;
        RelativeLayout tripDetailsRelativeLayout;
        RelativeLayout tripLikeCountRelativeLayout;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            this.image_view = (ImageView) view.findViewById(R.id.image_view);
            this.text_title = (TextView) view.findViewById(R.id.trip_location_title);
            this.price = (TextView) view.findViewById(R.id.trip_details);;
            this.tripDetailsRelativeLayout = (RelativeLayout) view.findViewById(R.id.trip_details_line_item);
            this.tripLikeCountRelativeLayout = (RelativeLayout) view.findViewById(R.id.trip_likes_line_item);
            this.itemView = view;
        }
    }
    public TripViewAdapter(Context _context, ArrayList<Trip> data) {
        this.context = _context;
        this.trips = data;
        this.width = 160;
        this.height = 100;
    }
    public TripViewAdapter(Context _context, ArrayList<Trip> data, int _width, int _height) {
        this.context = _context;
        this.trips = data;
        this.width = _width;
        this.height = _height;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_list, parent, false);
        // view.setOnClickListener(MainActivity.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try{
            Glide.with(holder.itemView)
                    .load(getImage(trips.get(position).getMapImage()))
                    .fitCenter()
                    .into(holder.image_view);
            holder.text_title.setText(trips.get(position).getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("dataSet", trips.get(position));
                    //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, holder.image_view, "robot");
                    context.startActivity(intent);
                }
            });
        }catch (Exception e){e.printStackTrace();}
    }
    private int getImage(String imageName) {
        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return drawableResourceId;
    }
    @Override
    public int getItemCount() {
        return trips.size();
    }
}