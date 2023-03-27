package com.example.fooddeliveryapp.ModelClass;

public class    MainModel {

    private int img;
    private String imgname;

    public MainModel(int img, String imgname) {
        this.img = img;
        this.imgname = imgname;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }
}
