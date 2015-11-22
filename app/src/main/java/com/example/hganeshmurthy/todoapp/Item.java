package com.example.hganeshmurthy.todoapp;

/**
 * Created by hganeshmurthy on 11/19/15.
 */
public class Item {
    private long id;
    private String item;

    public long getId() {
        return id;
    }

    public  Item(long id, String item)
    {
        this.id = id;
        this.item = item;
    }

    public  Item()
    {
        id = 0 ;
        item="";
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

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return item;
    }
} 
