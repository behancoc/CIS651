package com.bhancock.finalprojectapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PhotoPreviewActivity extends AppCompatActivity {

    Uri uri;
    EditText description;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);

        uri= Uri.parse(getIntent().getStringExtra("uri"));
        ImageView imageView=findViewById(R.id.preview_image);
        imageView.setImageURI(uri);
        description=findViewById(R.id.description);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    private void uploadImage() {
        FirebaseStorage storage= FirebaseStorage.getInstance();
        final String fileNameInStorage = UUID.randomUUID().toString();
        String path = "images/" + fileNameInStorage + ".jpg";
        final StorageReference imageRef = storage.getReference(path);
        imageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference postsRef = database.getReference("Posts");
                DatabaseReference newPostRef = postsRef.push();
                newPostRef.setValue(new Post(currentUser.getUid(), uri.toString(),
                        description.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PhotoPreviewActivity.this, "Success",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PhotoPreviewActivity.this,
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Publish(View view) {
        uploadImage();
        finish();
        Intent intent = new Intent(PhotoPreviewActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public static class Post {
        public String author;
        public String url;
        public Object timestamp;
        public String description;
        public int likeCount = 0;
        public Map<String, Boolean> likes = new HashMap<>();

        public Post(String author, String url, String description) {
            this.author = author;
            this.url = url;
            this.description = description;
            this.timestamp = ServerValue.TIMESTAMP;
        }

        public Object getTimestamp() {
            return timestamp;
        }

        public Post() {

        }
    }
}