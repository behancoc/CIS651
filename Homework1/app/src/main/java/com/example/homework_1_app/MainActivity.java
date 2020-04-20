package com.example.homework_1_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();


    private BottomNavigationView bottomNavigationView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        setState();

        Log.d(TAG, "First item is checked: " + bottomNavigationView.getMenu().getItem(0).isChecked());
        Log.d(TAG, "Second item is checked: " + bottomNavigationView.getMenu().getItem(1).isChecked());
        Log.d(TAG, "Third item is checked: " + bottomNavigationView.getMenu().getItem(2).isChecked());
        Log.d(TAG, "Fourth item is checked: " + bottomNavigationView.getMenu().getItem(3).isChecked());
        Log.d(TAG, "Fifth item is checked: " + bottomNavigationView.getMenu().getItem(4).isChecked());

        imageView = findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.brandon_hancock_image);

        Animation animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(1000);
        animation.setStartOffset(50);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Task_0: {
                Log.d(TAG, "is enabled? " + item.isEnabled());
                Toast.makeText(getApplicationContext(),"Launching Task 0",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Task_0.class);
                startActivity(intent);
            }
            break;
            case R.id.Task_1: {

                Toast.makeText(getApplicationContext(),"Launching Task 1",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Task_1.class);
                startActivity(intent);
            }
            break;
            case R.id.Task_2: {
                Toast.makeText(getApplicationContext(),"Launching Task 2",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Task_2.class);
                startActivity(intent);
            }
            break;
            case R.id.Task_3: {
                Toast.makeText(getApplicationContext(),"Launching Task 3",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Task_3.class);
                startActivity(intent);
            }
            break;
            case R.id.Task_4: {
                Toast.makeText(getApplicationContext(),"Launching Task 4",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Task_4.class);
                startActivity(intent);
            }
            break;
        }
        return true;
    }

    private void setState() {
        for(int i = 0; i< bottomNavigationView.getMenu().size(); i++) {
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
            Log.d(TAG, "setChecked to false for: " + bottomNavigationView.getMenu().getItem(i));
        }
    }
}
