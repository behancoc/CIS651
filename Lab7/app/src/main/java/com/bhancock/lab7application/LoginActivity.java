package com.bhancock.lab7application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mDisplayNameEditText;
    private EditText mPhoneNumberEditText;

    Button mSignUpButton;
    Button mResetPasswordButton;
    Button mSendEmailVerificationButton;
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mEmailEditText = findViewById(R.id.email);
        mPasswordEditText = findViewById(R.id.password);
        mDisplayNameEditText = findViewById(R.id.display_name);
        mPhoneNumberEditText = findViewById(R.id.phone_number);

        mSignUpButton = findViewById(R.id.signup_button);
        mResetPasswordButton = findViewById(R.id.reset_password_button);
        mSendEmailVerificationButton = findViewById(R.id.resend_email_verification_button);
        mLoginButton = findViewById(R.id.login_button);



        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEmailEditText.getText().toString().equals("")
                        || mPasswordEditText.getText().toString().equals("")
                        || mPhoneNumberEditText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please provide all necessary information",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mFirebaseAuth.createUserWithEmailAndPassword(
                        mEmailEditText.getText().toString(),
                        mPasswordEditText.getText().toString()).addOnSuccessListener(
                                LoginActivity.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mFirebaseUser = authResult.getUser();
                        mFirebaseUser.sendEmailVerification().addOnSuccessListener(
                                LoginActivity.this, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),
                                        "SignUp successful.  Verification email sent!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),
                                        e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        mResetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEmailEditText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your email address", Toast.LENGTH_SHORT).show();
                }
                mFirebaseAuth.sendPasswordResetEmail(mEmailEditText.getText().toString())
                        .addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(LoginActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(LoginActivity.this,
                                "Email sent!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mSendEmailVerificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFirebaseAuth == null) {
                    Toast.makeText(getApplicationContext(),
                            "Please login to resend verification email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mFirebaseUser.sendEmailVerification()
                        .addOnSuccessListener(LoginActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),
                                "Verification email sent!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEmailEditText.getText().toString().equals("") ||
                        mPasswordEditText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please provide the necessary information",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mFirebaseAuth.signInWithEmailAndPassword(mEmailEditText.getText().toString(),
                        mPasswordEditText.getText().toString())
                        .addOnSuccessListener(LoginActivity.this,
                                new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mFirebaseUser = authResult.getUser();

                        if(mFirebaseUser.isEmailVerified()) {
                            Toast.makeText(getApplicationContext(), "Login Successful",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,
                                    HomeActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Please verify your email and login again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        updateUI();
    }


    private void updateUI() {
        if (mFirebaseUser != null) {
            findViewById(R.id.display_name).setVisibility(View.GONE);
            findViewById(R.id.phone_number).setVisibility(View.GONE);
            mSignUpButton.setVisibility(View.GONE);
        }
    }

    private void saveUserDataToDB() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userReference = firebaseDatabase.getReference("Users");
        userReference.child(mFirebaseUser.getUid())
                .setValue(new User(mDisplayNameEditText.getText().toString(),
                                   mEmailEditText.getText().toString(),
                                   mPhoneNumberEditText.getText().toString()));
    }
}
