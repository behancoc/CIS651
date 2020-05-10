package com.example.lab5application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.os.Bundle;
import android.view.View;

public class MasterDetailFlowActivity extends AppCompatActivity implements
        com.example.lab5application.ListFragment.onItemSelectedListener {

    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail_flow);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new ListFragment())
                    .commit();
        }
        twoPane = false;
        if(findViewById(R.id.detail_container)!= null) {
            twoPane = true;
        }

    }

    @Override
    public void OnListItemSelected(View sharedView, int imageResourceID, String title, String year) {

    }
}
