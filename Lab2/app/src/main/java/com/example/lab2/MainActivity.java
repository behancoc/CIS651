package com.example.lab2;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView textView;
    private SeekBar seekBar;
    private ToggleButton toggleButton;
    private Integer oldValue = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.scoretext);
        seekBar = findViewById(R.id.seekBar);
        toggleButton = findViewById(R.id.disable_snack);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                oldValue = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                if (toggleButton.isChecked()) {
                    Snackbar snackbar = Snackbar.make(seekBar, "Progress Changed",
                            Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textView.setText(Integer.toString(oldValue));
                            seekBar.setProgress(oldValue);
                            Snackbar snackbar1 = Snackbar.make(v, "Seekbar restored", Snackbar.LENGTH_SHORT);
                            snackbar1.show();
                        }
                    });
                    snackbar.show();
                }

            }
        });
    }


    public void createToast(View view) {
        RadioButton simpleRB = findViewById(R.id.rb_simple);
        Toast.makeText(this, "Simple Toast Message", Toast.LENGTH_SHORT).show();

        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.custom_toast,
                (ViewGroup)findViewById(R.id.toastRoot));

        SeekBar seekBarInToast = layout.findViewById(R.id.seekBarInToast);
        TextView textViewInToast = layout.findViewById(R.id.textViewinToast);

        //TODO: Set the values for these, from the actual seekbar’s progess.

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


    @Override
    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

        Calendar calendar=Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        final TextView textViewDateTime=findViewById(R.id.datetime);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String oldValue=textViewDateTime.getText().toString();

                textViewDateTime.setText(String.valueOf(month)
                        + "/" + String.valueOf(dayOfMonth)
                        + "/" + String.valueOf(year)
                        + " " + String.valueOf(hourOfDay)
                        + ":"
                        + String.valueOf(minute));

                Snackbar snackbar = Snackbar.make(textViewDateTime, "Date and Time Selected",
                        Snackbar.LENGTH_LONG).setAction("UNDO",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                textViewDateTime.setText(oldValue);
                                Toast.makeText(MainActivity.this,
                                        "Done!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        snackbar.show();
            }
        }, h, m, false);
        timePickerDialog.show();
    }

    public void selectDateTime(View view) {
        Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                this, yy, mm, dd);
        datePickerDialog.show();
    }
}
