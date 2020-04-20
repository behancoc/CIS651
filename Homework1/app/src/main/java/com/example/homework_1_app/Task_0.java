package com.example.homework_1_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Task_0 extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button40;
    private Button button100000;
    private Button saveButton;
    private Button submitButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_0);

        inflateButtons();
    }

    private void inflateButtons() {
        button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + button1.getId() + "\n"
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

        button40 = findViewById(R.id.button_40);
        button40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + button40.getId()
                        + "\n"
                        + "Button Text: " + button40.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        button100000 = findViewById(R.id.button_100000);
        button100000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + button100000.getId()
                        + "\n"
                        + "Button Text: " + button100000.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + saveButton.getId()
                        + "\n"
                        + "Button Text: " + saveButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + submitButton.getId()
                        + "\n"
                        + "Button Text: " + submitButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton = findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + cancelButton.getId()
                        + "\n"
                        + "Button Text: " + cancelButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
