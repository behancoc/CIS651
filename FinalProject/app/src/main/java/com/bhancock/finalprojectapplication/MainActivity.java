package com.bhancock.finalprojectapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.splash_screen_image);
        mImageView.setImageResource(R.drawable.splashscreen);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        testUserLogin();
    }

    public void testUserLogin() {
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                if(mFirebaseUser == null) {
                    Toast.makeText(getApplicationContext(),
                            "No user found",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,
                            LoginActivity.class));
                        finish();
                }
                else {

                    if(mFirebaseUser.isEmailVerified()) {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "Please verify your email and login",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                }
            }
        }.start();
    }
}