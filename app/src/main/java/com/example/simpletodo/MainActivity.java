package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button button_add;
    EditText item_input;
    RecyclerView items_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_add = findViewById(R.id.add_btn);
        item_input = findViewById(R.id.item_input);
        items_rv = findViewById(R.id.items_list);

        items = new ArrayList<>();

        items.add("buy milk");
        items.add("do homework");

        ItemsAdapter adapter = new ItemsAdapter(items);
        items_rv.setAdapter(adapter);
        items_rv.setLayoutManager(new LinearLayoutManager(this));
    }


}