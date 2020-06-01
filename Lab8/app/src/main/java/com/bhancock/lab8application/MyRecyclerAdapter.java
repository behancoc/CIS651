package com.bhancock.lab8application;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{
    private static final String TAG = MyRecyclerAdapter.class.getSimpleName();

    SimpleDateFormat localDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private class UserModel {
        public String postKey;
        public String uid;
        public String description;
        public String url;
        public String date;

        public UserModel(String uid, String description, String url, String date, String key) {
            this.uid = uid;
            this.description = description;
            this.url = url;
            this.date = date;
            this.postKey = key;

        }
    }

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference userReference = firebaseDatabase.getReference("Posts");
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private List<UserModel> usersList;
    private RecyclerView recyclerView;
    private ChildEventListener usersRefListener;



    public MyRecyclerAdapter(final RecyclerView recyclerView) {
        usersList = new ArrayList<>();
        this.recyclerView = recyclerView;

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        usersRefListener = userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, dataSnapshot.toString());
                UserModel userModel = new UserModel(dataSnapshot.child("author").getValue()
                        .toString(), dataSnapshot.child("description").getValue().toString(),
                        dataSnapshot.child("url").getValue().toString(),
                        localDateFormat.format(new Date(Long.parseLong(dataSnapshot
                                .child("timestamp").getValue().toString()))), dataSnapshot.getKey());

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

    public void removeListener() {
        if(userReference!=null && usersRefListener!=null)
            userReference.removeEventListener(usersRefListener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstNameView;
        public TextView emailView;
        public TextView phoneView;
        public TextView dateView;

        public TextView descriptionView;
        public ImageView imageView;
        public ImageView likeBtn;
        public TextView likeCount;
        DatabaseReference uref;
        ValueEventListener urefListener;

        DatabaseReference likeCountRef;
        ValueEventListener likeCountRefListener;

        DatabaseReference likesRef;
        ValueEventListener likesRefListener;

        public ViewHolder(View view) {
            super(view);

            firstNameView = view.findViewById(R.id.first_name_view);
            emailView = view.findViewById(R.id.email_view);
            phoneView = view.findViewById(R.id.phone_view);
            dateView = view.findViewById(R.id.date_view);

            descriptionView = view.findViewById(R.id.description);
            imageView = view.findViewById(R.id.post_image);
            likeBtn = view.findViewById(R.id.like_btn);
            likeCount = view.findViewById(R.id.like_count);
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
    public void onBindViewHolder(@NonNull final MyRecyclerAdapter.ViewHolder holder, int position) {
        final UserModel userModel = usersList.get(position);
        String uid = userModel.uid;

        Picasso.get().load(userModel.url).into(holder.imageView);
        holder.descriptionView.setText(userModel.description);

        if(holder.uref != null && holder.urefListener != null) {
            holder.uref.removeEventListener(holder.urefListener);
        }

        if(holder.likesRef != null && holder.likesRefListener != null) {
            holder.likesRef.removeEventListener(holder.likesRefListener);
        }

        if(holder.likeCountRef != null && holder.likeCountRefListener != null) {
            holder.likeCountRef.removeEventListener(holder.likeCountRefListener);
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        holder.uref = database.getReference("Users").child(uid);
        holder.uref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.firstNameView.setText("First Name: "
                        + dataSnapshot.child("displayName").getValue().toString());

                holder.emailView.setText("Email: " +
                        dataSnapshot.child("email").getValue().toString());

                holder.phoneView.setText("Phone Num: " +
                        dataSnapshot.child("phone").getValue().toString());

                holder.dateView.setText("Date Created: "+ userModel.date);

                holder.likeCountRef=
                        database.getReference("Posts/"+ userModel.postKey + "/likeCount");

                holder.likeCountRefListener = holder.likeCountRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.likeCount.setText(dataSnapshot.getValue().toString() + "Likes");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                holder.likesRef=database.getReference("Posts/"+ userModel.postKey + "/likes/" + currentUser
                        .getUid());

                holder.likesRefListener = holder.likesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists() && dataSnapshot.getValue().toString().equals("true")) {
                            holder.likeBtn.setImageDrawable(ContextCompat.getDrawable(recyclerView.
                                    getContext(), R.drawable.like_active));
                        } else {
                            holder.likeBtn.setImageDrawable(ContextCompat.getDrawable(recyclerView.
                                    getContext(), R.drawable.like_disabled));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                holder.likeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.getReference("Posts/" + userModel.postKey).runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                                PhotoPreviewActivity.Post p = mutableData.getValue(PhotoPreviewActivity.Post.class);
                                if(p == null) {
                                    return Transaction.success(mutableData);
                                }

                                if(p.likes.containsKey(currentUser.getUid())) {
                                    p.likeCount --;
                                    p.likes.remove(currentUser.getUid());
                                } else {
                                    p.likeCount ++;
                                    p.likes.put(currentUser.getUid(), true);
                                }

                                // Set value and report transaction success
                                mutableData.setValue(p);
                                return Transaction.success(mutableData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError,
                                                   boolean b, @Nullable DataSnapshot dataSnapshot) {


                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return usersList.size();
    }

}
