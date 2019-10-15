package com.tyitproject.quare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HeartrateListviewActivity extends AppCompatActivity {

    private ListView listview;
    private ArrayList<Heartrate> heartrate_list;
    DatabaseReference databaseRef;
    String firebaseUid;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartrate_listview);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        firebaseUid = user.getUid();
        listview = findViewById(R.id.listView);
        databaseRef = FirebaseDatabase.getInstance().getReference("Heartrate Data");
        heartrate_list = new ArrayList<>();
    }

    @Override
    protected  void onStart(){
        super.onStart();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot bmiSnapshot : dataSnapshot.getChildren()) {
                    if (bmiSnapshot.getKey().equals(firebaseUid)) {
                        for (DataSnapshot heartrateSnapshot : bmiSnapshot.getChildren()) {
                            Heartrate heartrate = heartrateSnapshot.getValue(Heartrate.class);
                            heartrate_list.add(heartrate);
                            HeartrateListAdapter heartrateListAdapter = new HeartrateListAdapter(HeartrateListviewActivity.this, R.layout.activity_heartrate_listview, heartrate_list);
                            listview.setAdapter(heartrateListAdapter);
                        }
                    }
                }
            }

        @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
