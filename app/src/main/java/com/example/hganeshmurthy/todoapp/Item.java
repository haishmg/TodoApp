package com.example.hganeshmurthy.todoapp;

/**
 * Created by hganeshmurthy on 11/19/15.
 */
public class Item {
    private long id;
    private String item;
    private String priority;
    private String date;

    public long getId() {
        return id;
    }

    public  Item(long id, String item, String priority, String date)
    {
        this.id = id;
        this.item = item;
        this.priority = priority;
        this.date = date;
    }

    public  Item()
    {
        id = 0 ;
        item="";
        priority="low";
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setPriority(String priority) {this.priority = priority;}

    public String getPriority () {return this.priority;}

    public void setDate(String date) {this.date = date;}

    public String getDate () {return this.date;}

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return item;
    }
} 
