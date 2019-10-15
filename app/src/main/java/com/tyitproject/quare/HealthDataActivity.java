package com.tyitproject.quare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class HealthDataActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBtmView;
    private int mMenuId;
    ImageView bmi, heartrate, bloodpressure, glucose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_data);
        mBtmView = findViewById(R.id.bottom_navigation_view);
        mBtmView.setSelectedItemId(R.id.navigation_healthdata);
        mBtmView.setOnNavigationItemSelectedListener(this);

        bmi = findViewById(R.id.bmi_image);
        heartrate = findViewById(R.id.heart_rate_image);
        bloodpressure = findViewById(R.id.blood_pressure_image);
        glucose = findViewById(R.id.glucose_image);

        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthDataActivity.this, BmiMainActivity.class);
                startActivity(intent);
                HealthDataActivity.this.finish();
            }});

        heartrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthDataActivity.this, HeartrateMainActivity.class);
                startActivity(intent);
                HealthDataActivity.this.finish();
            }});

        bloodpressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthDataActivity.this, BloodpressureMainActivity.class);
                startActivity(intent);
                HealthDataActivity.this.finish();
            }});

        glucose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthDataActivity.this, GlucoseMainActivity.class);
                startActivity(intent);
                HealthDataActivity.this.finish();
            }});
    }

    @Override
    public void onBackPressed() {
        HealthDataActivity.this.finishAffinity();
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
                    Intent intent = new Intent(HealthDataActivity.this, HomeActivity.class);
                    startActivity(intent);
                    HealthDataActivity.this.finish();
                    break;
                }
                case R.id.navigation_healthdata: {
                    break;
                }
                case R.id.navigation_account: {
                    Intent intent = new Intent(HealthDataActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    HealthDataActivity.this.finish();
                    break;
                }
            }
            return true;
        }
        return false;
    }
}
