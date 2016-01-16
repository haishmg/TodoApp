package com.example.hganeshmurthy.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends ActionBarActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE_EDIT = 20;
    private final int REQUEST_CODE_INSERT = 30;

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
        //readItems();
        //itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        //lvItems.setAdapter(itemsAdapter);
        //ArrayList<Item> tempItems = (ArrayList<Item>) datasource.getAllItems();

        Cursor todoCursor = datasource.getAllItemsCursor();
        todoAdapter = new TodoCursorAdapter(this,todoCursor,0);
        lvItems.setAdapter(todoAdapter);


        setupListViewListener();
        setupEditViewListener();
    }

    public void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        //items.remove(pos);
                        //itemsAdapter.notifyDataSetChanged();
                        datasource.deleteItem(id);
                        Cursor tempCursor =  datasource.getAllItemsCursor();
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
                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
                i.putExtra("item", datasource.getItemFromId(id) );
                i.putExtra("pos",id);
                startActivityForResult(i, REQUEST_CODE_EDIT);
            }
        });
    }


    private void readItems() {
       /* File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }*/
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
            // Extract name value from result extras
            //String item = data.getExtras().getString("item");
            //int position = data.getExtras().getInt("pos", 0);
            //items.set(position, item);
            //itemsAdapter.notifyDataSetChanged();
            //Item tItem = new Item(position,item);
            //datasource.updateItem(tItem);

            Cursor tempCursor =  datasource.getAllItemsCursor();
            todoAdapter.changeCursor(tempCursor);
            todoAdapter.notifyDataSetChanged();

            // Toast the name to display temporarily on screen
            Toast.makeText(this, "Successfully updated the item", Toast.LENGTH_SHORT).show();
        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_INSERT) {
            // Extract name value from result extras
          ///  String item = data.getExtras().getString("item");
//            int position = data.getExtras().getInt("pos", 0);
            //items.set(position, item);
            //itemsAdapter.notifyDataSetChanged();

            Cursor tempCursor =  datasource.getAllItemsCursor();
            todoAdapter.changeCursor(tempCursor);
            todoAdapter.notifyDataSetChanged();

            //Item tItem = new Item(position,item);
            //datasource.updateItem(tItem);

            // Toast the name to display temporarily on screen
            Toast.makeText(this, "Successfully inserted the item", Toast.LENGTH_SHORT).show();
        }

    }

/*    public void writeItems() {

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
*/
    public void onAddItem(View v) {
        /*EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        datasource.createItem(itemText);
        */
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
