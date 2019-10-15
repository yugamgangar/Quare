package com.tyitproject.quare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class HeartrateMainActivity extends AppCompatActivity {

    Button buttonhrEnter, buttonHrGraph;
    EditText beatsperminute;
    private double bpm;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static int counter=0;
    DatabaseReference fReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartrate_main);
        buttonhrEnter = findViewById(R.id.buttonhrEnter);
        buttonHrGraph = findViewById(R.id.buttonHrGraph);
        beatsperminute = findViewById(R.id.beatsperminute);
        fReference = FirebaseDatabase.getInstance().getReference("Heartrate Data").child(Objects.requireNonNull(mAuth.getUid()));

        buttonhrEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (beatsperminute.getText().toString().isEmpty()) {
                    beatsperminute.setError("Invalid input");
                    beatsperminute.requestFocus();
                    return;
                }
                bpm = Double.parseDouble(beatsperminute.getText().toString());
                String newEntry = beatsperminute.getText().toString();
                AddData(newEntry);
            }
        });

        buttonHrGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HeartrateMainActivity.this, HeartrateListviewActivity.class);
                startActivity(intent);
            }
        });

    }
    public void onBackPressed() {
        Intent intent = new Intent(HeartrateMainActivity.this, HealthDataActivity.class);
        startActivity(intent);
        HeartrateMainActivity.this.finish();
    }

    public void AddData(String newEntry) {
        String time, date;
        SimpleDateFormat simpledateformat;

        Calendar c = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("HH:mm a");
        time = simpledateformat.format(c.getTime());
        date = "" + c.get(Calendar.DAY_OF_MONTH);
        simpledateformat = new SimpleDateFormat("MMMM");
        date = date + " " + simpledateformat.format(c.getTime());
        date = date + ", " + c.get(Calendar.YEAR);

        Heartrate heartrate = new Heartrate(newEntry, date, time);
        DatabaseReference a = fReference.child(String.valueOf(counter));
        while(a.getKey().equals(String.valueOf(counter))){
            counter++;}
        FirebaseDatabase.getInstance().getReference("Heartrate Data")
                .child(Objects.requireNonNull(mAuth.getUid()))
                .child(String.valueOf(counter))
                .setValue(heartrate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(HeartrateMainActivity.this, "Data succesfully recorded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HeartrateMainActivity.this, "Data not recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}