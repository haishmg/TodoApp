package com.example.hganeshmurthy.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends ActionBarActivity {
    EditText etEditItem;
    private final int REQUEST_CODE = 20;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String item = getIntent().getStringExtra("item");
        pos = getIntent().getIntExtra("pos",0);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(item);
    }

    public void onEditItem(View v) {
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        String itemText = etEditItem.getText().toString();
        // Pass relevant data back as a result
        Intent i = new Intent();
        i.putExtra("item", etEditItem.getText().toString());
        i.putExtra("pos", pos);
        setResult(RESULT_OK, i);
        finish();
    }


}
