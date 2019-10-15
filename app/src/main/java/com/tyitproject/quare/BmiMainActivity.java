package com.tyitproject.quare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class BmiMainActivity extends AppCompatActivity {

    Button buttonCalculate, buttonGraph;
    EditText weight, height;
    TextView showResult, showBMI, showImpBMI;
    private double kg, m;
    private DecimalFormat TWO_DECIMAL_PLACES = new DecimalFormat(".##");
    MetricFormula metricFormula;
    ImperialFormula imperialFormula;
    BMICategory bmiCategory = new BMICategory();
    private static int counter = 0;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference fReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_bmi);

        buttonCalculate = findViewById(R.id.buttonbmiCalculate);
        buttonGraph = findViewById(R.id.buttonGraph);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        showResult = findViewById(R.id.result);
        showBMI = findViewById(R.id.showBMI);
        showImpBMI = findViewById(R.id.showImpBMI);
        fReference = FirebaseDatabase.getInstance().getReference("BMI Data").child(Objects.requireNonNull(mAuth.getUid()));

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (weight.getText().toString().isEmpty()) {
                    weight.setError("Invalid input");
                    weight.requestFocus();
                    return;
                }

                if (height.getText().toString().isEmpty()) {
                    height.setError("Invalid input");
                    height.requestFocus();
                    return;
                }

                kg = Double.parseDouble(weight.getText().toString());
                m = Double.parseDouble(height.getText().toString());

                metricFormula = new MetricFormula(kg, m);
                imperialFormula = new ImperialFormula(kg, m);

                showBMI.setText("BMI = " + String.valueOf(TWO_DECIMAL_PLACES.format(metricFormula.computeBMI(metricFormula.getInputKg(), metricFormula.getInputM()))));
                showImpBMI.setText("In imperial formula: " + String.valueOf(TWO_DECIMAL_PLACES.format(imperialFormula.computeBMI(imperialFormula.getInputKg(), imperialFormula.getInputM()))));
                showResult.setText(bmiCategory.getCategory(metricFormula.computeBMI(metricFormula.getInputKg(), metricFormula.getInputM())));

                String newEntry = String.valueOf(TWO_DECIMAL_PLACES.format(metricFormula.computeBMI(metricFormula.getInputKg(), metricFormula.getInputM())));
                AddData(newEntry);

            }

        });




        buttonGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BmiMainActivity.this, BmiListviewActivity.class);
                startActivity(intent);
                BmiMainActivity.this.finish();
            }
        });

    }

    public void onBackPressed() {
        Intent intent = new Intent(BmiMainActivity.this, HealthDataActivity.class);
        startActivity(intent);
        BmiMainActivity.this.finish();
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

        BMI bmi = new BMI(newEntry, date, time);

        DatabaseReference a = fReference.child(String.valueOf(counter));
        while (a.getKey().equals(String.valueOf(counter))) {
            counter++;
        }
        FirebaseDatabase.getInstance().getReference("BMI Data")
                .child(Objects.requireNonNull(mAuth.getUid()))
                .child(String.valueOf(counter))
                .setValue(bmi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(BmiMainActivity.this, "Data succesfully recorded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BmiMainActivity.this, "Data not recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}