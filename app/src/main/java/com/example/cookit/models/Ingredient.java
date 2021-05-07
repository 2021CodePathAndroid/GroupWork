package com.example.cookit.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Ingredient {
    String name;
    double amount;
    String unitShort;

    //Parceler
    public Ingredient(){}

    public Ingredient(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        amount = jsonObject.getDouble("amount");
        unitShort = jsonObject.getString("unit");
//        amount = jsonObject.getJSONObject("measures").getJSONObject("us").getDouble("amount");
//        unitShort = jsonObject.getJSONObject("measures").getJSONObject("us").getString("unitShort");
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public String getAmountAndUnitFormatted(){
        return String.format(amount + " " + unitShort);
    }

    public String toString(){ return "Name : "+name+"\nAmount: "+amount+" "+unitShort+"\n"; }


}
