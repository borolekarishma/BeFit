/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */


package com.example.befit;

public class FoodData {

	
	String category, fooditem; 
	int cal;
	
	@Override
	public String toString() {
		return "FoodData [category=" + category + ", fooditem=" + fooditem
				+ ", cal=" + cal + "]";
	}

	

	public FoodData(String label, int i, String type) {
		// TODO Auto-generated constructor stub
		category = type;
		fooditem = label;
		cal= i;
	}

	public FoodData() {
		// TODO Auto-generated constructor stub
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFooditem() {
		return fooditem;
	}

	public void setFooditem(String fooditem) {
		this.fooditem = fooditem;
	}

	public int getCal() {
		return cal;
	}

	public void setCal(int cal) {
		this.cal = cal;
	}

	
	
}
