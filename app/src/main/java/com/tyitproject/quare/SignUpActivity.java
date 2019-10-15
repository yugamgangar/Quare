package com.tyitproject.quare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private TextView mSignIn, uDobPicker, uDob;
    private Button mSignUp;
    private EditText uName, uId, uPhone, uPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private int mYear, mMonth, mDay;
    private Spinner spinner;
    private String udob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignIn = findViewById(R.id.signIn);
        uDobPicker = findViewById(R.id.dob_date_picker);
        uDob = findViewById(R.id.dob_display);
        mSignUp = findViewById(R.id.sign_up_button);
        uName = findViewById(R.id.sign_up_name);
        uId = findViewById(R.id.sign_up_email);
        uPhone = findViewById(R.id.sign_up_phone);
        uPassword = findViewById(R.id.sign_up_password);
        spinner = findViewById(R.id.blood_group_picker);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        uDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDOB();
        }
        });

     //   spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    private void getDOB()
    {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        udob = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        uDob.setText(":   "+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        uDob.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.black));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {

            //handle the already login user
        }
    }

    private void registerUser() {
        final String name = uName.getText().toString().trim();
        final String email = uId.getText().toString().trim();
        String password = uPassword.getText().toString().trim();
        final String phone = uPhone.getText().toString().trim();
        final String uBloodGroup = spinner.getSelectedItem().toString().trim();

        if (name.isEmpty()) {
            uName.setError(getString(R.string.input_error_name));
            uName.requestFocus();
            return;
        }

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

        if (phone.isEmpty()) {
            uPhone.setError(getString(R.string.input_error_phone));
            uPhone.requestFocus();
            return;
        }

        if (phone.length() != 10) {
            uPhone.setError(getString(R.string.input_error_phone_invalid));
            uPhone.requestFocus();
            return;
        }

        if (spinner.getSelectedItemId()==0){
            Toast.makeText(SignUpActivity.this,"Select Valid Blood Group",Toast.LENGTH_SHORT);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(
                                    name,
                                    email,
                                    phone,
                                    uBloodGroup,
                                    udob
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        showCustomToast(getString(R.string.registration_success));
                                        startActivity(new Intent(SignUpActivity.this, HealthDataActivity.class));
                                        SignUpActivity.this.finish();
                                    } else {
                                        showCustomToast("Failed to enter user data");
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void showCustomToast(String string) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(string);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
