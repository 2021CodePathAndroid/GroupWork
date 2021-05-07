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
    String dishTypes="";
    String instructions;
    boolean vegan,vegetarian,ketogenic,veryHealthy,veryPopular,glutenFree,dairyFree,sustainable;
    String author,diet;

    // Empty constructor needed by the Parceler library
    public Recipe() {}

    public Recipe(JSONObject jsonObject) throws JSONException {

        vegan=jsonObject.optBoolean("vegan");
        vegetarian=jsonObject.optBoolean("vegetarian");
        ketogenic=jsonObject.optBoolean("ketogenic");
        veryHealthy=jsonObject.optBoolean("veryHealthy");
        veryPopular=jsonObject.optBoolean("veryPopular");
        glutenFree=jsonObject.optBoolean("glutenFree");
        dairyFree=jsonObject.optBoolean("dairyFree");
        sustainable=jsonObject.optBoolean("sustainable");
        dishTypes=jsonObject.getString("dishTypes");

        author=jsonObject.optString("author");
        if(author=="")
            author="No Author";
        diet=jsonObject.optString("diet");
        if(diet=="")
            diet="No Diet Specified";

        recipeID = jsonObject.getDouble("id");
        title = jsonObject.getString("title");
        cuisine = jsonObject.getString("cuisines");
        imagePath = jsonObject.optString("image");
        if(imagePath==""){
            System.out.println("Emtpy String");
            imagePath="https://media.istockphoto.com/vectors/sad-face-icon-unhappy-face-symbol-vector-id934714316?k=6&m=934714316&s=170667a&w=0&h=5Tn4NDO6HAvElaTn3KrZ9YrncMzJ4B7Vo3c_IlWNPcc=";
        }
        servings = jsonObject.getInt("servings");
        readyInMinutes = jsonObject.getDouble("readyInMinutes");
        instructions = jsonObject.getString("instructions");
        JSONArray ingredientsJSON = jsonObject.getJSONArray("extendedIngredients");
        ingredients= new ArrayList<>();
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
    public String getSpecifics(){
        String a="";
        if(vegan)
            a+="vegan";
        if(vegetarian)
            a+=" vegetarian";
        if(ketogenic)
            a+=" ketogenic";
        if(veryPopular)
            a+=" very popular";
        if(veryHealthy)
            a+=" very healthy";
        if(glutenFree)
            a+=" gluten free";
        if(dairyFree)
            a+=" dairy free";
        if(sustainable)
            a+=" sustainable";
        if(a=="")
            a="Not Specified";
        return a;
    }
    public String getDishTypes() {
        if(dishTypes.length()==2)
            return "";
        return (dishTypes.substring(1,dishTypes.length()-2).replace("\"",""));
    }

    public String getAuthor() {
        return author;
    }

    public String getDiet() {
        return diet;
    }

    public double getRecipeID() {
        return recipeID;
    }

    public String getTitle() {
        return title;
    }

    public String getCuisine() {
        if(cuisine.length()==2)
            return "";
        return (cuisine.substring(1,cuisine.length()-2)).replace("\"","");
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

    //combines ingredients into one string
    public String getIngridients(){
        String i="";
        if(ingredients==null){
            System.out.println("ingredients list is null");
            return "";
        }
        for(int a=0;a<ingredients.size();a++)
            i+=ingredients.get(a);
        return i;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        String a=instructions;
        try{
            a=a.replaceAll("<ol>","");
        }
        catch (Exception e){}
        try{
            a=a.replaceAll("<li>","");
        }
        catch(Exception e){}
        try{
            a=a.replaceAll("</li>","");
        }
        catch(Exception e){}
        try{
            a.replaceAll("</ol>","");
        }
        catch (Exception e){}
        try{
            a.replaceAll(".","\n");
        }
        catch (Exception e){}
        try{
            a=a.replaceAll("</p>","");
        }
        catch (Exception e){}
        a=a.substring(0,a.lastIndexOf(".")+1);
        if(a=="")
            return "No Instructions";
        return a;
    }
}
