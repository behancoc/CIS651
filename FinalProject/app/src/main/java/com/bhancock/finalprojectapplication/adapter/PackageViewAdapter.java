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
import com.bhancock.finalprojectapplication.model.Product;
import com.bumptech.glide.Glide;



import java.util.ArrayList;
public class PackageViewAdapter extends RecyclerView.Adapter<PackageViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Product> dataModel;
    private int width, height;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view;
        TextView text_title;
        TextView price;
        TextView discount;
        TextView cut_price;
        RatingBar rating;
        RelativeLayout price_bar;
        RelativeLayout cut_rate_bar;
        View itemView;
        public MyViewHolder(View view) {
            super(view);
            this.image_view = (ImageView) view.findViewById(R.id.image_view);
            this.text_title = (TextView) view.findViewById(R.id.title);
            this.price = (TextView) view.findViewById(R.id.price);
            this.discount = (TextView) view.findViewById(R.id.discount);
            this.cut_price = (TextView) view.findViewById(R.id.cut_price);
            this.rating = (RatingBar) view.findViewById(R.id.rating);
            this.price_bar = (RelativeLayout) view.findViewById(R.id.price_bar);
            this.cut_rate_bar = (RelativeLayout) view.findViewById(R.id.cut_rate_bar);
            this.itemView = view;
        }
    }
    public PackageViewAdapter(Context _context, ArrayList<Product> data) {
        this.context = _context;
        this.dataModel = data;
        this.width = 160;
        this.height = 100;
    }
    public PackageViewAdapter(Context _context, ArrayList<Product> data, int _width, int _height) {
        this.context = _context;
        this.dataModel = data;
        this.width = _width;
        this.height = _height;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.package_list, parent, false);
        // view.setOnClickListener(MainActivity.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try{
            Glide.with(holder.itemView)
                    .load(getImage(dataModel.get(position).getImage()))
                    .fitCenter()
                    .into(holder.image_view);
            holder.text_title.setText(dataModel.get(position).getTitle());
            holder.price.setText(context.getString(R.string.price, dataModel.get(position).getPrice()));
            holder.cut_price.setText(context.getString(R.string.price, dataModel.get(position).getCut_price()));
            holder.discount.setText(dataModel.get(position).getDiscount());
            holder.rating.setRating(dataModel.get(position).getRating());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("dataSet", dataModel.get(position));
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
        return dataModel.size();
    }
}