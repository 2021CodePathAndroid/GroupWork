package com.example.cookit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.cookit.R;
import com.example.cookit.adapters.RecipesAdapter;
import com.example.cookit.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipesFragment extends Fragment {

    public static final String YOUR_API_KEY = "9fee86db00964b1fa3268dbab654b27d";//82f2b2603b9046ac824424798aaf7ebc|a4ac8323620042179412db200f9b28d5
    public static final String RECIPES_URL = "https://api.spoonacular.com/recipes/random?number=20&apiKey=" + YOUR_API_KEY;
    public static final String TAG = "MainActivity";
    List<Recipe> recipes = new ArrayList<>();

    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.recipes_main);

        RecyclerView rvRecipes = view.findViewById(R.id.rv_recipes);

        // Create an adapter
        RecipesAdapter recipesAdapter = new RecipesAdapter(getContext(), recipes);

        // Set the adapter on the RecyclerView
        rvRecipes.setAdapter(recipesAdapter);

        // Set a layout manager on the RecyclerView
        rvRecipes.setLayoutManager(new LinearLayoutManager(getContext()));

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
