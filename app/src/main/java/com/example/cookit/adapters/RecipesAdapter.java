package com.example.cookit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookit.R;
import com.example.cookit.models.Recipe;

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

        TextView tvTitle;
        TextView tvCuisine;
        ImageView ivRecipePhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCuisine = itemView.findViewById(R.id.tv_cuisine);
            ivRecipePhoto = itemView.findViewById(R.id.iv_recipePhoto);
        }

        public void bind(Recipe recipe) {
            //Take the different attributes of a tweet and bnd them into views
            tvTitle.setText(recipe.getTitle());
            tvCuisine.setText(recipe.getCuisine());
            Glide.with(context).load(recipe.getImagePath()).into(ivRecipePhoto);
        }
    }
}
