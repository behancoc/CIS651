package com.bhancock.finalprojectapplication.ui.profile;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bhancock.finalprojectapplication.EditProfileActivity;
import com.bhancock.finalprojectapplication.LoginActivity;
import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.adapter.ProfileGridAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private RecyclerView mProfileGridRecyclerView;
    private ProfileGridAdapter mAdapter;
    private Context mContext;
    private Button mEditProfileButton;
    private Toolbar mToolbar;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference usersRef;
    private ImageView profileImage;
    private TextView userNameTextView;
    private String uid;
    private FirebaseFirestore firebaseFirestore;

    private final static String TAG = ProfileFragment.class.getSimpleName();



    private void initRecyclerView() {
        mContext = getActivity().getApplicationContext();
        mAdapter = new ProfileGridAdapter(getContext(), profileViewModel.getUserPlaces().getValue());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        mProfileGridRecyclerView.setLayoutManager(layoutManager);
        mProfileGridRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init();
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
//        mProfileGridRecyclerView = root.findViewById(R.id.profile_grid_recycler_view);

//        initRecyclerView();

//        profileViewModel.getUserPlaces().observe(getViewLifecycleOwner(), new Observer<List<VisitedPlaces>>() {
//            @Override
//            public void onChanged(List<VisitedPlaces> visitedPlaces) {
//                mAdapter.notifyDataSetChanged();
//            }
//        });

        profileImage = root.findViewById(R.id.profile_image_view);
        userNameTextView = root.findViewById(R.id.username_text_view);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef = firebaseDatabase.getReference("Users/" + mFirebaseUser.getUid());

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("profilePicture").exists()) {

                    Picasso.get().load(dataSnapshot.child("profilePicture").getValue().toString()).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DocumentReference userDocumentReference =
                firebaseFirestore.collection("User")
                        .document(FirebaseAuth.getInstance().getUid());

        userDocumentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    String userName = documentSnapshot.getString("userName");
                    userNameTextView.setText(userName);
                }
            }
        });



        userDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String userName = documentSnapshot.getString("userName");
                    Log.i(TAG, "userName : " + userName);
                    userNameTextView.setText(userName);

                } else {
                    Toast.makeText(getContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "document has data.." + documentSnapshot.getData());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "fail");
                e.printStackTrace();
            }
        });




        setHasOptionsMenu(true);

        mEditProfileButton = root.findViewById(R.id.edit_profile_button);
        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void logoutUser() {
        mFirebaseAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_logout:
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}