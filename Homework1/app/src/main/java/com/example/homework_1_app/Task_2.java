package com.example.homework_1_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Task_2 extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_2);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            TextView textView  = findViewById(R.id.text_view);
        }

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflateButtons();

        }
    }

    private void inflateButtons() {
        button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + button1.getId()
                        + "\n"
                        + "Button Text: " + button1.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        button2 = findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + button2.getId()
                        + "\n"
                        + "Button Text: " + button2.getText(), Toast.LENGTH_SHORT).show();

            }
        });

        button3 = findViewById(R.id.button_3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + button3.getId()
                        + "\n"
                        + "Button Text: " + button3.getText(), Toast.LENGTH_SHORT).show();

            }
        });

        button4 = findViewById(R.id.button_4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + button4.getId()
                        + "\n"
                        + "Button Text: " + button4.getText(), Toast.LENGTH_SHORT).show();

            }
        });

        button5 = findViewById(R.id.button_5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + button5.getId()
                        + "\n"
                        + "Button Text: " + button5.getText(), Toast.LENGTH_SHORT).show();

            }
        });

        button6 = findViewById(R.id.button_6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + button6.getId()
                        + "\n"
                        + "Button Text: " + button6.getText(), Toast.LENGTH_SHORT).show();

            }
        });

        button7 = findViewById(R.id.button_7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + button7.getId()
                        + "\n"
                        + "Button Text: " + button7.getText(), Toast.LENGTH_SHORT).show();

            }
        });

        buttonSearch = findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + buttonSearch.getId()
                        + "\n"
                        + "Button Text: " + buttonSearch.getText(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}
