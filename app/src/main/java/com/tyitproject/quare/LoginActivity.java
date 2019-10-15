package com.tyitproject.quare;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {

    private TextView mSignUp, forgotPassword;
    private Button mLogin;
    private EditText uId, uPassword;
    SignInButton gButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;
    public final static int RC_SIGN_IN = 2;
    CoordinatorLayout coordinatorLayout;
    FirebaseAuth.AuthStateListener mAuthListener;
    private String dailogText;

    @Override
    protected void onStart() {
        super.onStart();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null)   {
                    if(mAuth.getCurrentUser() != null)   {
                    startActivity(new Intent(LoginActivity.this, HealthDataActivity.class));
                    LoginActivity.this.finish();
                   }
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);
        if(mAuth.getCurrentUser() != null)   {
                 startActivity(new Intent(LoginActivity.this, HealthDataActivity.class));
             }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gButton = findViewById(R.id.google_button);
        mSignUp = findViewById(R.id.signUp);
        mLogin = findViewById(R.id.login);
        uId = findViewById(R.id.sign_in_email);
        uPassword = findViewById(R.id.sign_in_password);
        forgotPassword = findViewById(R.id.sign_in_forgot_pass_tv);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        gButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseLogin();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomToast("fp clicked");
                passwordResetDailog();
            }
        });

        // GOOGLE SIGN-IN
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    // --------------------- GOOGLE SIGN_IN ---------------------------------
    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);

            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            LoginActivity.this.finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Snackbar.make(coordinatorLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });
    }

    //------------------------------------- FIREBASE LOGIN ----------------------------------------

    private void firebaseLogin() {
        final String email = uId.getText().toString().trim();
        String password = uPassword.getText().toString().trim();

        if (email.isEmpty()) {
            uId.setError(getString(R.string.input_error_email));
            uId.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            uId.setError(getString(R.string.input_error_email_invalid));
            uId.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            uPassword.setError(getString(R.string.input_error_password));
            uPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            uPassword.setError(getString(R.string.input_error_password_length));
            uPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            showCustomToast("Login Successful");
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            LoginActivity.this.finish();
                        }else{
                            progressBar.setVisibility(View.GONE);
                            Snackbar.make(coordinatorLayout, "Login Failed.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //----------------------------------- CUSTOM TOAST ------------------------------------
    private void showCustomToast(String str) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(str);


        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    //---------------------------------- PASSWORD RESET DAILOG ----------------------------------------
    private void passwordResetDailog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Reset Password");
        builder.setMessage("Please enter your email address");
    // Set up the input
        final EditText input = new EditText(LoginActivity.this);
    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Email");
        builder.setView(input);

    // Set up the buttons
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dailogText = input.getText().toString();
                if (dailogText.isEmpty()) {
                    uId.setError(getString(R.string.input_error_email));
                    uId.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(dailogText).matches()) {
                    uId.setError(getString(R.string.input_error_email_invalid));
                    uId.requestFocus();
                    return;
                }
                mAuth.sendPasswordResetEmail(dailogText);
                Toast.makeText(LoginActivity.this,"Password reset email sent",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}