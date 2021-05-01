package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookit.models.Recipe;

import org.parceler.Parcels;

public class RecipeDetailActivity extends AppCompatActivity {
    TextView tvTitle;
    TextView tvInstructions;
    TextView tvCuisine;
    TextView tvAuthor;
    TextView tvDiet;
    TextView tvIngridients,tvSpecifics,tvReadyTime,tvDishType;
    ImageView ivPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        tvDishType=findViewById(R.id.tvDishType);
        tvSpecifics=findViewById(R.id.tvSpecifics);
        tvReadyTime=findViewById(R.id.tvReadyTime);
        tvIngridients=findViewById(R.id.tvIngridients);
        tvTitle=findViewById(R.id.tvTitle);
        tvInstructions=findViewById(R.id.tvInstructions);
        ivPhoto=findViewById(R.id.ivPhoto);
        tvAuthor=findViewById(R.id.tvAuthor);
        tvCuisine=findViewById(R.id.tvCuisine);
        tvDiet=findViewById(R.id.tvDiet);
        Recipe recipe= Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        tvTitle.setText(recipe.getTitle());
        new DownloadingImages((ImageView) findViewById(R.id.ivPhoto)).execute(recipe.getImagePath());
        tvIngridients.setText("Ingredients "+recipe.getIngredients().size()+"\n"+recipe.getIngridients());
        if(recipe.getCuisine()=="")
            tvCuisine.setText("Cuisine: No Cuisine Specified");
        else
            tvCuisine.setText("Cuisine: "+recipe.getCuisine());
        tvAuthor.setText("Author: "+recipe.getAuthor());
        tvDiet.setText("Diet : "+recipe.getDiet());
        tvSpecifics.setText("Specifics: "+recipe.getSpecifics());
        tvReadyTime.setText("Ready in "+(int)recipe.getReadyInMinutes()+" minutes.");
        tvDishType.setText("Dish type: "+recipe.getDishTypes());
        tvInstructions.setText("Instructions: \n"+recipe.getInstructions());
    }
}
