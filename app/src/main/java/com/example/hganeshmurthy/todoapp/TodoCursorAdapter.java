package com.example.hganeshmurthy.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
/**
 * Created by hganeshmurthy on 1/13/16.
 */

public class TodoCursorAdapter extends CursorAdapter {
    public TodoCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvItem = (TextView) view.findViewById(R.id.tvItem);
        TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);


        view.setBackgroundColor(Color.LTGRAY);
        // Extract properties from cursor
        String item = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_ITEM));
        String priority = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_PRIORITY));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_DATE));

        String lDate[] = date.split("-");
        int mm = Integer.parseInt(lDate[0])+1;
        int dd = Integer.parseInt(lDate[1]);
        int yy = Integer.parseInt(lDate[2]);

        date = mm+"/"+dd+"/"+yy;

        // Populate fields with extracted properties
        tvItem.setText(item);
        if (priority.equalsIgnoreCase("high"))
        {
            tvPriority.setTextColor(Color.RED);
            tvDate.setTextColor(Color.RED);
        }
        else if (priority.equalsIgnoreCase("medium")) {
            tvPriority.setTextColor(Color.BLUE);
            tvDate.setTextColor(Color.BLUE);
        }
        else {
            tvPriority.setTextColor(Color.GREEN);
            tvDate.setTextColor(Color.GREEN);
        }
        tvPriority.setText(String.valueOf(priority));
        tvDate.setText(String.valueOf(date));

    }
}