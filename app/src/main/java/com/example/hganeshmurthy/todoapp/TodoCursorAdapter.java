package com.example.hganeshmurthy.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CursorAdapter;
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
        TextView tvId = (TextView) view.findViewById(R.id.tvId);
        TextView tvItem = (TextView) view.findViewById(R.id.tvItem);
        // Extract properties from cursor
        String item = cursor.getString(cursor.getColumnIndexOrThrow("item"));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        // Populate fields with extracted properties
        tvItem.setText(item);
        tvId.setText(String.valueOf(id));

    }
}

/*
public class TodoCursorAdapter extends ArrayAdapter<Item> {

    public TodoCursorAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        // Lookup view for data population
        TextView tvItem= (TextView) convertView.findViewById(R.id.tvItem);
        TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
        // Populate the data into the template view using the data object

        tvItem.setText(item.getItem());
        tvId.setText(String.valueOf(item.getId()));
        // Return the completed view to render on screen
        return convertView;
    }

}
*/