package com.example.blogs_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginForm extends AppCompatActivity {
    EditText new_email, new_password;
    Button register, Login;
    SignInButton googlelogin;
    CircleImageView google, facebook;
    ProgressBar p;
    FirebaseAuth auth;
    GoogleSignInClient signInClient;
    static int google_req = 1;
    CallbackManager mCallbackManager;
    LoginButton loginButton;

    void calling() {
        register = findViewById(R.id.new_register);
        p = findViewById(R.id.progressBar2);
        new_email = findViewById(R.id.My_email);
        new_password = findViewById(R.id.My_password);
        Login = findViewById(R.id.Login);
        googlelogin = findViewById(R.id.googlesing_in);


    }

    void setRegister() {

        p.setVisibility(View.INVISIBLE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                p.setVisibility(View.VISIBLE);
                register.setVisibility(View.INVISIBLE);
                Login.setVisibility(View.INVISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(2000);

                            startActivity(new Intent(LoginForm.this, RegisterationForm.class));

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    void login() {


        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (new_email.getText().toString().trim().isEmpty()) {
                    new_email.setError("Enter your Email");
                } else if (new_password.getText().toString().trim().isEmpty()) {
                    new_password.setError("Enter your Email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(new_email.getText().toString()).matches()) {
                    new_email.setError("Enter correct email");
                } else {
                    successsfull();
                    auth.signInWithEmailAndPassword(new_email.getText().toString(), new_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "successfull", Toast.LENGTH_SHORT).show();
                                go_to_homePage();
                            } else {
                                failed();
                                Toast.makeText(getApplicationContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }
        });

    }

    void google_sing() {

        GoogleSignInOptions sign = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, sign);
        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successsfull();
                sign_req();
            }
        });
    }
/*
    void face_sing() {
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.loginface);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginForm.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginForm.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI_face(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginForm.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI_face(null);
                        }
                    }
                });

    }
*/


    private void sign_req() {
        startActivityForResult(new Intent(signInClient.getSignInIntent()), google_req);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        auth = FirebaseAuth.getInstance();
        //  FacebookSdk.sdkInitialize(LoginForm.this);
        calling();
        login();
        setRegister();
        google_sing();

        /*
        try {
            face_sing();
        } catch (Exception e) {
            Toast.makeText(LoginForm.this, e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            go_to_homePage();
        }
    }

    void go_to_homePage() {
        startActivity(new Intent(LoginForm.this, MyhomeActivity.class));
        finish();


    }

    @Override
    protected void onPause() {
        super.onPause();
       failed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == google_req) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            hand_sing_in(task);
        }
        /*
        else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        */
    }

    private void hand_sing_in(Task<GoogleSignInAccount> task) {
        try {

            GoogleSignInAccount signInAccount = task.getResult(ApiException.class);
            // Toast.makeText(LoginForm.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(signInAccount);

        } catch (Exception e) {
            // Toast.makeText(LoginForm.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount signInAccount) {

        if (signInAccount != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
            auth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginForm.this, "Successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Toast.makeText(LoginForm.this, "Failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        } else {

            Toast.makeText(LoginForm.this, "acc failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser account) {
        if (account != null) {
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            String personId = account.getUid();
            Uri personPhoto = account.getPhotoUrl();
            Push_data(new data(personName, "", personEmail, personPhoto.toString(), personId));

        }
    }

    private void updateUI_face(FirebaseUser account) {
        if (account != null) {
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            String personId = account.getUid();
            Uri personPhoto = account.getPhotoUrl();
            Push_data(new data(personName, "", personEmail,
                    personPhoto.toString()
                    , personId));

        }
    }

    private void Push_data(data data) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("user_info").child(data.getUserid()).setValue(data);
        go_to_homePage();
    }

    void successsfull() {
        p.setVisibility(View.VISIBLE);
        register.setVisibility(View.INVISIBLE);
        Login.setVisibility(View.INVISIBLE);
    }

    void failed() {
        p.setVisibility(View.INVISIBLE);
        register.setVisibility(View.VISIBLE);
        Login.setVisibility(View.VISIBLE);
    }
}