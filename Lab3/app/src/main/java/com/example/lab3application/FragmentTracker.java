package com.example.lab3application;

public interface FragmentTracker {
    public void fragmentVisible(String s);
    public void goNext();
    public void goBack();
    public void saveNameAndLastName(String firstName, String lastName);
    public void saveCityAndZip(String city, String zip);
    public void saveLanguage(String language);
    public void finished();
}
