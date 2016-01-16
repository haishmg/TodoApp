package com.example.hganeshmurthy.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

public class InsertItemActivity extends ActionBarActivity {

    EditText etInsertItem;
    static long pos=0;
    private ItemsDataSource datasource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_item);
        String item = getIntent().getStringExtra("item");
        //pos = getIntent().getIntExtra("pos",0);
        datasource = new ItemsDataSource(this);
        datasource.open();
       // etInsertItem = (EditText) findViewById(R.id.etInsertItem);
       // etInsertItem.setText(item);
        etInsertItem = (EditText) findViewById(R.id.etInsertItem);
    }

    public void onInsertItem(View v) {
        EditText etInsertItem = (EditText) findViewById(R.id.etInsertItem);
        String itemText = etInsertItem.getText().toString();
        // Pass relevant data back as a result

        pos = datasource.getMaxId();
        pos++;

        Item tempItem = new Item();
        tempItem.setItem(itemText);
        tempItem.setId(pos);

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
