package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        items = new ArrayList<>();

        // Set up the Adapter from model to RecylcerView
        ItemsAdapter.OnLongClickListener longClickListener = new ItemsAdapter.OnLongClickListener()
        {
            @Override
            public void onItemLongClicked(int position) {
                 // Delete item from the model and notify the adapter
                items.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this, "Item was removed", Toast.LENGTH_SHORT).show();
            }
        };

        adapter = new ItemsAdapter(items, longClickListener);
        items_rv.setAdapter(adapter);
        items_rv.setLayoutManager(new LinearLayoutManager(this));

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
            }
        });
    }

}