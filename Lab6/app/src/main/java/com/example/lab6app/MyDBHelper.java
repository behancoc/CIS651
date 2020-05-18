package com.example.lab6app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Contacts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CONTACT_NAME = "firstname";
    public static final String COLUMN_CONTACT_LASTNAME = "lastname";
    public static final String COLUMN_CONTACT_PHONE = "phone";

    public MyDBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CONTACT_NAME + " TEXT NOT NULL," +
                COLUMN_CONTACT_LASTNAME + " TEXT NOT NULL," +
                COLUMN_CONTACT_PHONE + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void saveNewContact(Contact contact, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME, contact.getFirstName());
        values.put(COLUMN_CONTACT_LASTNAME, contact.getLastName());
        values.put(COLUMN_CONTACT_PHONE, contact.getPhoneNumber());
        db.insert(TABLE_NAME, null, values);
        db.close();
        Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
    }

    public List<Contact> contactList() {
        String query = "SELECT * FROM " + TABLE_NAME;

        List<Contact> contactLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Contact contact;

        if (cursor.moveToFirst()) {
            do {
            contact = new Contact();
            contact.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            contact.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NAME)));
            contact.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_LASTNAME)));
            contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_PHONE)));
            contactLinkedList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactLinkedList;
    }

    public Contact getContact(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE _id=" + id;
        Cursor cursor = db.rawQuery(query, null);
        Contact contact = new Contact();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            contact.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NAME)));
            contact.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_LASTNAME)));
            contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_PHONE)));
        }
        cursor.close();
        return contact;
    }

    public void deleteContact(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
    }

    public void updateContact(long contactId, Context context, Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET name='" + contact.getFirstName() +
                "', lastname ='" + contact.getLastName() + "', phone ='" + contact.getPhoneNumber()
                + "' WHERE _id='" + contactId + "'");

        Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
    }
}
