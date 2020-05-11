package com.example.lab5application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NextActivity extends AppCompatActivity {

    public boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new ListFragment()).commit();
        }
        twoPane = false;
        if (findViewById(R.id.detail_container) != null) {
            twoPane = true;
        }
    }
}
