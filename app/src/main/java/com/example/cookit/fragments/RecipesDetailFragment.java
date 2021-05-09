package com.example.cookit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookit.DownloadingImages;
import com.example.cookit.R;
import com.example.cookit.models.Recipe;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipesDetailFragment extends Fragment {
    TextView tvTitle;
    TextView tvInstructions;
    TextView tvCuisine;
    TextView tvAuthor;
    TextView tvDiet;
    TextView tvIngridients,tvSpecifics,tvReadyTime,tvDishType;
    ImageView ivPhoto;

    public RecipesDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.recipe_detail);
        tvDishType=view.findViewById(R.id.tvDishType);
        tvSpecifics=view.findViewById(R.id.tvSpecifics);
        tvReadyTime=view.findViewById(R.id.tvReadyTime);
        tvIngridients=view.findViewById(R.id.tvIngridients);
        tvTitle=view.findViewById(R.id.tvTitle);
        tvInstructions=view.findViewById(R.id.tvInstructions);
        ivPhoto=view.findViewById(R.id.ivPhoto);
        tvAuthor=view.findViewById(R.id.tvAuthor);
        tvCuisine=view.findViewById(R.id.tvCuisine);
        tvDiet=view.findViewById(R.id.tvDiet);
        Recipe recipe= Parcels.unwrap(getActivity().getIntent().getParcelableExtra("recipe"));
        tvTitle.setText(recipe.getTitle());
        new DownloadingImages((ImageView) view.findViewById(R.id.ivPhoto)).execute(recipe.getImagePath());
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