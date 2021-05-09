package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cookit.fragments.ShoppingListFragment;

public class ShoppingListEdit extends AppCompatActivity {

    EditText edit_item;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_edit);

        edit_item = findViewById(R.id.edit_item);
        btn_save = findViewById(R.id.btn_save);

        getSupportActionBar().setTitle("Edit item");

        edit_item.setText(getIntent().getStringExtra(ShoppingListFragment.KEY_ITEM_TEXT));

        //When the user is done editing, they click the save button
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create an intent which will contain the results
                Intent intent = new Intent();
                //Pass the data (results of editing)
                intent.putExtra(ShoppingListFragment.KEY_ITEM_TEXT, edit_item.getText().toString());
                intent.putExtra(ShoppingListFragment.KEY_ITEM_POSITION, getIntent().getExtras().getInt(ShoppingListFragment.KEY_ITEM_POSITION));
                //Set the result of the intent
                setResult(RESULT_OK, intent);
                //Finish activity, close the screen and go back
                finish();
            }
        });
    }
}