package application;

import java.io.*;
import java.util.*;

@SuppressWarnings("rawtypes")
public class FoodData<F> implements FoodDataADT {

	private List<FoodItem> list;
	private List<FoodItem> customerList;

	FoodData() {
		list = new ArrayList<FoodItem>();
	}

	@Override
	/*
	 * load file in the food list
	 * @param read in file path as a string value
	 */
	public void loadFoodItems(String filePath) {
		list = new ArrayList<FoodItem>();
		String[] details = new String[7];
		Scanner input;
		File file = new File(filePath);
		try {
			input = new Scanner(file);

			while (input.hasNext()) {
				String line = input.nextLine();
				String[] read = line.split(",");
				details[0] = read[0];
				details[1] = read[1];
				details[2] = read[3];
				details[3] = read[5];
				details[4] = read[7];
				details[5] = read[9];
				details[6] = read[11];

				FoodItem food = new FoodItem(read[0], read[1], read[3], read[5], read[7], read[9], read[11]);
				food.addNutrient(read[2], Double.parseDouble(read[3]));
				food.addNutrient(read[4], Double.parseDouble(read[5]));
				food.addNutrient(read[6], Double.parseDouble(read[7]));
				food.addNutrient(read[8], Double.parseDouble(read[9]));
				food.addNutrient(read[10], Double.parseDouble(read[11]));

				list.add(food);


			}
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	/* filter name and update list
	 * @param user used string (name) to search the food it they want
	 * 	
	 */
	public List<F> filterByName(String substring) {

		List<FoodItem> filterList = new ArrayList<FoodItem>();

		for (int i = 0; i < list.size(); i++) {
			boolean bool = list.get(i).getName().toLowerCase().contains(substring.toLowerCase());
			if (bool) {
				filterList.add(list.get(i));
			}
		}
		return (List<F>) filterList;
	}
	/*filter nutrient and update list
	 * could read in data for cal, fat,fiber,carbs,protein
	 * @param rules user input
	 */

	@SuppressWarnings("unchecked")
	@Override
	 public List<F> filterByNutrients(List rules) {
	  
	  ArrayList<String> rulesList = new ArrayList<String>();
	  rulesList = (ArrayList<String>) rules;
	  
	  int numberRules = rulesList.size();
	  List<FoodItem> templist = new ArrayList<FoodItem>();
	  templist.addAll(list);
	  for (int i = 0; i < numberRules; i++) {
	    
	   String[] splited = ((String) rulesList.get(i)).split("\\s+");

	   String factor = splited[0];
	   String operator = splited[1];
	   double comparedNumber = Double.parseDouble(splited[2]);
	   
	   int j = 0;
	   while (j < templist.size()) {

	    if (operator.equals("<=")) {
	     double foodstat = templist.get(j).getNutrientValue(factor);
	     if (foodstat > comparedNumber) {
	   
	      templist.remove(j);
	     } else { 
	      j++;
	     }
	    } else if (operator.equals("==")) {
	     double foodstat = templist.get(j).getNutrientValue(factor);
	     if (foodstat != comparedNumber) {
	      templist.remove(j);
	     } else { 
	      j++;
	     }
	    } else if (operator.equals(">=")) {
	     double foodstat = templist.get(j).getNutrientValue(factor);
	     if (foodstat < comparedNumber) {
	      templist.remove(j);
	     } else { 
	      j++;
	     }
	    }
	   }
	  
	  }
	  return (List<F>) templist;
	 }

	@Override
	/*
	 * helper method
	 * add fooditem and also sort the list
	 * 
	 */
	public void addFoodItem(FoodItem foodItem) {
		list.add(foodItem);
		Collections.sort(list);
	}
	/*
	 * helper method
	 * delete fooditem and also sort the list
	 */

	public void deleteFoodItem(FoodItem foodItem) {
		list.remove(foodItem);
		Collections.sort(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	/*
	 * helper method
	 * get the whole list that was loaded
	 */
	public List<F> getAllFoodItems() {

		Collections.sort(list);
		return (List<F>) list;
	}
	/*
	 * save the meal list into a new file
	 * @param filename
	 */

	@Override
	public void saveFoodItems(String filename) {
		try {
			File newFile = new File(filename);
			newFile.createNewFile();
			FileWriter fileW = new FileWriter(newFile);
			BufferedWriter buffW = new BufferedWriter(fileW);
			buffW.write("ID,Food Name,Calories,Fat,Carbohydrate,Fiber,Protein");
			buffW.newLine();
			int totalFiber = 0;
			   int totalCabs = 0;
			   int totalProtein = 0;
			   int totalCalories = 0;
			   int totalfat = 0;
			for (int i = 0; i < customerList.size(); i++) {
				buffW.write(customerList.get(i).getID() + "," + customerList.get(i).getName() + ","
						+ customerList.get(i).getCalories() + "," + customerList.get(i).getFat() + ","
						+ customerList.get(i).getCarb() + "," + customerList.get(i).getFiber() + ","
						+ customerList.get(i).getProtein());
				buffW.newLine();
				totalfat += Integer.parseInt(customerList.get(i).getFat());
			    totalProtein += Integer.parseInt(customerList.get(i).getProtein());
			    totalCabs += Integer.parseInt(customerList.get(i).getCarb());
			    totalCalories += Integer.parseInt(customerList.get(i).getCalories());
			    totalFiber += Integer.parseInt(customerList.get(i).getFiber());
			}
			buffW.write("Total fat = " + totalfat + ".\n"
				       + "Total protein = " + totalProtein + ".\n"
				       + "Total fiber = " + totalFiber + ".\n"
				       + "Total Calories = " + totalCalories + ".\n"
				       + "Total carbonhydrate = " + totalCabs + ".\n");
				   buffW.newLine();
			buffW.close();
		} catch (Exception ex) {

		}

	}
	
	/*
	 * sort meal list
	 */

	@SuppressWarnings("unchecked")
	public void setCustomerList(List<F> list) {
		customerList = (List<FoodItem>) list;
		Collections.sort(customerList);

	}
	

}
