package com.example.capstone2_v1.adapter;

public class HorizontalData {

    private String imgPath;
    private String text;

    public HorizontalData(String imgPath, String text){
        this.imgPath = imgPath;
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
