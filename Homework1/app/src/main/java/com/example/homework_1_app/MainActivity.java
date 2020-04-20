package com.example.homework_1_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView bottomNavigationView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        imageView = findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.brandon_hancock_image);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Task_0: {
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
}
