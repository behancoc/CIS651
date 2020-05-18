package com.example.lab6app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewContact extends AppCompatActivity {

    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mPhoneEditText;
    private Button mAddButton;
    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        mFirstNameEditText = findViewById(R.id.contactName);
        mLastNameEditText = findViewById(R.id.contactLastName);
        mPhoneEditText = findViewById(R.id.contactPhone);
        mAddButton = findViewById(R.id.add_new_contact_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });
    }

    private void saveContact() {
        String firstName = mFirstNameEditText.getText().toString().trim();
        String lastName = mLastNameEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();
        dbHelper = new MyDBHelper(this);
        if (firstName.isEmpty()) {
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (lastName.isEmpty()) {
            Toast.makeText(this, "You must enter a last name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "You must enter a phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        Contact contact = new Contact(firstName, lastName, phone);
        dbHelper.saveNewContact(contact, this);
        finish();
    }
}
