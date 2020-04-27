package com.example.lab3application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentTracker {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;

    private GestureDetectorCompat mDetector;
    private final PersonalInfo personInfo = new PersonalInfo();
    private int next = 1;

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 3;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.title);


        mDetector = new GestureDetectorCompat(getApplicationContext(), new MyGestureListener());

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        loadFragment(fragment1);

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    @Override
    public void fragmentVisible(String s) {
    }

    @Override
    public void goNext() {
        next ++;
        Log.d(TAG, "The current value of next is " + next);


        switch(next) {
            case 1:
                loadFragment(fragment1);
                if (fragment1.isVisible()) {
                    textView.setText(Fragment1.fragmentTitle);
                }
                break;
            case 2:
                loadFragment(fragment2);
                textView.setText(Fragment2.fragmentTitle);
                break;
            case 3:
                loadFragment(fragment3);
                textView.setText(Fragment3.fragmentTitle);
                break;
        }
    }

    @Override
    public void goBack() {
        next --;

        switch (next) {
            case 1:
                loadFragment(fragment1);
                textView.setText(Fragment1.fragmentTitle);
                break;
            case 2:
                loadFragment(fragment2);
                textView.setText(Fragment2.fragmentTitle);
                break;
            case 3:
                loadFragment(fragment3);
                textView.setText(Fragment3.fragmentTitle);
                break;
        }
    }

    @Override
    public void saveNameAndLastName(String firstName, String lastName) {
        personInfo.setFirstName(firstName);
        personInfo.setLastName(lastName);
    }

    @Override
    public void saveCityAndZip(String city, String zip) {
        personInfo.setCity(city);
        personInfo.setZip(zip);
    }


    @Override
    public void saveLanguage(String language) {
        Log.d(TAG, "saveLanguage method called!");
        personInfo.setLanguage(language);

        Log.d(TAG, "The language is: " + personInfo.getLanguage());
    }

    @Override
    public void finished() {
        Log.d(TAG, "Finished() called!");
        Intent intent = new Intent(this, SummaryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("pi", personInfo);
        startActivity(intent);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling called");
            if(e1.getX() < e2.getX()) {
                if (next > MIN_VALUE) {
                    goBack();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Swipe from the other side to proceed", Toast.LENGTH_SHORT);
                    TextView view = (TextView) toast.getView().findViewById(android.R.id.message);
                    try {
                        view.setGravity(Gravity.CENTER);
                        toast.show();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                Log.d(TAG, "Surface F!ing left!");
                Log.d(TAG, "The current value of next is: " + next);


                if (next == 3) {
                    Toast.makeText(getApplicationContext(),
                            "Please click the FINISHED button",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (next <= MAX_VALUE) {
                        goNext();
                    }
                }
            }
            return true;
        }
    }
}
