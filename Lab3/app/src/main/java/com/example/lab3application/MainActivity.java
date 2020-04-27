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
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentTracker {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;

    private GestureDetectorCompat mDetector;
    private final PersonalInfo personInfo = new PersonalInfo();
    private int next = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        this.mDetector.onTouchEvent(ev);
//        return super.dispatchTouchEvent(ev);
//    }

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
                break;
            case 2:
                loadFragment(fragment2);
                break;
            case 3:
                loadFragment(fragment3);
                break;
        }
    }

    @Override
    public void goBack() {
        next --;

        switch (next) {
            case 1:
                loadFragment(fragment1);
                break;
            case 2:
                loadFragment(fragment2);
                break;
            case 3:
                loadFragment(fragment3);
                break;
        }
    }

    @Override
    public void saveNameAndLastName(String firstName, String lastName) {
        Log.d(TAG, "Your name is: " + firstName + " " + lastName);
    }

    @Override
    public void saveCityAndZip(String city, String zip) {
        Log.d(TAG, "You live in " + city + " " + zip);
    }

    @Override
    public void saveLanguage(String language) {
        Log.d(TAG, "You speak: " + language);

    }

    @Override
    public void finished() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("personal info", personInfo);
        startActivity(intent);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() < e2.getX()) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Fling right", Toast.LENGTH_LONG);
                toast.show();
                //nextFragment(1);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Fling Left", Toast.LENGTH_LONG);
                toast.show();
                //nextFragment(-1);
            }

            return true;
        }
    }
}
