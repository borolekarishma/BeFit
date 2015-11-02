/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */


package com.example.befit;


import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	
	public ArrayList<FoodData> ParseJson (String toParse) throws JSONException, MalformedURLException{
		
		ArrayList<FoodData> fd = new ArrayList<FoodData>();
		
		JSONObject root = new JSONObject(toParse);
		JSONArray FoodDatasArray = root.getJSONArray("hits");
		
		for(int i =0; i< FoodDatasArray.length();i++){
			
			JSONObject jsonFoodData =FoodDatasArray.getJSONObject(i);
			String label = jsonFoodData.getJSONObject("recipe").getString("label");
			int cal = jsonFoodData.getJSONObject("recipe").getInt("calories");
			int quant = jsonFoodData.getJSONObject("recipe").getInt("yield");
			FoodData thisFoodData = new FoodData(label,cal/quant,HomeActivity.type);
			
			fd.add(thisFoodData);
		}
		
		return fd;
	}

}
