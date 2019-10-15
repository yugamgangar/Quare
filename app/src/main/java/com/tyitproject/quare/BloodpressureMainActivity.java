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

public class BloodpressureMainActivity extends AppCompatActivity {

    Button buttonBpEnter, buttonBpGraph;
    EditText systolic, diastolic;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static int counter=0;
    DatabaseReference fReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodpressure_main);

        buttonBpEnter = findViewById(R.id.buttonhbpEnter);
        buttonBpGraph = findViewById(R.id.buttonbpGraph);
        systolic = findViewById(R.id.systolic);
        diastolic = findViewById(R.id.diastolic);
        fReference = FirebaseDatabase.getInstance().getReference("Bloodpressure Data").child(Objects.requireNonNull(mAuth.getUid()));

        buttonBpEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (systolic.getText().toString().isEmpty()) {
                    systolic.setError("Invalid input");
                    systolic.requestFocus();
                    return;
                }
                if (diastolic.getText().toString().isEmpty()) {
                    diastolic.setError("Invalid input");
                    diastolic.requestFocus();
                    return;
                }

                String systo = systolic.getText().toString();
                String diasto = diastolic.getText().toString();

                AddData(systo, diasto);

            }

        });

        buttonBpGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BloodpressureMainActivity.this, BloodpressureListviewActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onBackPressed() {
        Intent intent = new Intent(BloodpressureMainActivity.this, HealthDataActivity.class);
        startActivity(intent);
        BloodpressureMainActivity.this.finish();
    }

    public void AddData(String systo, String diasto) {
        String time, date;
        SimpleDateFormat simpledateformat;

        Calendar c = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("HH:mm a");
        time = simpledateformat.format(c.getTime());
        date = "" + c.get(Calendar.DAY_OF_MONTH);
        simpledateformat = new SimpleDateFormat("MMMM");
        date = date + " " + simpledateformat.format(c.getTime());
        date = date + ", " + c.get(Calendar.YEAR);

        BloodPressure bloodPressure = new BloodPressure(systo, diasto, date, time);

        DatabaseReference a = fReference.child(String.valueOf(counter));
        while(a.getKey().equals(String.valueOf(counter))){
            counter++;}
        FirebaseDatabase.getInstance().getReference("Bloodpressure Data")
                .child(Objects.requireNonNull(mAuth.getUid()))
                .child(String.valueOf(counter))
                .setValue(bloodPressure).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //  progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(BloodpressureMainActivity.this, "Data succesfully recorded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BloodpressureMainActivity.this, "Data not recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}