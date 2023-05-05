package com.example.fooddeliveryapp.ModelClass;

public class OrderModel {
    public int item_pic;

    public String item_name;

    public String price;

    public OrderModel(int item_pic, String item_name, String price) {
        this.item_pic = item_pic;
        this.item_name = item_name;
        this.price = price;
    }

    public int getItem_pic() {
        return item_pic;
    }

    public void setItem_pic(int item_pic) {
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
