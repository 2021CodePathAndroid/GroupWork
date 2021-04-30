package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cookit.R;
import com.example.cookit.models.Recipe;

import org.parceler.Parcels;
import org.w3c.dom.Text;

public class RecipeDetailActivity extends AppCompatActivity {
    TextView tvTitle;
    TextView tvDetails;
    TextView tvCuisine;
    TextView tvAuthor;
    TextView tvDiet;
    ImageView ivPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        tvTitle=findViewById(R.id.tvTitle);
        tvDetails=findViewById(R.id.tvDetails);
        ivPhoto=findViewById(R.id.ivPhoto);
        tvAuthor=findViewById(R.id.tvAuthor);
        tvCuisine=findViewById(R.id.tvCuisine);
        tvDiet=findViewById(R.id.tvDiet);
        Recipe recipe= Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        tvTitle.setText(recipe.getTitle());
        new DownloadingImages((ImageView) findViewById(R.id.ivPhoto)).execute(recipe.getImagePath());
        //tvCuisine.setText("Cuisine: "+recipe.getCuisine());
        //tvAuthor.setText("Author: "+recipe.getAuthor());
        //tvDiet.setText("Diet : "+recipe.getDiet());
    }
}
