package com.bhancock.finalprojectapplication.ui.home;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.adapter.PackageViewAdapter;
import com.bhancock.finalprojectapplication.model.Product;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */
public class InternationalFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    PackageViewAdapter packageViewAdapter;
    private ArrayList<Product> productList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following_home_feed, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();
        recyclerView = view.findViewById(R.id.recyclerView);
        populateData();
        packageViewAdapter = new PackageViewAdapter(context, productList);
        recyclerView.setAdapter(packageViewAdapter);
    }
    private void populateData() {
        productList.add(new Product("image_1", "Android", getString(R.string.lorem), "4999", "5999", "10% off", 4.2f));
        productList.add(new Product("image_2", "Beta", getString(R.string.lorem), "6999", "8999", "15% off", 4.6f));
        productList.add(new Product("image_3", "Cupcake", getString(R.string.lorem), "4999", "5999", "10% off", 3.2f));
        productList.add(new Product("image_4", "Donut", getString(R.string.lorem), "4999", "5999", "10% off", 4.1f));
        productList.add(new Product("image_5", "Eclair", getString(R.string.lorem), "4999", "5999", "10% off", 4.8f));
        productList.add(new Product("image_6", "Froyo", getString(R.string.lorem), "9999", "10999", "10% save now", 3.6f));
        productList.add(new Product("image_1", "Gingerbread", getString(R.string.lorem), "4999", "5999", "10% off", 2.8f));
        productList.add(new Product("image_2", "Ice Cream Sandwich", getString(R.string.lorem), "4999", "5999", "10% off", 4.1f));
        productList.add(new Product("image_3", "Jelly Bean", getString(R.string.lorem), "3999", "4999", "10% off", 4.5f));
        productList.add(new Product("image_4", "Lollipop", getString(R.string.lorem), "4999", "5999", "10% off", 4.0f));
        productList.add(new Product("image_5", "Marshmallow", getString(R.string.lorem), "4999", "5999", "10% save now", 5.0f));
        productList.add(new Product("image_6", "Nougat", getString(R.string.lorem), "5999", "7999", "10% off", 4.1f));
        productList.add(new Product("image_1", "Oreo", getString(R.string.lorem), "4999", "5999", "10% off", 3.9f));
    }
}