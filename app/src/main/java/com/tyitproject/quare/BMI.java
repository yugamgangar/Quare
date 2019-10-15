package com.tyitproject.quare;

public class BMI {
    private String date, time, bmi_data;

    public BMI(String bmi_data, String date, String time) {

        this.bmi_data = bmi_data;
        this.date = date;
        this.time = time;
    }

    public BMI(){

    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setBmi_data(String bmi_data) {
        this.bmi_data = bmi_data;
    }

    public String getDate() {
        return date;
    }


    public String getTime() {
        return time;
    }

    public String getBmi_data() {
        return bmi_data;
    }
}
