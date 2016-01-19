package com.example.hganeshmurthy.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class EditItemActivity extends ActionBarActivity {
    EditText etEditItem;
    private final int REQUEST_CODE = 20;
    public int RESULT_CODE_CANCEL=30;

    long pos;
    private ItemsDataSource datasource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String item = getIntent().getStringExtra("item");
        pos = getIntent().getLongExtra("pos", 0);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(item);
        datasource = new ItemsDataSource(this);
        datasource.open();
        String date = getIntent().getStringExtra("date");
        String dList[] =  date.split("-");
        DatePicker dpItem = (DatePicker) findViewById(R.id.dpItem);
        int yr = Integer.parseInt(dList[2]);
        int mm = Integer.parseInt(dList[0]);
        int dd = Integer.parseInt(dList[1]);

        Spinner spPriority = (Spinner) findViewById(R.id.spPriority);
        String priority = getIntent().getStringExtra("priority");
        int selection=0;
        if (priority.equals("Medium"))
            selection = 1 ;
        else if (priority.equals("Low"))
            selection = 2 ;
        spPriority.setSelection(selection);
        dpItem.updateDate(yr,mm,dd);
    }

    public void onCancelItem(View v) {
        Intent i = new Intent();
        setResult(RESULT_CODE_CANCEL, i);
        finish();
    }

    public void onEditItem(View v) {
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        String itemText = etEditItem.getText().toString();
        Spinner spPriority = (Spinner) findViewById(R.id.spPriority);

        String spValue = String.valueOf(spPriority.getSelectedItem());

        DatePicker dpItem = (DatePicker) findViewById(R.id.dpItem);
        int day = dpItem.getDayOfMonth();
        int month = dpItem.getMonth();
        int year = dpItem.getYear();



        // Pass relevant data back as a result
        Item it = new Item(pos,itemText,spValue,month+"-"+day+"-"+year);
        datasource.updateItem(it);

        Intent i = new Intent();
        i.putExtra("item", etEditItem.getText().toString());
        i.putExtra("pos", pos);
        setResult(RESULT_OK, i);
        finish();
    }


}
