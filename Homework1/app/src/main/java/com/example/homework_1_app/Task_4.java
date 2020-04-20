package com.example.homework_1_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
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
                resetImageSize(imageView);
                return true;
            }
        });


        seekBar = findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                imageView.getHeight();
                scaleImage(imageView, progress);
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

        //Basing this upon device... currently running a Google Pixel 3XL
        //The maximum distance I can swipe from edge to edge is approx 1400 x axis
        private static final int MAX_SWIPE_DISTANCE_X_AXIS = 1100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling event 1: " + e1.toString());
            Log.d(DEBUG_TAG, "onFling event 2: " + e2.toString());
            Log.d(DEBUG_TAG, "onFling velocity x: " + velocityX);


            double changeInXAxis = e1.getX() - e2.getX();

            Log.d(DEBUG_TAG, "changeInXAxis " + changeInXAxis);

            if((Math.abs(changeInXAxis)>= MIN_SWIPE_DISTANCE_X_AXIS) && (Math.abs(changeInXAxis) <= MAX_SWIPE_DISTANCE_X_AXIS)) {
                Log.d(DEBUG_TAG, "here");
                if (changeInXAxis > 0) {

                    Log.d(DEBUG_TAG, "Index number: " + index);

                    try {
                        if (index == movieData.getSize() - 1) {
                            Log.d(DEBUG_TAG, "Index number inside try: " + index);
                            Log.d(DEBUG_TAG, "Protection against index out of bounds issue...");

                        } else {
                            Log.d(DEBUG_TAG, "Decrement Index!!!!!!!!!!!!");
                            index ++;
                            imageView.setImageResource((Integer) movieData.getItem(index).get("image"));
                        }

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
    }

    public void resetImageSize(ImageView imageView) {
          imageView.getLayoutParams().width = 637;
          imageView.getLayoutParams().height = 943;
          imageView.requestLayout();
    }

    public void scaleImage(ImageView image, double seekBarProgress) {
        int defaultWidth = 637;
        int defaultHeight = 943;
        double scaledWidth = defaultWidth;
        double scaledHeight = defaultHeight;


        double width = image.getWidth();
        double height = image.getHeight();

        double progressPercentage = seekBarProgress/100.0;



        if (progressPercentage <= 0.49) {
            double adjustedPercentage = (1.0 + (progressPercentage - 0.50));
            scaledWidth = adjustedPercentage * defaultWidth;
            scaledHeight = adjustedPercentage * defaultHeight;
            Log.d(TAG, "scaledWidth: " + scaledWidth);
            Log.d(TAG, "scaledHeight: " + scaledHeight);
        }


        if (progressPercentage == 0.50) {
             scaledWidth = defaultWidth;
             scaledHeight = defaultHeight;

        }

        if (progressPercentage >= 0.51) {
            double adjustedPercentage = (1.0 + (progressPercentage - 0.50));
            scaledWidth = (adjustedPercentage + progressPercentage) * defaultWidth;
            scaledHeight = (adjustedPercentage + progressPercentage) * defaultWidth;
        }

        imageView.getLayoutParams().width = (int) scaledWidth;
        imageView.getLayoutParams().height = (int) scaledHeight;
        imageView.requestLayout();


    }
}
