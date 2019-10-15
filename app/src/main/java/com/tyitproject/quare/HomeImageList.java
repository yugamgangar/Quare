package com.tyitproject.quare;

public class HomeImageList {

    private String leftlist_name, rightlistName;
    private int leftlist_id, rightlist_id;

    public HomeImageList(String leftlist_name, int leftlist_id, String rightlistName, int rightlist_id) {
        this.leftlist_name = leftlist_name;
        this.rightlistName = rightlistName;
        this.leftlist_id = leftlist_id;
        this.rightlist_id = rightlist_id;
    }

    public String getLeftlist_name() {
        return leftlist_name;
    }

    public String getRightlistName() {
        return rightlistName;
    }

    public int getLeftlist_id() {
        return leftlist_id;
    }

    public int getRightlist_id() {
        return rightlist_id;
    }

}
