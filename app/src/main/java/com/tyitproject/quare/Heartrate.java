package com.tyitproject.quare;

public class Heartrate {
    private String date, time, heartrate_data;

    public Heartrate(String heartrate_data, String date, String time) {

        this.heartrate_data = heartrate_data;
        this.date = date;
        this.time = time;
    }

    public Heartrate(){

    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getHeartrate_data() {
        return heartrate_data;
    }
}
