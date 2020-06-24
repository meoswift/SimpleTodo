package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        adapter = new ItemsAdapter(items, longClickListener);
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