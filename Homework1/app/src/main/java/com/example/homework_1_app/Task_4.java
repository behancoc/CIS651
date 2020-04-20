package com.example.homework_1_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Task_4 extends AppCompatActivity {

    private static final String TAG = Task_4.class.getSimpleName();

    private GestureDetectorCompat mDetector;
    ImageView imageView;
    SeekBar seekBar;
    MovieData movieData = new MovieData();
    CoordinatorLayout coordinatorLayout;
    int index =  0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_4);

        mDetector = new GestureDetectorCompat(getApplicationContext(), new MyGestureListener());


        imageView = findViewById(R.id.movie_image_view);
        imageView.setImageResource((Integer) movieData.getItem(0).get("image"));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast =  Toast.makeText(getApplicationContext(), "Come up with better toast message!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
                toast.show();
                Snackbar snackbar = Snackbar.make(v, "My Snackbar!", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                seekBar.setProgress(50);
                return true;
            }
        });


        seekBar = findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                double percentage = progress/100.00;
                Log.d(TAG, "Progress: " + percentage);
                //scaleImage(imageView, progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        private static final int MIN_SWIPE_DISTANCE_X_AXIS = 100;
        private static final int MIN_SWIPE_DISTANCE_Y_AXIS = 100;

        //Basing this upon device... currently running a Google Pixel 3XL
        //The maximum distance I can swipe from edge to edge is approx 1400 x axis
        private static final int MAX_SWIPE_DISTANCE_X_AXIS = 1100;
        private static final int MAX_SWIPE_DISTANCE_Y_AXIS = 1100;



        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling event 1: " + e1.toString());
            Log.d(DEBUG_TAG, "onFling event 2: " + e2.toString());
            Log.d(DEBUG_TAG, "onFling velocity x: " + velocityX);
            Log.d(DEBUG_TAG, "onFling velocity y: " + velocityY);

            double changeInXAxis = e1.getX() - e2.getX();
            double changeInYAxis = e1.getY() - e2.getY();

            Log.d(DEBUG_TAG, "changeInXAxis " + changeInXAxis);
            Log.d(DEBUG_TAG, "changeInYAxis " + changeInYAxis);

            if((Math.abs(changeInXAxis)>= MIN_SWIPE_DISTANCE_X_AXIS) && (Math.abs(changeInXAxis) <= MAX_SWIPE_DISTANCE_X_AXIS)) {
                Log.d(DEBUG_TAG, "here");
                if (changeInXAxis > 0) {

                    try {
                        Log.d(DEBUG_TAG, "Decrement Index!!!!!!!!!!!!");
                        index ++;
                        imageView.setImageResource((Integer) movieData.getItem(index).get("image"));

                    } catch (IndexOutOfBoundsException | NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                if (changeInXAxis < 0) {
                    try {
                        if (index == 0) {

                            Log.d(DEBUG_TAG, "Protection against index out of bounds issue...");

                        } else {
                            Log.d(DEBUG_TAG, "Decrement Index!!!!!!!!!!!!");
                            index--;
                            imageView.setImageResource((Integer) movieData.getItem(index).get("image"));
                        }
                    } catch(IndexOutOfBoundsException | NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }


        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(DEBUG_TAG, "onLongPress: " + e.toString());
            seekBar.setProgress(50);
        }
    }

    public void scaleImage(ImageView image, double progressPercentage) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Log.d(TAG, "Image width: " + width);
        Log.d(TAG, "Image height: " + height);

        double scaledWidth = progressPercentage * width;
        double scaledHeight = progressPercentage * height;

        bitmap = Bitmap.createScaledBitmap(bitmap, (int)scaledWidth, (int)scaledHeight, true);
        image.setImageBitmap(bitmap);
    }
}
