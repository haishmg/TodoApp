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

        // Extract properties from cursor
        String item = cursor.getString(cursor.getColumnIndexOrThrow("item"));
        String priority = cursor.getString(cursor.getColumnIndexOrThrow("priority"));
        // Populate fields with extracted properties
        tvItem.setText(item);
        //tvId.setText(String.valueOf(id));
        if (priority.equals("High"))
            tvPriority.setTextColor(Color.RED);
        else if (priority.equals("Medium"))
            tvPriority.setTextColor(Color.BLUE);
        else
            tvPriority.setTextColor(Color.GREEN);
        tvPriority.setText(String.valueOf(priority));

    }
}