package com.example.cookit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.FileUtils;
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

public class ShoppingList extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT =  "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> items;

    Button btn_add;
    EditText et_item;
    RecyclerView rv_items;
    ShoppListAdapter shoppListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        btn_add = findViewById(R.id.btn_add);
        et_item = findViewById(R.id.et_item);
        rv_items = findViewById(R.id.rv_items);

        loadItems();

        ShoppListAdapter.OnLongClickListener onLongClickListener = new ShoppListAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from the model
                items.remove(position);
                //Notify the adapter
                shoppListAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        ShoppListAdapter.OnClickListener onClickListener = new ShoppListAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("ShoppingList", "Single click at position " + position);
                //Create the new activity
                Intent i = new Intent(ShoppingList.this, ShoppingListEdit.class);
                //Pass the data being edited
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                //Display the activity
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };
        shoppListAdapter = new ShoppListAdapter(items, onLongClickListener, onClickListener);
        rv_items.setAdapter(shoppListAdapter);
        rv_items.setLayoutManager(new LinearLayoutManager(this));

        btn_add.setOnClickListener(new View.OnClickListener() { //adding an item through the button
            @Override
            public void onClick(View v) {
                String tobuyItem = et_item.getText().toString();
                //Add item to the model
                items.add(tobuyItem);
                //Notify the adapter an item is inserted
                shoppListAdapter.notifyItemInserted(items.size()-1);
                et_item.setText(""); //clear the edit text
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    //Handle the result of the edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            //Retrieve the updated text value
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            //Extract the original position of the edited item from the position key
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            //Update the model at the right position with new item text
            items.set(position, itemText);
            //Notify the adapter
            shoppListAdapter.notifyItemChanged(position);
            //Persist the changes
            saveItems();
            Toast.makeText(getApplicationContext(), "Item updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("ShoppingList", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    //This function will load items by reading every line of the data file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("ShoppingList", "Error reading items", e);
            items = new ArrayList<>();
        }
    }
    //This function saves items by writing them into the data file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("ShoppingList", "Error writing items", e);
        }
    }
}