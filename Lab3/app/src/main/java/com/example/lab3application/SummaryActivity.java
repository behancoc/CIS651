package com.example.lab3application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        PersonalInfo personalInfo =  (PersonalInfo) getIntent().getParcelableExtra("pi");
        TextView nl=findViewById(R.id.name_lastname);
        TextView cz=findViewById(R.id.city_zip);
        TextView lang=findViewById(R.id.lang);
        nl.setText("First name : "+
                personalInfo.getFirstName() +
                " LastName : " +
                personalInfo.getLastName());

        cz.setText("City : "+ personalInfo.getCity()+" Zip : "+ personalInfo.getZip());
        lang.setText("Language : "+ personalInfo.getLanguage());


    }
}
