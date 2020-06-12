package com.bhancock.finalprojectapplication.ui.camera;

import androidx.activity.OnBackPressedCallback;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhancock.finalprojectapplication.HomeActivity;
import com.bhancock.finalprojectapplication.PermissionUtils;
import com.bhancock.finalprojectapplication.PhotoPreviewActivity;
import com.bhancock.finalprojectapplication.R;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {

    private static final String TAG = CameraFragment.class.getSimpleName();

    private CameraViewModel cameraViewModel;
    private static final int REQUEST_FOR_CAMERA=0011;
    private Uri imageUri = null;


    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.d(TAG, "handleOnBackPressed called...");
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        cameraViewModel =
                ViewModelProviders.of(this).get(CameraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_camera, container, false);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cameraViewModel = ViewModelProviders.of(this).get(CameraViewModel.class);
        // TODO: Use the ViewModel

        uploadNewPhoto();
    }

    public void uploadNewPhoto() {
        checkPermissions();
    }


    public void takePhoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        Intent chooser=Intent.createChooser(intent,"Select a Camera App.");

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(chooser, REQUEST_FOR_CAMERA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_FOR_CAMERA && resultCode == RESULT_OK) {
            if(imageUri == null) {
                Toast.makeText(getActivity().getApplicationContext(), "Error taking photo.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(getContext(), PhotoPreviewActivity.class);
            intent.putExtra("uri", imageUri.toString());
            startActivity(intent);
            return;
        }
    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(getActivity().getBaseContext(), android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "We need permission to access your camera and photo.",
                    Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_FOR_CAMERA);
        } else {
            takePhoto();
        }
    }


}