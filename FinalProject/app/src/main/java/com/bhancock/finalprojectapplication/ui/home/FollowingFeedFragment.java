package com.bhancock.finalprojectapplication.ui.home;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.adapter.TripViewAdapter;
import com.bhancock.finalprojectapplication.model.Trip;
import com.bhancock.finalprojectapplication.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class FollowingFeedFragment extends Fragment {

    private static final String TAG = FollowingFeedFragment.class.getSimpleName();

    private Context mContext;
    private RecyclerView mRecyclerView;
    private TripViewAdapter mTripViewAdapter;
    private ArrayList<Trip> tripList = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    private String followingPath;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("User");

        DocumentReference userDocumentReference =
                firebaseFirestore.collection("User")
                        .document(FirebaseAuth.getInstance().getUid());

        Task<QuerySnapshot> querySnapshotTask = collectionReference.getFirestore().collection("User").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                queryDocumentSnapshots.getDocuments().get(0).getReference()
                        .collection("Following").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        queryDocumentSnapshots.getDocuments().get(0).getReference()
                                .collection("Trips").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                followingPath = queryDocumentSnapshots.getDocuments().get(0).getReference().getPath();
                                Log.d(TAG, "PATH: " + followingPath);

                                queryDocumentSnapshots.getDocuments().get(0).getReference().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Log.d(TAG, "GET DATA" +documentSnapshot.getData());

                                        //FINALLY GETTING DATA


                                    }
                                });

                                Log.d(TAG, "Metadata: " + queryDocumentSnapshots.getMetadata());

//
                            }
                        });
                    }
                });
            }
        });



        String path = firebaseFirestore.collection("User")
                .document(FirebaseAuth.getInstance().getUid()).collection("Following")
                .document().collection("Trips").document().getPath();


        Log.d(TAG, "path?: " + path);



        userDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "I was able to retrieve the current user!");

                User currentUser = documentSnapshot.toObject(User.class);
                //mUserLocation.setUser(currentUser);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "I was unable to find the current user :o( ");
                e.printStackTrace();
                e.getLocalizedMessage();
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_home_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();
        setUpRecyclerView(view);
    }

    private void setUpRecyclerView(View view) {

        //TODO: Correct Document path issue so it works at runtime without hardcoded string
//        Query query = collectionReference.getFirestore().collection("User")
//                .document("Qr8M3UK2amYKaFTPNKJCNNgWmt22")
//                .collection("Trips").orderBy("tripTitle", Query.Direction.DESCENDING);


        String path = firebaseFirestore.collection("User")
                .document(FirebaseAuth.getInstance().getUid()).collection("Following")
                .document().collection("Trips").getPath();




        Log.d(TAG, "path?: " + path);

        final Query query = collectionReference.getFirestore().collection("User")
                .document("Qr8M3UK2amYKaFTPNKJCNNgWmt22")
                .collection("Following").document("gGnJTLNCXRR3gYxHd0LS")
                .collection("Trips").orderBy("tripTitle", Query.Direction.DESCENDING);



        FirestoreRecyclerOptions<Trip> tripFirestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<Trip>().setQuery(query, Trip.class).build();

        mTripViewAdapter = new TripViewAdapter(tripFirestoreRecyclerOptions, mContext);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mTripViewAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mTripViewAdapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        mTripViewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mTripViewAdapter.stopListening();
    }
}