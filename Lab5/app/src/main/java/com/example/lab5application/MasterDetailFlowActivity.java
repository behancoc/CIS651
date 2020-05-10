package com.example.lab5application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.os.Bundle;

public class MasterDetailFlowActivity extends AppCompatActivity {

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
}
