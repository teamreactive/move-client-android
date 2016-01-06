package com.example.wilsond.moveapplication;

import java.util.Date;

/**
 * Created by WILSOND on 12/28/15.
 */
public class Order {
    Date date;
    Place place;
    long price;

    public Order(Date date,Place place, long price ){
        this.date = date;
        this.place = place;
        this.price = price;
    }

    public String itemsContToString(){
        return new String(" 1 Producto");
    }
    public String dateToString(){
        return date.toString();
    }
}
