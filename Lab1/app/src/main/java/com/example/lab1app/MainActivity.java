package com.example.lab1app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout mainContainer = findViewById(R.id.main_containter);


        Button button = (Button) findViewById(R.id.add_button);
        button.setText("Add Item");
        EditText editText = findViewById(R.id.edit_text_view);

        //final String newText = editText.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello!", Toast.LENGTH_LONG).show();
                mainContainer.addView(addNew("TEST"));

            }
        });
    }

    public LinearLayout addNew(String textFieldString) {
        final LinearLayout mainContainer  = findViewById(R.id.main_containter);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(0, 5, 0, 0);
        linearLayout.setWeightSum(3);

        final EditText editText = new EditText(getApplicationContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout
                .LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2f);
        editText.setLayoutParams(layoutParams);

        Button button = new Button(getApplicationContext());
        button.setText(textFieldString);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout
                .LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        button.setLayoutParams(layoutParams1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainContainer.addView(addNew(editText.toString()));
            }
        });
        linearLayout.addView(editText);
        linearLayout.addView(button);

        return linearLayout;

    }
}
