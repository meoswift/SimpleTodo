package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText edit_item;
    Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edit_item = findViewById(R.id.edit_item);
        save_button = findViewById(R.id.save_btn);

        // set the name of action bar
        getSupportActionBar().setTitle("Edit item");

        // get the intent that contains data passed from MainActivity
        Intent intent = getIntent();
        String item_text = intent.getStringExtra(MainActivity.KEY_ITEM_TEXT);
        final int position = intent.getExtras().getInt(MainActivity.KEY_ITEM_POSITION);
        edit_item.setText(item_text);

        // when user is done editing, they click to save the edited item
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create an intent which will contain the results
                Intent intent = new Intent();
                // pass the data (results of editing)
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, edit_item.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, position);
                // set result of intents
                setResult(RESULT_OK, intent);
                // finish activity, close and go back to main
                finish();
            }
        });
    }
}