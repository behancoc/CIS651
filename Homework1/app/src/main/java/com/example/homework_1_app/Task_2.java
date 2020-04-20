package com.example.homework_1_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Task_2 extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button40;
    private Button button100000;
    private Button buttonSave;
    private Button buttonSubmit;
    private Button buttonCancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_2);

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

        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + buttonSave.getId()
                        + "\n"
                        + "Button Text: " + buttonSave.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + buttonSubmit.getId()
                        + "\n"
                        + "Button Text: " + buttonSubmit.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel = findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button ID: " + buttonCancel.getId()
                        + "\n"
                        + "Button Text: " + buttonCancel.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
