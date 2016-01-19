package com.example.hganeshmurthy.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class InsertItemActivity extends ActionBarActivity {

    EditText etInsertItem;
    static long pos=0;
    private ItemsDataSource datasource;
    public int RESULT_CODE_CANCEL=30;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_item);
        //pos = getIntent().getIntExtra("pos",0);
        datasource = new ItemsDataSource(this);
        datasource.open();
       // etInsertItem = (EditText) findViewById(R.id.etInsertItem);
       // etInsertItem.setText(item);
        etInsertItem = (EditText) findViewById(R.id.etInsertItem);

    }

    public void onCancelItem(View v) {
        Intent i = new Intent();
        setResult(RESULT_CODE_CANCEL, i);
        finish();
    }

    public void onInsertItem(View v) {
        EditText etInsertItem = (EditText) findViewById(R.id.etInsertItem);
        Spinner spPriority = (Spinner) findViewById(R.id.spPriority);

        String spValue = String.valueOf(spPriority.getSelectedItem());
        String itemText = etInsertItem.getText().toString();
        // Pass relevant data back as a result

        DatePicker dpItem = (DatePicker) findViewById(R.id.dpItem);
        int day = dpItem.getDayOfMonth();
        int month = dpItem.getMonth();
        int year = dpItem.getYear();

        pos = datasource.getMaxId();
        pos++;
        Item tempItem = new Item();
        tempItem.setItem(itemText);
        tempItem.setId(pos);
        tempItem.setPriority(spValue);
        tempItem.setDate(month+"-"+day+"-"+year);

        //itemsAdapter.add(itemText);
        etInsertItem.setText("");
        datasource.createItem(tempItem);


        Intent i = new Intent();
        i.putExtra("item", etInsertItem.getText().toString());
        i.putExtra("pos", pos);
        setResult(RESULT_OK, i);
        finish();
    }
}
