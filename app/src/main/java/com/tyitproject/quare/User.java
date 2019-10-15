package com.tyitproject.quare;

public class User {
    public String name, email, phone, bloodgroup, dob;

    public User(){

    }

    public User(String name, String email, String phone, String bloodgroup, String dob) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bloodgroup = bloodgroup;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public String getDob() {
        return dob;
    }
}