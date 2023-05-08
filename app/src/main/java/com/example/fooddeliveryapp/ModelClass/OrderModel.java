package com.example.fooddeliveryapp.ModelClass;

public class OrderModel {
    private String item_pic;
    private String item_name,payment;
    private String price;

    public OrderModel(String item_pic, String item_name, String price,String payment) {
        this.item_pic = item_pic;
        this.item_name = item_name;
        this.price = price;
        this.payment = payment;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getItem_pic() {
        return item_pic;
    }

    public void setItem_pic(String item_pic) {
        this.item_pic = item_pic;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
