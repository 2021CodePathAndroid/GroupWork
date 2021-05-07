package com.example.cookit.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookit.R;
import com.example.cookit.RecipeDetailActivity;
import com.example.cookit.models.Recipe;

import org.parceler.Parcels;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder>{

    Context context;
    List<Recipe> recipes;

    public RecipesAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    //Pass in context and list of tweets
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    //For each row inflate the layout for a tweet
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the data at a position
        Recipe recipe = recipes.get(position);
        //Bind the tweet with the viewHolder
        holder.bind(recipe);
    }

    //Bind values based on the position of the element
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    //SwipeLayout helper method
    //Clean all elements of the recycler
    public void clear(){
        recipes.clear(); //modifying the existing reference, not creating a new one
        notifyDataSetChanged();
    }

    //SwipeLayout helper method
    //Add a list of items to our dataset
    public void addAll(List<Recipe> tweetList) {
        recipes.addAll(tweetList);
        notifyDataSetChanged();
    }

    // Define a viewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout recipeFrame;
        TextView tvTitle;
        TextView tvCuisine;
        ImageView ivRecipePhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCuisine = itemView.findViewById(R.id.tv_cuisine);
            ivRecipePhoto = itemView.findViewById(R.id.iv_recipePhoto);
            recipeFrame=itemView.findViewById(R.id.recipe_frame);
        }

        public void bind(Recipe recipe) {
            //Take the different attributes of a tweet and bnd them into views
            tvTitle.setText(recipe.getTitle());
            tvCuisine.setText(recipe.getCuisine());
            Glide.with(context).load(recipe.getImagePath()).into(ivRecipePhoto);
            //Registering a clicklistener
            recipeFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Navigating to a new activity
                    Intent details=new Intent(context, RecipeDetailActivity.class);
                    details.putExtra("recipe", Parcels.wrap(recipe));
                    context.startActivity(details);
                    //Toast.makeText(context,"AHGAKFAFKAGVJMADGBVNMAD", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
