package com.example.lab1app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout mainContainer = findViewById(R.id.main_containter);

        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            final Button button = findViewById(R.id.add_button);
            button.setText("Add Item");
            final EditText editText = findViewById(R.id.edit_text_view);

            final String[] textField = new String[1];
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        textField[0] = editText.getText().toString();
                        Log.d(TAG, "textField: " + editText.getText().toString());
                        editText.setCursorVisible(false);

                    }
                    return false;
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mainContainer.addView(addNew(textField[0]));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Button mButton = findViewById(R.id.add_button);
            mButton.setText("Add Image".toUpperCase());

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addNewInLandscape();
                }
            });
        }
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
        editText.setHint("Enter Text");
        editText.setSingleLine();
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        final String[] textField = new String[1];
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);

                    textField[0] = editText.getText().toString();

                    editText.setCursorVisible(false);
                }
                return false;
            }
        });


        Button button = new Button(getApplicationContext());
        button.setText(textFieldString.toUpperCase());
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout
                .LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        button.setLayoutParams(layoutParams1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mainContainer.addView(addNew(textField[0]));
                } catch(NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        linearLayout.addView(editText);
        linearLayout.addView(button);
        return linearLayout;
    }

    public void addNewInLandscape() {
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.syracuse_univ);
        LinearLayout linearLayout = findViewById(R.id.main_containter);
        linearLayout.addView(imageView);

    }
}
