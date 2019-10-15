package com.tyitproject.quare;

public class BloodPressure {
    private String date, time, systol, diastol;

    public BloodPressure(String systol, String diastol,  String date, String time) {

        this.systol = systol;
        this.diastol = diastol;
        this.date = date;
        this.time = time;
    }

    public BloodPressure(){

    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSystol() {
        return systol;
    }

    public String getDiastol() {
        return diastol;
    }
}
