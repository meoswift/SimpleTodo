package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_pos";
    public static final int EDIT_TEXT_CODE = 1234;

    List<String> items;

    Button button_add;
    EditText item_input;
    RecyclerView items_rv;
    ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_add = findViewById(R.id.add_btn);
        item_input = findViewById(R.id.item_input);
        items_rv = findViewById(R.id.items_list);

        // Load items from data file to maintain persistence
        readItems();

        ItemsAdapter.OnClickListener clickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                String clicked_item = items.get(position);

                // pass data to EditActivity
                intent.putExtra(KEY_ITEM_TEXT, clicked_item);
                intent.putExtra(KEY_ITEM_POSITION, position);

                // start EditActivity that gets back the edited item to update list
                startActivityForResult(intent, EDIT_TEXT_CODE);
            }
        };


        // Set up the Adapter from model to RecyclerView
        ItemsAdapter.OnLongClickListener longClickListener = new ItemsAdapter.OnLongClickListener()
        {
            @Override
            public void onItemLongClicked(int position) {
                 // Delete item from the model and notify the adapter
                items.remove(position);
                adapter.notifyItemRemoved(position);

                Toast.makeText(MainActivity.this, "Item was removed", Toast.LENGTH_SHORT).show();

                // save the added item to list
                writeItems();
            }
        };

        // Set up the adapter with the override function and list of items
        adapter = new ItemsAdapter(items, longClickListener, clickListener);
        items_rv.setAdapter(adapter);
        items_rv.setLayoutManager(new LinearLayoutManager(this));

        // Function to add a new item to the list when user click "Add"
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todo_item = item_input.getText().toString();

                // Add item to the model
                items.add(todo_item);

                // Notify the adapter that an item has been added
                adapter.notifyItemInserted(items.size() - 1);
                item_input.setText("");

                // Pop-up message that tells user that an item has been successfully added
                Toast.makeText(MainActivity.this, "Item was added", Toast.LENGTH_SHORT).show();

                // save the added item to list
                writeItems();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TEXT_CODE && resultCode == RESULT_OK) {
            // retrieve data that was passed from EditActivity
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            String new_item = data.getStringExtra(KEY_ITEM_TEXT);
            // update the model with edited item
            items.set(position, new_item);
            // notify adapter of changes
            adapter.notifyDataSetChanged();
            // save to file to persist the changes
            writeItems();

            Toast.makeText(MainActivity.this, "Item was updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    // Get the file we want to read and write from
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    // Function to read lines from a file and populate the list of todo items
    private void readItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    // Overwrite the data file to save changes when user add or remove an item
    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}