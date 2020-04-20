package com.example.homework_1_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Task_3 extends AppCompatActivity {

    private final String TAG = Task_3.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_3);

        Log.d(TAG, "onCreate() method has been called...");
        Toast.makeText(getApplicationContext(), "onCreate() method has been called...",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart() method has been called...");
        Toast.makeText(getApplicationContext(),"onStart() method has been called...",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() method has been called...");
        Toast.makeText(getApplicationContext(),"onResume() method has been called...",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause() method has been called...");
        Toast.makeText(getApplicationContext(),"onPause() method has been called...",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop() method has been called...");
        Toast.makeText(getApplicationContext(),"onStop() method has been called...",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy() method has been called...");
        Toast.makeText(getApplicationContext(),"onDestroy() method has been called...",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG, "onRestart() method has been called...");
        Toast.makeText(getApplicationContext(),"onRestart() method has been called...",
                Toast.LENGTH_SHORT).show();
    }
}
