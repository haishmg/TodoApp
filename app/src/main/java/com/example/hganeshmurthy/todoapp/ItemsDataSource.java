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
            MySQLiteHelper.COLUMN_ITEM };

    public ItemsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Item createItem(String Item) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ITEM, Item);
        long insertId = database.insert(MySQLiteHelper.TABLE_ITEMS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Item newItem = cursorToItem(cursor);
        cursor.close();
        return newItem;
    }

    public void deleteItem(Item Item) {
        long id = Item.getId();
        System.out.println("Item deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_ITEMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public void updateItem(Item Item) {
        long id = Item.getId();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ITEM, Item.getItem());
        System.out.println("Item deleted with id: " + id);
        database.update(MySQLiteHelper.TABLE_ITEMS, values, MySQLiteHelper.COLUMN_ID+ " = " + id,null);
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

    private Item cursorToItem(Cursor cursor) {
        Item Item = new Item();
        Item.setId(cursor.getLong(0));
        Item.setItem(cursor.getString(1));
        return Item;
    }
} 