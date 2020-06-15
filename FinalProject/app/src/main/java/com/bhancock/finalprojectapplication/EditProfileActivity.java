package com.bhancock.finalprojectapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = EditProfileActivity.class.getSimpleName();
    private static final int REQUEST_FOR_CAMERA=0011;
    private static final int OPEN_FILE=0012;

    private EditText displayName, phoneNumber;
    private ImageView profileImage;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference usersRef;
    private Uri imageUri = null;
    private String uid;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profileImage = findViewById(R.id.profile_image);
        phoneNumber = findViewById(R.id.phone_number_text);
        displayName =findViewById(R.id.display_name_text);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        firebaseFirestore =  FirebaseFirestore.getInstance();


        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference userRef = firebaseFirestore.collection("User");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phoneNumber.setText(dataSnapshot.child("phone").getValue().toString());
                displayName.setText(dataSnapshot.child("displayName").getValue().toString());

                if(dataSnapshot.child("profilePicture").exists()) {
                    Picasso.get().load(dataSnapshot.child("profilePicture").getValue().toString())
                            .transform(new CircleTransform()).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DocumentReference userDocumentReference =
                firebaseFirestore.collection("User")
                        .document(FirebaseAuth.getInstance().getUid());


        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef = firebaseDatabase.getReference("Users/" + currentUser.getUid());

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("profilePicture").exists()) {

                    Picasso.get().load(dataSnapshot.child("profilePicture").getValue().toString())
                            .transform(new CircleTransform()).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.takephoto:
                checkPermissions();
                return true;
            case R.id.upload:
                Intent intent = new Intent().setType("*/").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select a file"), OPEN_FILE);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void uploadProfilePhoto(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    public void takePhoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        Intent chooser=Intent.createChooser(intent,"Select a Camera App.");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(chooser, REQUEST_FOR_CAMERA);
        }
    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getBaseContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,
                    "We need permission to access your camera and photo.",
                    Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_FOR_CAMERA);
        } else {
            takePhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && requestCode == REQUEST_FOR_CAMERA) {
            if(ContextCompat.checkSelfPermission(getBaseContext(),
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getBaseContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                takePhoto();
            } else {
                Toast.makeText(this,
                        "We need to access your camera and photos to upload",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadImage() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        final String fileNameInStorage = UUID.randomUUID().toString();
        String path = "images/" + fileNameInStorage + ".jpg";
        final StorageReference imageReference = firebaseStorage.getReference(path);
        imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        usersRef.child("profilePicture").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Picasso.get().load(uri.toString()).transform(new CircleTransform()).into(profileImage);

                                Picasso.get().load(uri.toString()).into(profileImage);

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FOR_CAMERA && resultCode == RESULT_OK) {
            if(imageUri==null)
            {
                Toast.makeText(this, "Error taking photo.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            uploadImage();
            return;
        }
        if(requestCode==OPEN_FILE && resultCode==RESULT_OK) {
            imageUri = data.getData();
            uploadImage();
        }
    }

    public void Save(View view) {
//        if(displayName.getText().toString().equals("") ||
//                phoneNumber.getText().toString().equals(""))
//        {
//            Toast.makeText(this, "Please enter your display name and phone number",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }
//        usersRef.child("phone").setValue(phoneNumber.getText().toString());
//        usersRef.child("displayname").setValue(displayName.getText().toString());
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}