package com.tyitproject.quare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class GlucoseMainActivity extends AppCompatActivity {

    Button buttonGlucoseEnter, buttonGlucoseGraph;
    EditText bloodglucose;
    Spinner mealtime;
    private String meal_t;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static int counter=0;
    DatabaseReference fReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_glucose);

        buttonGlucoseEnter = findViewById(R.id.buttonGlucoseEnter);
        buttonGlucoseGraph = findViewById(R.id.buttonGlucoseGraph);
        bloodglucose = findViewById(R.id.bloodglucose);
        mealtime = findViewById(R.id.mealtime);
        fReference = FirebaseDatabase.getInstance().getReference("Glucose Data").child(Objects.requireNonNull(mAuth.getUid()));

        buttonGlucoseEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bloodglucose.getText().toString().isEmpty()) {
                    bloodglucose.setError("Invalid input");
                    bloodglucose.requestFocus();
                    return;
                }

                meal_t = mealtime.getSelectedItem().toString();

                String gluco = bloodglucose.getText().toString();

                AddData(gluco, meal_t);

            }

        });

        buttonGlucoseGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlucoseMainActivity.this, GlucoseListViewActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onBackPressed() {
        Intent intent = new Intent(GlucoseMainActivity.this, HealthDataActivity.class);
        startActivity(intent);
        GlucoseMainActivity.this.finish();
    }

    public void AddData(String gluco, String mealt) {
        String time, date;
        SimpleDateFormat simpledateformat;

        Calendar c = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("HH:mm a");
        time = simpledateformat.format(c.getTime());
        date = "" + c.get(Calendar.DAY_OF_MONTH);
        simpledateformat = new SimpleDateFormat("MMMM");
        date = date + " " + simpledateformat.format(c.getTime());
        date = date + ", " + c.get(Calendar.YEAR);

        Glucose glucose = new Glucose(gluco, mealt, date, time);
        DatabaseReference a = fReference.child(String.valueOf(counter));
        while(a.getKey().equals(String.valueOf(counter))){
            counter++;}
        FirebaseDatabase.getInstance().getReference("Glucose Data")
                .child(Objects.requireNonNull(mAuth.getUid()))
                .child(String.valueOf(counter))
                .setValue(glucose).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //  progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(GlucoseMainActivity.this, "Data succesfully recorded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GlucoseMainActivity.this, "Data not recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}