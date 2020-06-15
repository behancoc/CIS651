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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class UserFeedFragment extends Fragment {

    private static final String TAG = UserFeedFragment.class.getSimpleName();

    private Context mContext;
    private RecyclerView mRecyclerView;
    private TripViewAdapter mTripViewAdapter;
    private ArrayList<Trip> tripList = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("Trip");

         DocumentReference userDocumentReference =
                firebaseFirestore.collection("User")
                        .document(FirebaseAuth.getInstance().getUid());


         Task<QuerySnapshot> hopeReferenceQuery = firebaseFirestore.collection("User")
                 .document(FirebaseAuth.getInstance().getUid()).collection("Trips")
                 .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                     @Override
                     public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                         for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                             Log.d(TAG, "get Data: " + documentSnapshot.getData());

                             Log.d(TAG, "get some: " + documentSnapshot.get("tripDetails"));


                         }
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {

                     }
                 });


         DocumentReference testReference = firebaseFirestore.document("User/Qr8M3UK2amYKaFTPNKJCNNgWmt22/Trips/o56JnJa5LhoYFZxpibvF");
         testReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
             @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {
                 if(documentSnapshot.exists()) {
                     Log.d(TAG, "Success??????");
                     String td = documentSnapshot.getString("tripDetails");
                     Log.d(TAG, "td: " + td);

                 } else {
                     Log.d(TAG, "document does not exist");
                 }
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {

             }
         });

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
        //mRecyclerView = view.findViewById(R.id.recyclerView);
        //populateData();
        //mTripViewAdapter = new TripViewAdapter(mContext, tripList);
        //mRecyclerView.setAdapter(mTripViewAdapter);

        setUpRecyclerView(view);

    }

    private void setUpRecyclerView(View view) {

        //TODO: Correct Document path issue so it works at runtime without hardcoded string
        Query query = collectionReference.getFirestore().collection("User")
                .document("Qr8M3UK2amYKaFTPNKJCNNgWmt22")
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


    private void populateData() {
//        tripList.add(new Trip("image_1", "Android", "California", "Los Angeles", 27));
//        tripList.add(new Trip("image_2",  "Android", "California", "Los Angeles", 28));
//        tripList.add(new Trip("image_3",  "Android", "California", "Los Angeles", 29));
//        tripList.add(new Trip("image_4",  "Android", "California", "Los Angeles", 30));
//        tripList.add(new Trip("image_5",  "Android", "California", "Los Angeles", 31));
//        tripList.add(new Trip("image_6",  "Android", "California", "Los Angeles", 32));
//        tripList.add(new Trip("image_1",  "Android", "California", "Los Angeles", 33));
//        tripList.add(new Trip("image_2",  "Android", "California", "Los Angeles", 24));
//        tripList.add(new Trip("image_3",  "Android", "California", "Los Angeles", 35));
//        tripList.add(new Trip("image_4",  "Android", "California", "Los Angeles", 36));
//        tripList.add(new Trip("image_5",  "Android", "California", "Los Angeles", 37));
//        tripList.add(new Trip("image_6",  "Android", "California", "Los Angeles", 38));
//        tripList.add(new Trip("image_1",  "Android", "California", "Los Angeles", 29));
    }
}
