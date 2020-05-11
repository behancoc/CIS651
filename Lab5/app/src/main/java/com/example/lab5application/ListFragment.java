package com.example.lab5application;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ListFragment extends Fragment {
    public interface  OnItemSelectedListener {
        public void onListItemSelected(View sharedView, int imageResourceID,
                                       String title, String year);
    }

    OnItemSelectedListener clickListener;
    private MovieData movieData = new MovieData();
    private final MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(movieData.getMoviesList());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.mainRecyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                4, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        staggeredGridLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(myRecyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            clickListener = (OnItemSelectedListener) context;
            myRecyclerAdapter.setOnItemClickListener(clickListener);
        } catch (ClassCastException e) {
            e.printStackTrace();
            //throw new ClassCastException(context.toString() + " must implement EventTrack");
        }
    }
}
