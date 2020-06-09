package com.bhancock.finalprojectapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.model.UserPreviousLocation;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.List;

public class UserPreviousLocationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<UserPreviousLocation> mSavedTrips = new ArrayList<>();


    public UserPreviousLocationsAdapter(Context context, List<UserPreviousLocation> userPreviousLocations) {
        mContext = context;
        mSavedTrips = userPreviousLocations;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_locations, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //TODO: Implement view holder to set mapview and location text
        ((ViewHolder)holder).mLocationNameTextView.setText(mSavedTrips.get(position).getLocationName());
    }

    @Override
    public int getItemCount() {
        return mSavedTrips.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private MapView mMapView;
        private TextView mLocationNameTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mMapView = itemView.findViewById(R.id.user_visited_map_view);
            mLocationNameTextView = itemView.findViewById(R.id.location_name);
        }
    }
}
