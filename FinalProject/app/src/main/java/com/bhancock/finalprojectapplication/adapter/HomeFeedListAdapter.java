package com.bhancock.finalprojectapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.model.Channel;
import com.bhancock.finalprojectapplication.model.Video;

import java.util.List;

public class HomeFeedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Channel.HomeFeedItem homeFeedItem;
    private List<Video> videoList;


    public HomeFeedListAdapter(Context context, List<Video> videoList) {
        mContext = context;
        this.videoList = videoList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_feed_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).mImageView.setImageResource(R.mipmap.ic_launcher);
        ((ViewHolder) holder).mVideoTitle.setText("home");
    }

    @Override
    public int getItemCount() {
        return videoList.size();
//        return homeFeedItem.getVideos().size();   
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mVideoTitle;

        public ViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.home_feed_image_view);
            mVideoTitle = view.findViewById(R.id.home_feed_video_title);
        }
    }
}
