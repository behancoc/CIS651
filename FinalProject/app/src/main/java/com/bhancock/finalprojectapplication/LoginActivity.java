package com.bhancock.finalprojectapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bhancock.finalprojectapplication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Date;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int RC_SIGN_IN = 2002;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser mFirebaseUser;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mDisplayNameEditText;
    private EditText mPhoneNumberEditText;

    Button mSignUpButton;
    Button mResetPasswordButton;
    Button mSendEmailVerificationButton;
    Button mLoginButton;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "onCreate() called ");


        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();




        Log.d(TAG, "mFirebaseUser " + mFirebaseUser);

        mEmailEditText = findViewById(R.id.email);
        mPasswordEditText = findViewById(R.id.password);
        mDisplayNameEditText = findViewById(R.id.display_name);
        mPhoneNumberEditText = findViewById(R.id.phone_number);

        mSignUpButton = findViewById(R.id.signup_button);
        mResetPasswordButton = findViewById(R.id.reset_password_button);
        mSendEmailVerificationButton = findViewById(R.id.resend_email_verification_button);
        mLoginButton = findViewById(R.id.login_button);

        mEmailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d(TAG, "Action Done clicked!");
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    mEmailEditText.setCursorVisible(false);
                    return true;
                }
                return false;
            }
        });

        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d(TAG, "Action Done clicked!");
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    mPasswordEditText.setCursorVisible(false);
                    return true;
                }
                return false;
            }
        });

        mDisplayNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d(TAG, "Action Done clicked!");
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    mDisplayNameEditText.setCursorVisible(false);
                    return true;
                }
                return false;
            }
        });


        mPhoneNumberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d(TAG, "Action Done clicked!");
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    mPhoneNumberEditText.setCursorVisible(false);
                    return true;
                }
                return false;
            }
        });



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

                final String emailAddress = mEmailEditText.getText().toString();
                final String userName = mDisplayNameEditText.getText().toString();
                final String phoneNumber = mPhoneNumberEditText.getText().toString();


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
                                                saveUserDataToDB(userName, emailAddress, mFirebaseUser.getUid(), phoneNumber);
                                                updateUI();
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
                    return;
                }
                try {
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
                            updateUI();
                        }
                    });

                } catch (IllegalArgumentException e) {
                    Log.d(TAG, e.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Your email address is not known", Toast.LENGTH_SHORT).show();
                }

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

                try {
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

                } catch (NullPointerException e) {
                    Log.d(TAG, e.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Please enter an email address", Toast.LENGTH_SHORT).show();
                }


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

    @Override
    protected void onStart() {
        super.onStart();

    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }



    private void updateUI() {
        Log.d(TAG, "updateUI() called...");
        Log.d(TAG, "mFirebaseUser value: " + mFirebaseUser);
        if (mFirebaseUser != null) {
            findViewById(R.id.display_name).setVisibility(View.GONE);
            findViewById(R.id.phone_number).setVisibility(View.GONE);
            mSignUpButton.setVisibility(View.GONE);
        }
    }

    private void saveUserDataToDB(String username, String email, String userID, String phoneNumber) {
        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setUserId(userID);
        user.setPhone(phoneNumber);

        FirebaseFirestoreSettings firebaseFirestoreSettings = new FirebaseFirestoreSettings.Builder().build();

        firebaseFirestore.setFirestoreSettings(firebaseFirestoreSettings);

        DocumentReference documentReference = firebaseFirestore.collection("User")
                .document(mFirebaseUser.getUid());

        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Check Firestore... a new document should be there...");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.getLocalizedMessage();
                e.printStackTrace();
                Log.d(TAG, "That didn't go as intended....");
            }
        });

        Log.d(TAG, "New User should have been added to the database");
    }

    @Override
    public void onClick(View v) {

    }
}