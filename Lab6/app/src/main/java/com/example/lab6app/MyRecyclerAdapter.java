package com.example.lab6app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private static final String TAG = MyRecyclerAdapter.class.getSimpleName();

    private List<Contact> mContactList;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public MyRecyclerAdapter(List<Contact> myDataset, Context context, RecyclerView recyclerView) {
        mContactList = myDataset;
        mContext = context;
        mRecyclerView = recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstNameView;
        public TextView lastNameView;
        public TextView phoneView;

        public ViewHolder(View view) {
            super(view);
            firstNameView = view.findViewById(R.id.first_name_view);
            lastNameView = view.findViewById(R.id.last_name_view);
            phoneView = view.findViewById(R.id.phone_number);
        }
    }

    public void updateList(List<Contact> contactList) {
        mContactList = contactList;

        for (Contact contact : mContactList) {
            Log.d(TAG, "updateList: contact in list " + contact.getFirstName());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_layout, parent, false);
        //View view = LayoutInflater.from(parent.getContext().getApplicationContext()).inflate(R.layout.card_view_layout, parent, false);

        final ViewHolder  viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, final int position) {
        final Contact contact = mContactList.get(position);
        holder.firstNameView.setText("First Name: " + contact.getFirstName());
        holder.lastNameView.setText("Last Name: " + contact.getLastName());
        holder.phoneView.setText("Phone: " + contact.getPhoneNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose Option");
                builder.setMessage("Update or delete user?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToUpdateActivity(contact.getId());
                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDBHelper dbHelper = new MyDBHelper(mContext);
                        dbHelper.deleteContact(contact.getId(), mContext);
                        mContactList.remove(position);
                        mRecyclerView.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mContactList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "The current contact list size is: " + mContactList.size());
        return mContactList.size();
    }

    private void goToUpdateActivity(long personId) {
        Intent intent = new Intent(mContext, UpdateContact.class);
        intent.putExtra("CONTACT_ID", personId);
        mContext.startActivity(intent);
    }
}
