package com.example.homework_1_app;

import androidx.appcompat.app.AppCompatActivity;
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

    private GestureDetectorCompat gestureDetector;
    ImageView imageView;
    SeekBar seekBar;
    MovieData movieData = new MovieData();
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_4);

        imageView = findViewById(R.id.movie_image_view);
        imageView.setImageResource(R.drawable.alice);

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
