package com.example.homework_1_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView bottomNavigationView;
    private int buttonNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        buttonNumber = item.getItemId();

        switch (item.getItemId()) {
            case R.id.Button_1: {
                //TODO: Implement Intent to fire Activity 1
                Toast.makeText(getApplicationContext(),"Launching Task 0",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Task_0.class);
                startActivity(intent);
            }
            break;
            case R.id.Button_2: {
                //TODO: Implement Intent to fire Activity 2
                Toast.makeText(getApplicationContext(),"Launching Task 1",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Task_1.class);
                startActivity(intent);
            }
            break;
            case R.id.Button_3: {
                //TODO: Implement Intent to fire Activity 3
                Toast.makeText(getApplicationContext(),"Launching Task 2",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Task_2.class);
                startActivity(intent);
            }
            break;
            case R.id.Button_4: {
                //TODO: Implement Intent to fire Activity 4
                Toast.makeText(getApplicationContext(),"Launching Task 3",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Task_3.class);
                startActivity(intent);
            }
            break;
            case R.id.Button_5: {
                //TODO: Implement Intent to fire Activity 5
                Toast.makeText(getApplicationContext(),"Launching Task 4",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Task_4.class);
                startActivity(intent);
            }
            break;
        }
        return true;
    }
}
