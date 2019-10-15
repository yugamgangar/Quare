package com.tyitproject.quare;

public class Glucose {
    private String date, time, glucose_data, mealtime_data;

    public Glucose(String glucose_data, String mealtime_data, String date, String time) {

        this.glucose_data = glucose_data;
        this.mealtime_data = mealtime_data;
        this.date = date;
        this.time = time;
    }

    public Glucose(){

    }

    public String getDate() {
        return date;
    }


    public String getTime() {
        return time;
    }

    public String getGlucose_data() {
        return glucose_data;
    }

    public String getMealtime_data() {
        return mealtime_data;
    }
}
