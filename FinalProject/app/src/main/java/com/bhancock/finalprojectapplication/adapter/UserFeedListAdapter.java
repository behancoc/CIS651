package com.bhancock.finalprojectapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.model.UserFeedItem;
import com.bhancock.finalprojectapplication.model.VisitedPlaces;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFeedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<UserFeedItem> mUserFeedItems;

    public UserFeedListAdapter(Context context, List<UserFeedItem> userFeedItemsList) {
        mContext = context;
        mUserFeedItems = userFeedItemsList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mUserFeedItems.size();
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
