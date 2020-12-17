package com.example.capstone2_v1.adapter;

public class HorizontalData {

    private int img;
    private String text;

    public HorizontalData(int img, String text){
        this.img = img;
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public int getImg() {
        return this.img;
    }
}
