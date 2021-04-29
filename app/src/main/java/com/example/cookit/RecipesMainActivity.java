package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.cookit.adapters.RecipesAdapter;
import com.example.cookit.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class RecipesMainActivity extends AppCompatActivity {
    public static final String YOUR_API_KEY = "82f2b2603b9046ac824424798aaf7ebc";
    public static final String RECIPES_URL = "https://api.spoonacular.com/recipes/random?number=20&apiKey=" + YOUR_API_KEY;
    public static final String TAG = "MainActivity";
    List<Recipe> recipes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_main);

        RecyclerView rvRecipes = findViewById(R.id.rv_recipes);

        // Create an adapter
        RecipesAdapter recipesAdapter = new RecipesAdapter(this, recipes);

        // Set the adapter on the RecyclerView
        rvRecipes.setAdapter(recipesAdapter);

        // Set a layout manager on the RecyclerView
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(RECIPES_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("recipes");
                    Log.i(TAG, "Recipes: " + results.toString());
                    recipes.addAll(Recipe.fromJSONArray(results));
                    recipesAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Recipes: " + recipes.size());
                } catch (JSONException e) {
                    Log.i(TAG, "Hit JSON exception " + e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.i(TAG, "onFailure");
                Log.i(TAG, throwable.toString());
            }
        });
    }
}