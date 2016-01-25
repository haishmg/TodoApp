package com.example.hganeshmurthy.todoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hganeshmurthy.todoapp.EditItemDialog.EditItemDialogListener;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends ActionBarActivity  implements EditItemDialogListener, DatePickerDialog.OnDateSetListener {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE_EDIT = 20;
    private final int REQUEST_CODE_INSERT = 30;
    private String date;
    private String position;
    private String item;
    private String priority;


    private ItemsDataSource datasource;
    TodoCursorAdapter todoAdapter;
    Cursor todoCursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        datasource = new ItemsDataSource(this);
        datasource.open();

        lvItems = (ListView) findViewById(R.id.lvItems);

        Cursor todoCursor = datasource.getAllItemsCursor();
        todoAdapter = new TodoCursorAdapter(this,todoCursor,0);
        lvItems.setAdapter(todoAdapter);

        setupListViewListener();
        setupEditViewListener();
        setActivityBackgroundColor(0xff00ff00);
    }


    public void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        datasource.deleteItem(id);
                        Cursor tempCursor = datasource.getAllItemsCursor();
                        todoAdapter.changeCursor(tempCursor);
                        todoAdapter.notifyDataSetChanged();
                        return true;
                    }
                });
    }

    public void setupEditViewListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDialog(id);
                /*
                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
                i.putExtra("item", datasource.getItemFromId(id));
                i.putExtra("pos", id);
                i.putExtra("date", datasource.getDateFromId(id));
                i.putExtra("priority", datasource.getPriorityFromId(id));

                startActivityForResult(i, REQUEST_CODE_EDIT);
                */
            }
        });
    }

    private void showEditDialog(long id) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editNameDialog = EditItemDialog.newInstance(datasource.getItemFromId(id), id, datasource.getDateFromId(id), datasource.getPriorityFromId(id));
        editNameDialog.show(fm, "fragment_edit_item ");

    }

    public void onFinishEditDialog(String item,String position, String date, String priority) {

        showSpinnerDialog(Long.parseLong(position));

         this.priority = priority;
         this.item = item;
         this.date = date;
         this.position = position;


        // Toast the name to display temporarily on screen
    }

    private void showSpinnerDialog(long id) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }
    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        this.date=monthOfYear+"-"+dayOfMonth+"-"+year;

        Item it = new Item(Long.parseLong(position),item,priority,date);
        datasource.updateItem(it);

        Cursor tempCursor =  datasource.getAllItemsCursor();
        todoAdapter.changeCursor(tempCursor);
        todoAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Successfully inserted the item", Toast.LENGTH_SHORT).show();

    }



    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

    private void readItems() {
        try {
            List<Item> tempItems = new  ArrayList<Item>();
            tempItems = datasource.getAllItems();
            List<String> actItems = new  ArrayList<String>();
            int cnt= 0;
            for (Item i : tempItems)
            {
                actItems.add(cnt,tempItems.get(cnt).toString());
                cnt++;
            }
         items =  new ArrayList<String>(actItems);
        } catch (Exception e) {
            items = new ArrayList<String>();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT) {

            Cursor tempCursor =  datasource.getAllItemsCursor();
            todoAdapter.changeCursor(tempCursor);
            todoAdapter.notifyDataSetChanged();

            // Toast the name to display temporarily on screen
            Toast.makeText(this, "Successfully updated the item", Toast.LENGTH_SHORT).show();
        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_INSERT) {


            Cursor tempCursor =  datasource.getAllItemsCursor();
            todoAdapter.changeCursor(tempCursor);
            todoAdapter.notifyDataSetChanged();

            // Toast the name to display temporarily on screen
            Toast.makeText(this, "Successfully inserted the item", Toast.LENGTH_SHORT).show();
        }

    }

    public void onAddItem(View v) {
        Intent i = new Intent(TodoActivity.this, InsertItemActivity.class);
        startActivityForResult(i, REQUEST_CODE_INSERT);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
