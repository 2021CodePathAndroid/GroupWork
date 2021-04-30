package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cookit.R;

public class RecipeDetailActivity extends AppCompatActivity {
    TextView tvTitle;
    TextView tvDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        tvTitle=findViewById(R.id.tvTitle);
        tvDetails=findViewById(R.id.tvDetails);

    }
}