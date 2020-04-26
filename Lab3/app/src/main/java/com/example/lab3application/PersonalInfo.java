package com.example.lab3application;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonalInfo implements Parcelable {
    private String firstName;
    private String lastName;
    private String city;
    private String zip;
    private String language;

    public PersonalInfo() {
        firstName = "";
        lastName = "";
        city = "";
        zip = "";
        language = "";
    }

    public PersonalInfo(String firstName, String lastName, String city, String zip, String language) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.zip = zip;
        this.language = language;
    }

    public PersonalInfo(Parcel input) {
        String[] data = new String[5];
        input.readStringArray(data);
        firstName = data[0];
        lastName = data[1];
        city = data[2];
        zip = data[3];
        language = data[4];
    }

    public static final Creator<PersonalInfo> CREATOR = new Creator<PersonalInfo>() {
        @Override
        public PersonalInfo createFromParcel(Parcel in) {
            return new PersonalInfo(in);
        }

        @Override
        public PersonalInfo[] newArray(int size) {
            return new PersonalInfo[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] {
                firstName, lastName, city, zip, language
        });
    }


}
