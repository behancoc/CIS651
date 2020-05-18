package com.example.lab6app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContact extends AppCompatActivity {

    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mPhoneEditText;
    private Button mUpdateButton;
    private MyDBHelper dbHelper;
    private long contactId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        mFirstNameEditText = findViewById(R.id.contactName);
        mLastNameEditText = findViewById(R.id.contactLastName);
        mPhoneEditText = findViewById(R.id.contactPhone);
        mUpdateButton = findViewById(R.id.update_contact_button);

        dbHelper = new MyDBHelper(this);

        try {
            contactId = getIntent().getLongExtra("CONTACT_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Contact contact = dbHelper.getContact(contactId);
        mFirstNameEditText.setText(contact.getFirstName());
        mLastNameEditText.setText(contact.getLastName());
        mPhoneEditText.setText(contact.getPhoneNumber());
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact();
            }
        });
    }

    private void updateContact() {
        String firstName = mFirstNameEditText.getText().toString().trim();
        String lastName = mLastNameEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();
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
        Contact updatedContact = new Contact(firstName, lastName, phone);
        dbHelper.updateContact(contactId, this, updatedContact);
        finish();
    }
}
