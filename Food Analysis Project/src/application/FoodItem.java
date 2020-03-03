package application;

import java.util.*;
/**
 * This class represents a food item with all its properties.
 * 
 * @author aka
 */
public class FoodItem implements Comparable<FoodItem> {

	/*
	 * instance fields
	 */
	private String name;
	private String id;
	private String calories;
	private String fat;
	private String carb;
	private String protein;
	private String fiber;

	// Map of nutrients and value.
	private HashMap<String, Double> nutrients;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name of the food item
	 * @param id
	 *            unique id of the food item
	 */
	public FoodItem(String id, String name) {
		this.name = name;
		this.id = id;
		nutrients = new HashMap<String, Double>();
	}

	public FoodItem(String id, String name, String calories, String fat, String carb, String fiber, String protein) {
		this.id = id;
		this.name = name;
		this.calories = calories;
		this.fat = fat;
		this.carb = carb;
		this.protein = protein;
		this.fiber = fiber;
		nutrients = new HashMap<String, Double>();
	}

	/**
	 * Gets the name of the food item
	 * 
	 * @return name of the food item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the unique id of the food item
	 * 
	 * @return id of the food item
	 */
	public String getID() {
		return id;
	}

	/**
	 * Gets the nutrients of the food item
	 * 
	 * @return nutrients of the food item
	 */
	public HashMap<String, Double> getNutrients() {

		return nutrients;
	}

	/**
	 * Adds a nutrient and its value to this food. If nutrient already exists,
	 * updates its value.
	 */
	public void addNutrient(String name, double value) {
		nutrients.put(name, value);
	}

	/**
	 * Returns the value of the given nutrient for this food item. If not present,
	 * then returns 0.
	 */
	public double getNutrientValue(String name) {

		if (!nutrients.containsKey(name)) {
			return 0;
		}

		return nutrients.get(name);
	}
	/*
	 * sort food list and meal list fooditem is a comparable object
	 * 
	 * @param Fooditem
	 */

	public int compareTo(FoodItem a) {
		return this.getName().compareTo(a.getName());

	}

	/*
	 * helper method return fat
	 */
	public String getFat() {
		return fat;
	}

	/*
	 * helper method return carbs
	 */
	public String getCarb() {
		return carb;
	}

	/*
	 * helper method return calories
	 */
	public String getCalories() {
		return calories;
	}

	/*
	 * helper method return protein
	 */
	public String getProtein() {
		return protein;
	}

	/*
	 * helper method retun fiber
	 */
	public String getFiber() {
		return fiber;
	}

}
