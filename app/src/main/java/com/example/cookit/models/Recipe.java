package com.example.cookit.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Recipe {
    double recipeID;
    String title;
    String imagePath;
//    List<String> cuisines;
    String cuisine;
    int servings;
    double readyInMinutes;
    List<Ingredient> ingredients;
    String instructions;


    // Empty constructor needed by the Parceler library
    public Recipe() {}

    public Recipe(JSONObject jsonObject) throws JSONException {
        recipeID = jsonObject.getDouble("id");
        title = jsonObject.getString("title");
        cuisine = jsonObject.getString("cuisines");
        imagePath = jsonObject.getString("image");
        servings = jsonObject.getInt("servings");
        readyInMinutes = jsonObject.getDouble("readyInMinutes");
        instructions = jsonObject.getString("instructions");
        JSONArray ingredientsJSON = jsonObject.getJSONArray("extendedIngredients");
        List<Ingredient> ingredients= new ArrayList<>();
        for (int i = 0; i < ingredientsJSON.length(); ++i) {
            Ingredient ingredient = new Ingredient(ingredientsJSON.getJSONObject(i));
            ingredients.add(ingredient);
        }
    }

    public static List<Recipe> fromJSONArray(JSONArray recipeJsonArray) throws JSONException {
        List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < recipeJsonArray.length(); ++i) {
            Recipe r = new Recipe(recipeJsonArray.getJSONObject(i));
            recipes.add(r);
        }
        return recipes;
    }

    public double getRecipeID() {
        return recipeID;
    }

    public String getTitle() {
        return title;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getServings() {
        return servings;
    }

    public double getReadyInMinutes() {
        return readyInMinutes;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
}
