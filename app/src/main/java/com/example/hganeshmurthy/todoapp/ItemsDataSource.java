package com.example.hganeshmurthy.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

public class ItemsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_ITEM,MySQLiteHelper.COLUMN_PRIORITY,MySQLiteHelper.COLUMN_DATE};

    public ItemsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createItem(Item Item) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ITEM, Item.getItem());
        values.put(MySQLiteHelper.COLUMN_ID, Item.getId());
        values.put(MySQLiteHelper.COLUMN_PRIORITY, Item.getPriority());
        values.put(MySQLiteHelper.COLUMN_DATE, Item.getDate());
        database.insert(MySQLiteHelper.TABLE_ITEMS, null,
                values);
    }

    public void deleteItem(long id) {
        System.out.println("Item deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_ITEMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public String getItemFromId(long id) {
        String item="";
        String q="SELECT "+MySQLiteHelper.COLUMN_ITEM+" FROM "+MySQLiteHelper.TABLE_ITEMS+" WHERE "+MySQLiteHelper.COLUMN_ID+"=" + id;
        Cursor  cursor = database.rawQuery(q, null);
        if (cursor != null &&  cursor.moveToFirst()) {
             item = cursor.getString(0);
        }
        cursor.close();
        return item;
    }

    public String getPriorityFromId(long id) {
        String priority="";
        String q="SELECT "+MySQLiteHelper.COLUMN_PRIORITY+" FROM "+MySQLiteHelper.TABLE_ITEMS+" WHERE "+MySQLiteHelper.COLUMN_ID+"=" + id;
        Cursor  cursor = database.rawQuery(q, null);
        if (cursor != null &&  cursor.moveToFirst()) {
            priority = cursor.getString(0);
        }
        cursor.close();
        return priority;
    }

    public String getDateFromId(long id) {
        String date="";
        String q="SELECT "+MySQLiteHelper.COLUMN_DATE+" FROM "+MySQLiteHelper.TABLE_ITEMS+" WHERE "+MySQLiteHelper.COLUMN_ID+"=" + id;
        Cursor  cursor = database.rawQuery(q, null);
        if (cursor != null &&  cursor.moveToFirst()) {
            date = cursor.getString(0);
        }
        cursor.close();
        return date;
    }

    public long getMaxId() {
        long id = 0 ;
        String q="SELECT max("+MySQLiteHelper.COLUMN_ID+") FROM "+MySQLiteHelper.TABLE_ITEMS;
        Cursor  cursor = database.rawQuery(q,null);
        cursor.moveToFirst();

        if (cursor != null && cursor.moveToFirst()) {
                id = cursor.getLong(0);
        }
        cursor.close();
        return id;
    }

    public void updateItem(Item it) {
        long id = it.getId();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ITEM, it.getItem());
        values.put(MySQLiteHelper.COLUMN_PRIORITY, it.getPriority());
        values.put(MySQLiteHelper.COLUMN_DATE, it.getDate());


        System.out.println("Item updates with id: " + id);
        database.update(MySQLiteHelper.TABLE_ITEMS, values, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }


    public List<Item> getAllItems() {
        List<Item> Items = new ArrayList<Item>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEMS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Item Item = cursorToItem(cursor);
            Items.add(Item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return Items;
    }


    public Cursor getAllItemsCursor() {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEMS,
                allColumns, null, null, null, null, null);
        return cursor;
    }


    private Item cursorToItem(Cursor cursor) {
        Item Item = new Item();
        Item.setId(cursor.getLong(0));
        Item.setItem(cursor.getString(1));
        return Item;
    }
} 