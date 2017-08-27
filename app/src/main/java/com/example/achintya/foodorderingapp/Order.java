package com.example.achintya.foodorderingapp;

/**
 * Created by Achintya on 7/4/2017.
 */

public class Order {

    String username, itemname;

    public Order(){

    }

    public Order(String username, String itemname) {
        this.username = username;
        this.itemname = itemname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}
