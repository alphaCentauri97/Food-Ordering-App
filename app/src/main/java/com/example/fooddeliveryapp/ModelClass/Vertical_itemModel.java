package com.example.fooddeliveryapp.ModelClass;

public class Vertical_itemModel {

    private String productname,priceRs,date;
    private int productimg,clockimg;


    public Vertical_itemModel(String productname, String priceRs, int productimg) {
        this.productname = productname;
        this.priceRs = priceRs;
        this.productimg = productimg;
    }

    public Vertical_itemModel(String productname, String priceRs, String date, int productimg, int clockimg) {
        this.productname = productname;
        this.priceRs = priceRs;
        this.date = date;

        this.productimg = productimg;
        this.clockimg = clockimg;
    }


    public String getProductname() {
        return productname;
    }

    public String getPriceRs() {
        return priceRs;
    }

    public String getDate() {
        return date;
    }


    public int getProductimg() {
        return productimg;
    }

    public int getClockimg() {
        return clockimg;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setPriceRs(String priceRs) {
        this.priceRs = priceRs;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setProductimg(int productimg) {
        this.productimg = productimg;
    }

    public void setClockimg(int clockimg) {
        this.clockimg = clockimg;
    }



}
