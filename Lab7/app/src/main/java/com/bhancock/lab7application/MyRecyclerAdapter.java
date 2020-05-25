package com.bhancock.lab7application;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private static final String TAG = MyRecyclerAdapter.class.getSimpleName();

    SimpleDateFormat localDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private class UserModel {
        public String displayName;
        public String email;
        public String phone;
        public String date;

        public UserModel (String displayName, String email, String phone) {
            this.displayName = displayName;
            this.email = email;
            this.phone = phone;

        }

        public UserModel (String displayName, String email, String phone, String date) {
            this.displayName = displayName;
            this.email = email;
            this.phone = phone;
            this.date = date;
        }
    }

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference userReference = firebaseDatabase.getReference("Users");
    private List<UserModel> usersList;
    private RecyclerView recyclerView;

    public MyRecyclerAdapter(final RecyclerView recyclerView) {
        usersList = new ArrayList<>();
        this.recyclerView = recyclerView;

        userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, dataSnapshot.toString());
                UserModel userModel = new UserModel(dataSnapshot.child("displayName").getValue()
                        .toString(), dataSnapshot.child("email").getValue().toString(),
                        dataSnapshot.child("phone").getValue().toString(),
                        localDateFormat.format(new Date(Long.parseLong(dataSnapshot
                                .child("timestamp").getValue().toString()))));

                usersList.add(userModel);
                MyRecyclerAdapter.this.notifyItemInserted(usersList.size() - 1);
                recyclerView.scrollToPosition(usersList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstNameView;
        public TextView emailView;
        public TextView phoneView;
        public TextView dateView;

        public ViewHolder(View view) {
            super(view);

            firstNameView = view.findViewById(R.id.first_name_view);
            emailView = view.findViewById(R.id.email_view);
            phoneView = view.findViewById(R.id.phone_view);
            dateView = view.findViewById(R.id.date_view);
        }
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, int position) {
        final UserModel userModel = usersList.get(position);
        holder.firstNameView.setText("First Name: " + userModel.displayName);
        holder.emailView.setText("Email: " + userModel.email);
        holder.phoneView.setText("Phone Number: " + userModel.phone);
        holder.dateView.setText("Date Created: " + userModel.date);
    }


    @Override
    public int getItemCount() {
        return usersList.size();
    }

}
