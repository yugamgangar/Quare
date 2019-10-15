package com.tyitproject.quare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    DatabaseReference databaseRef;
    String firebaseUid;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    TextView name, phone, dob, bloodgroup, uid;
    Button logout_button;
    private BottomNavigationView mBtmView;
    private int mMenuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mBtmView = findViewById(R.id.bottom_navigation_view);
        mBtmView.setSelectedItemId(R.id.navigation_account);
        mBtmView.setOnNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        databaseRef = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUid = user.getUid();

        name = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        dob = findViewById(R.id.dob);
        uid = findViewById(R.id.mail_id);
        bloodgroup = findViewById(R.id.blood_group);
        logout_button = findViewById(R.id.logout_button);

        //GETTING GOOGLE CLIENT ID FOR SIGNING OUT
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogout();
            }
        });
    }

    @Override
    protected  void onStart(){
        super.onStart();

        //================================= FIREBASE FETCHING USER DATA ==============================

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    Log.d("Check","firebaseid::"+firebaseUid+" | userSnapshot child::"+userSnapshot.getKey());
                    if(userSnapshot.getKey().equals(firebaseUid)){
                        User userObject = userSnapshot.getValue(User.class);
                        //  Toast.makeText(com.tyitproject.quare.ProfileActivity.this, user.getBmi_data(), Toast.LENGTH_LONG).show();
                        name.setText(userObject.getName());
                        phone.setText("(+91) "+userObject.getPhone());
                        uid.setText(userObject.getEmail());
                        dob.setText(userObject.getDob());
                        bloodgroup.setText(userObject.getBloodgroup());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void userLogout() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(mAuth.getCurrentUser() == null) {
                            Toast.makeText(ProfileActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                            finish();
                        }
                        else{
                            mAuth.signOut();
                            if(mAuth.getCurrentUser() == null){
                                Toast.makeText(ProfileActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                                finish();
                            }else {
                                Toast.makeText(ProfileActivity.this, "Log out unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    //======================================= NAVIGATION ===========================================

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
        mMenuId = item.getItemId();
        for (int i = 0; i < mBtmView.getMenu().size(); i++) {
            MenuItem menuItem = mBtmView.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == item.getItemId();
            menuItem.setChecked(isChecked);

            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                    startActivity(intent);
                    ProfileActivity.this.finish();
                    break;
                }
                case R.id.navigation_healthdata: {
                    Intent intent = new Intent(ProfileActivity.this, HealthDataActivity.class);
                    startActivity(intent);
                    ProfileActivity.this.finish();
                    break;
                }
                case R.id.navigation_account: {
                    break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
