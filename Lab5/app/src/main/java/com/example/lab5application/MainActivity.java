package com.example.lab5application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.alice);

        final Button nextActivityButton = (Button) findViewById(R.id.next_activity_button);
        nextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NextActivity(view);
            }
        });

        final Button moveAndRotateButton = (Button) findViewById(R.id.move_and_rotate_button);
        moveAndRotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.animate().setDuration(1000).x(500).y(800).rotationYBy(720).scaleX(.4F).scaleY(.4f);
            }
        });

        final Button moveBackButton = (Button) findViewById(R.id.move_back_button);
        moveBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.animate().setDuration(1000).x(imageView.getLeft()).y(imageView.getTop()).rotationYBy(720).scaleX(1F).scaleY(1F);
            }
        });

        final Button fadeOutButton = (Button) findViewById(R.id.fade_out_button);
        fadeOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.animate().setDuration(1000).alpha(0F);
            }
        });

        final Button fadeInButton = (Button) findViewById(R.id.fade_in_button);
        fadeInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.animate().setDuration(1000).alpha(1F);
            }
        });


        final Button xmlAnimatorButton = (Button) findViewById(R.id.xml_animator_button);
        xmlAnimatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator spinSet = (AnimatorSet) AnimatorInflater
                        .loadAnimator(getApplicationContext(), R.animator.custom_animator);

                spinSet.setTarget(imageView);
                spinSet.start();
            }
        });
    }

    public void NextActivity(View view) {
        Intent intent = new Intent(this, MasterDetailFlowActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, view, "newActivity");
        startActivity(intent, activityOptionsCompat.toBundle());
    }
}
