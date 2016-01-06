package com.example.wilsond.moveapplication;

/**
 * Created by WILSOND on 12/29/15.
 */
public class Item {
    private String name;
    private int amount;
    public Item (String name, int amount) {
        this.name = name;
        this.amount = amount;
    }
    public int getAmount(){
        return this.amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
