package application;

import java.util.*;
import java.awt.Desktop;
import java.io.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main extends Application {

	/*
	 * instance fields
	 */
	private static final int filterX = 930;
	private static final int filterY = 100;

	private File file;
	@SuppressWarnings("unused")
	private Desktop desktop = Desktop.getDesktop();

	private FoodData<FoodItem> fooddata;

	private List<FoodItem> list;
	private List<FoodItem> customerlist;

	private final ObservableList<FoodItem> data = FXCollections.observableArrayList();
	private final ObservableList<FoodItem> customerData = FXCollections.observableArrayList();

	private TableView<FoodItem> table;
	private TableView<FoodItem> customerTable;

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) {
		try {

			fooddata = new FoodData<FoodItem>();

			table = new TableView<FoodItem>();
			customerTable = new TableView<FoodItem>();

			list = new ArrayList<FoodItem>();
			customerlist = new ArrayList<FoodItem>();

			FileChooser fileChooser = new FileChooser();

			Button buttonLoad = new Button("Load File...");
			Button buttonSave = new Button("Export Meal List...");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("csv, txt Files", "*.csv", "*.txt"));
			buttonLoad.setOnAction(e -> {
				file = fileChooser.showOpenDialog(stage);
				fooddata.loadFoodItems(file.getPath());
				list = fooddata.getAllFoodItems();
				data.clear();
				listToData(data, list);
			});

			buttonSave.setOnAction(e -> {
				customerlist.clear();
				dataToList(customerData, customerlist);
				fooddata.setCustomerList(customerlist);
				fooddata.saveFoodItems("Created_Customer_Food_List.csv");
			});

			HBox IObox = new HBox();
			Label space = new Label("                                                                 ");
			IObox.setSpacing(50);
			IObox.getChildren().addAll(space, buttonLoad, buttonSave);

			/*
			 * setting up the size of stage (GUI page) , the title of the GUI, and the title
			 * for the food list table
			 */

			HBox hb = new HBox();
			HBox hb2 = new HBox();

			Scene scene = new Scene(new Group());
			stage.setTitle("Food List");
			stage.setWidth(1300);
			stage.setHeight(1100);

			final Label label = new Label("Food List");
			label.setFont(new Font("Arial", 20));

			/*
			 * setting up all the columns in the Food List table
			 */

			TableColumn<FoodItem, String> id = new TableColumn<>("ID");
			id.setMinWidth(30);
			id.setCellValueFactory(new PropertyValueFactory<>("ID"));

			TableColumn<FoodItem, String> foodNameCol = new TableColumn<>("Food Name");
			foodNameCol.setMinWidth(10);
			foodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<FoodItem, String> caloriesCol = new TableColumn<>("Calories");
			caloriesCol.setMinWidth(10);
			caloriesCol.setCellValueFactory(new PropertyValueFactory<>("calories"));

			TableColumn<FoodItem, String> fatCol = new TableColumn<>("Fat");
			fatCol.setMinWidth(10);
			fatCol.setCellValueFactory(new PropertyValueFactory<>("fat"));

			TableColumn<FoodItem, String> carbCol = new TableColumn<>("Carb");
			carbCol.setMinWidth(10);
			carbCol.setCellValueFactory(new PropertyValueFactory<>("carb"));

			TableColumn<FoodItem, String> proteinCol = new TableColumn<>("Protein");
			proteinCol.setMinWidth(10);
			proteinCol.setCellValueFactory(new PropertyValueFactory<>("protein"));

			TableColumn<FoodItem, String> fiberCol = new TableColumn<>("Fiber");
			fiberCol.setMinWidth(10);
			fiberCol.setCellValueFactory(new PropertyValueFactory<>("fiber"));

			/*
			 * text field allows user to add their preference food into the food list based
			 * on conditions such as Name, Calories, Fat, Carb, Protein, Fiber, and Food ID.
			 * After adding their preference, clear all the text field
			 */

			table.setItems(data);
			table.getColumns().addAll(id, foodNameCol, caloriesCol, fatCol, carbCol, fiberCol, proteinCol);

			final TextField addFoodName = new TextField();
			addFoodName.setPromptText("Name");
			addFoodName.setMaxWidth(foodNameCol.getPrefWidth());

			final TextField addCalories = new TextField();
			addCalories.setMaxWidth(caloriesCol.getPrefWidth());
			addCalories.setPromptText("Calories");

			final TextField addFat = new TextField();
			addFat.setPromptText("Fat");
			addFat.setMaxWidth(fatCol.getPrefWidth());

			final TextField addCarb = new TextField();
			addCarb.setPromptText("Carb");
			addCarb.setMaxWidth(carbCol.getPrefWidth());

			final TextField addProtein = new TextField();
			addProtein.setPromptText("Fiber");
			addProtein.setMaxWidth(proteinCol.getPrefWidth());

			final TextField addFiber = new TextField();
			addFiber.setPromptText("Protein");
			addFiber.setMaxWidth(fiberCol.getPrefWidth());

			final TextField addID = new TextField();
			addID.setPromptText("Food ID");
			addID.setMaxWidth(id.getPrefWidth());

			/*
			 * check the validation of the input 1.check if its empty 2.check if right type
			 * 3.check for negative value clear all the text field after the button is
			 * pressed
			 */

			final Button addButton = new Button("Add to Food List");
			addButton.setOnAction((ActionEvent e) -> {

				if (!(addID.getText().trim().isEmpty() || addFoodName.getText().trim().isEmpty()
						|| addCalories.getText().trim().isEmpty() || addFat.getText().trim().isEmpty()
						|| addCarb.getText().trim().isEmpty() || addProtein.getText().trim().isEmpty()
						|| addFiber.getText().trim().isEmpty())) {

					if (isNumeric(addID.getText()) && !isNumeric(addFoodName.getText())
							&& isNumeric(addCalories.getText()) && isNumeric(addFat.getText())
							&& isNumeric(addCarb.getText()) && isNumeric(addProtein.getText())
							&& isNumeric(addFiber.getText())) {

						if (!(Double.parseDouble(addID.getText()) < 0 || Double.parseDouble(addCalories.getText()) < 0
								|| Double.parseDouble(addFat.getText()) < 0 || Double.parseDouble(addCarb.getText()) < 0
								|| Double.parseDouble(addProtein.getText()) < 0
								|| Double.parseDouble(addFiber.getText()) < 0)) {
							data.add(new FoodItem(addID.getText(), addFoodName.getText(), addCalories.getText(),
									addFat.getText(), addCarb.getText(), addProtein.getText(), addFiber.getText()));
							fooddata.addFoodItem(new FoodItem(addID.getText(), addFoodName.getText(),
									addCalories.getText(), addFat.getText(), addCarb.getText(), addProtein.getText(),
									addFiber.getText()));

						} else {
							Alert a = new Alert(AlertType.WARNING, "please don't enter negative value");
							a.show();

						}
					} else {
						Alert a = new Alert(AlertType.WARNING,
								"please enter valid type (hint: name must be string type)");
						a.show();
					}

				} else {
					Alert a = new Alert(AlertType.WARNING,
							"please add food id, name ,data (if no data, enter 0 instead");
					a.show();
				}
				addID.clear();
				addFoodName.clear();
				addCalories.clear();
				addFat.clear();
				addCarb.clear();
				addProtein.clear();
				addFiber.clear();
			});

			/*
			 * user could delete food items they don't prefer to see in the foodList
			 */

			final Button deleteButton = new Button("Delete from Food List");
			deleteButton.setOnAction((ActionEvent e) -> {
				ObservableList<FoodItem> foodItemSelected, allfoodItems;
				allfoodItems = table.getItems();
				foodItemSelected = table.getSelectionModel().getSelectedItems();
				foodItemSelected.forEach(allfoodItems::remove);
			});

			hb.getChildren().addAll(addID, addFoodName, addCalories, addFat, addCarb, addProtein, addFiber, addButton,
					deleteButton);
			hb.setSpacing(3);

			/*
			 * setting meal List table, including all the same elements the food list table
			 * have
			 */

			customerTable.setEditable(false);

			TableColumn<FoodItem, String> foodNameCol1 = new TableColumn<>("Food Name");
			foodNameCol1.setMinWidth(10);
			foodNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<FoodItem, String> caloriesCol1 = new TableColumn<>("Calories");
			caloriesCol1.setMinWidth(10);
			caloriesCol1.setCellValueFactory(new PropertyValueFactory<>("calories"));

			TableColumn<FoodItem, String> fatCol1 = new TableColumn<>("Fat");
			fatCol1.setMinWidth(10);
			fatCol1.setCellValueFactory(new PropertyValueFactory<>("fat"));

			TableColumn<FoodItem, String> carbCol1 = new TableColumn<>("Carb");
			carbCol1.setMinWidth(10);
			carbCol1.setCellValueFactory(new PropertyValueFactory<>("carb"));

			TableColumn<FoodItem, String> proteinCol1 = new TableColumn<>("Protein");
			proteinCol1.setMinWidth(10);
			proteinCol1.setCellValueFactory(new PropertyValueFactory<>("fiber"));

			TableColumn<FoodItem, String> fiberCol1 = new TableColumn<>("Fiber");
			fiberCol1.setMinWidth(10);
			fiberCol1.setCellValueFactory(new PropertyValueFactory<>("protein"));

			TableColumn<FoodItem, String> id1 = new TableColumn<>("ID");
			id1.setMinWidth(30);
			id1.setCellValueFactory(new PropertyValueFactory<>("ID"));

			customerTable.setItems(customerData);
			customerTable.getColumns().addAll(id1, foodNameCol1, caloriesCol1, fatCol1, carbCol1, fiberCol1,
					proteinCol1);

			/*
			 * User could add their preference items from the food list into the customer
			 * list
			 */

			final Button ACL1 = new Button("     Add to Meal List   ");

			ACL1.setOnAction((ActionEvent e) -> {

				ObservableList<FoodItem> selected;

				selected = table.getSelectionModel().getSelectedItems();

				customerData.add(new FoodItem(selected.get(0).getID(), selected.get(0).getName(),
						selected.get(0).getCalories(), selected.get(0).getFat(), selected.get(0).getCarb(),
						selected.get(0).getProtein(), selected.get(0).getFiber()));

			});

			/*
			 * User could delete item from the meal list
			 */

			final Button DCL2 = new Button("Delete from Meal List");
			DCL2.setOnAction((ActionEvent e) -> {
				ObservableList<FoodItem> foodItemSelected, allfoodItems;
				allfoodItems = customerTable.getItems();
				foodItemSelected = customerTable.getSelectionModel().getSelectedItems();
				foodItemSelected.forEach(allfoodItems::remove);
			});

			/*
			 * User could reset the list and clear everything
			 */
			final Button RCL3 = new Button("     Reset Meal List     ");
			RCL3.setOnAction((ActionEvent e) -> {
				customerTable.getItems().clear();

			});

			/*
			 * user could see the total nutrient they consume from different categories
			 */
			final Button ACL4 = new Button("   Analyze Meal List   ");

			ACL4.setOnAction((ActionEvent e) -> {
				customerlist.clear();
				int counter = 0;
				for (FoodItem eachItem : customerData) {
					customerlist.add(eachItem);
					counter++;
				}
				Double totalFiber = 0.0;
				Double totalCabs = 0.0;
				Double totalProtein = 0.0;
				Double totalCalories = 0.0;
				Double totalfat =0.0;
				for (int i = 0; i < counter; i++) {
					totalfat += Double.parseDouble(customerlist.get(i).getFat());
					totalProtein += Double.parseDouble(customerlist.get(i).getProtein());
					totalCabs += Double.parseDouble(customerlist.get(i).getCarb());
					totalCalories += Double.parseDouble(customerlist.get(i).getCalories());
					totalFiber += Double.parseDouble(customerlist.get(i).getFiber());
				}
				Label secondLabel = new Label("Total fat = " + totalfat + "\n" + "Total fiber = " + totalProtein
						+ "\n" + "Total protein = " + totalFiber + "\n" + "Total Calories = " + totalCalories + "\n"
						+ "Total carbonhydrate = " + totalCabs + "\n");

				StackPane secondaryLayout = new StackPane();
				secondaryLayout.getChildren().add(secondLabel);

				Scene secondScene = new Scene(secondaryLayout, 200, 100);

				Stage secondStage = new Stage();
				secondStage.setTitle("Total nutritions");
				secondStage.setScene(secondScene);

				secondStage.show();
			});

			/*
			 * set up for filter button function
			 */

			final Label label2 = new Label("Meal List");

			label2.setFont(Font.font("Verdana", 20));
			final Label nameLabel = new Label("Name");
			nameLabel.setLayoutX(filterX - 30);
			nameLabel.setLayoutY(filterY);

			final TextField nameBox = new TextField();
			nameBox.setPromptText("name");
			nameBox.setMaxWidth(foodNameCol.getPrefWidth());
			nameBox.setLayoutX(filterX + 70);
			nameBox.setLayoutY(filterY - 5);

			final Label calLabel = new Label("Calories");
			calLabel.setLayoutX(filterX - 30);
			calLabel.setLayoutY(filterY + 30);

			final Label calLabel1 = new Label("~");
			calLabel1.setLayoutX(filterX + 155);
			calLabel1.setLayoutY(filterY + 30);

			final TextField mincal = new TextField();
			mincal.setPromptText("minimun");
			mincal.setMaxWidth(foodNameCol.getPrefWidth());
			mincal.setLayoutX(filterX + 70);
			mincal.setLayoutY(filterY + 25);

			final TextField maxcal = new TextField();
			maxcal.setPromptText("maximun");
			maxcal.setMaxWidth(foodNameCol.getPrefWidth());
			maxcal.setLayoutX(filterX + 170);
			maxcal.setLayoutY(filterY + 25);

			final Label fiberLabel = new Label("Fiber");
			fiberLabel.setLayoutX(filterX - 30);
			fiberLabel.setLayoutY(filterY + 60);

			final Label proteinLabel1 = new Label("~");
			proteinLabel1.setLayoutX(filterX + 155);
			proteinLabel1.setLayoutY(filterY + 60);

			final TextField minprotein = new TextField();
			minprotein.setPromptText("minimun");
			minprotein.setMaxWidth(foodNameCol.getPrefWidth());
			minprotein.setLayoutX(filterX + 70);
			minprotein.setLayoutY(filterY + 55);

			final TextField maxprotein = new TextField();
			maxprotein.setPromptText("maximun");
			maxprotein.setMaxWidth(foodNameCol.getPrefWidth());
			maxprotein.setLayoutX(filterX + 170);
			maxprotein.setLayoutY(filterY + 55);

			final Label proteinLabel = new Label("Protein");
			proteinLabel.setLayoutX(filterX - 30);
			proteinLabel.setLayoutY(filterY + 90);

			final Label fiberLabel1 = new Label("~");
			fiberLabel1.setLayoutX(filterX + 155);
			fiberLabel1.setLayoutY(filterY + 90);

			final TextField minfiber = new TextField();
			minfiber.setPromptText("minimun");
			minfiber.setMaxWidth(foodNameCol.getPrefWidth());
			minfiber.setLayoutX(filterX + 70);
			minfiber.setLayoutY(filterY + 85);

			final TextField maxfiber = new TextField();
			maxfiber.setPromptText("maximun");
			maxfiber.setMaxWidth(foodNameCol.getPrefWidth());
			maxfiber.setLayoutX(filterX + 170);
			maxfiber.setLayoutY(filterY + 85);

			final Label fatLabel = new Label("Fat");
			fatLabel.setLayoutX(filterX - 30);
			fatLabel.setLayoutY(filterY + 120);

			final Label fatLabel1 = new Label("~");
			fatLabel1.setLayoutX(filterX + 155);
			fatLabel1.setLayoutY(filterY + 120);

			final TextField minfat = new TextField();
			minfat.setPromptText("minimun");
			minfat.setMaxWidth(foodNameCol.getPrefWidth());
			minfat.setLayoutX(filterX + 70);
			minfat.setLayoutY(filterY + 115);

			final TextField maxfat = new TextField();
			maxfat.setPromptText("maximun");
			maxfat.setMaxWidth(foodNameCol.getPrefWidth());
			maxfat.setLayoutX(filterX + 170);
			maxfat.setLayoutY(filterY + 115);

			final Label carbsLabel = new Label("Carbohydrate");
			carbsLabel.setLayoutX(filterX - 30);
			carbsLabel.setLayoutY(filterY + 150);

			final Label carbsLabel1 = new Label("~");
			carbsLabel1.setLayoutX(filterX + 155);
			carbsLabel1.setLayoutY(filterY + 150);

			final TextField mincarbs = new TextField();
			mincarbs.setPromptText("minimun");
			mincarbs.setMaxWidth(foodNameCol.getPrefWidth());
			mincarbs.setLayoutX(filterX + 70);
			mincarbs.setLayoutY(filterY + 145);

			final TextField maxcarbs = new TextField();
			maxcarbs.setPromptText("maximun");
			maxcarbs.setMaxWidth(foodNameCol.getPrefWidth());
			maxcarbs.setLayoutX(filterX + 170);
			maxcarbs.setLayoutY(filterY + 145);

			/*
			 * filter function : reset and filter condition test for every data negative
			 * integer or string value is not allow
			 */

			Button b2 = new Button("Reset");
			Button b3 = new Button("Filter");

			/*
			 * filter button function constantly update the list until all the rule has been
			 * applied
			 */

			b3.setOnAction((ActionEvent e) -> {

				table.getItems().clear();

				List<String> RulesList = new ArrayList<String>();
				List<FoodItem> filteredNameList = new ArrayList<FoodItem>();
				System.out.println(RulesList);
				Alert a = new Alert(AlertType.WARNING, "Please enter positive valeue");
				///
				if (!nameBox.getText().trim().isEmpty()) {

					String substring = nameBox.getText();
					filteredNameList = fooddata.filterByName(substring);

				}

				if (!mincal.getText().trim().isEmpty()) {
					if (isNumeric(mincal.getText())) {
						if (Double.parseDouble(mincal.getText()) >= 0) {
							String numberOfRule = mincal.getText();
							String addedRule = "calories >= " + numberOfRule;
							RulesList.add(addedRule);

						} else {
							a.show();
							mincal.clear();
						}
					} else {
						a.show();
						mincal.clear();
					}
				}
				if (!maxcal.getText().trim().isEmpty()) {
					if (isNumeric(maxcal.getText())) {
						if (Double.parseDouble(maxcal.getText()) >= 0) {
							String numberOfRule = maxcal.getText();
							String addedRule = "calories <= " + numberOfRule;
							RulesList.add(addedRule);
						} else {
							a.show();
							maxcal.clear();
						}
					} else {
						a.show();
						maxcal.clear();
					}
				}
				if (!minprotein.getText().trim().isEmpty()) {
					if (isNumeric(minprotein.getText())) {
						if (Double.parseDouble(minprotein.getText()) >= 0) {
							String numberOfRule = minprotein.getText();
							String addedRule = "protein >= " + numberOfRule;
							RulesList.add(addedRule);
						} else {
							a.show();
							minprotein.clear();
						}
					} else {
						a.show();
						minprotein.clear();
					}
				}
				if (!maxprotein.getText().trim().isEmpty()) {
					if (isNumeric(maxprotein.getText())) {
						if (Double.parseDouble(maxprotein.getText()) >= 0) {
							String numberOfRule = maxprotein.getText();
							String addedRule = "protein <= " + numberOfRule;
							RulesList.add(addedRule);
						} else {
							a.show();
							maxprotein.clear();
						}
					} else {
						a.show();
						maxprotein.clear();
					}
				}
				if (!minfiber.getText().trim().isEmpty()) {
					if (isNumeric(minfiber.getText())) {
						if (Double.parseDouble(minfiber.getText()) >= 0) {
							String numberOfRule = minfiber.getText();
							String addedRule = "fiber >= " + numberOfRule;
							RulesList.add(addedRule);
						} else {
							a.show();
							minfiber.clear();
						}
					} else {
						a.show();
						minfiber.clear();
					}
				}
				if (!maxfiber.getText().trim().isEmpty()) {
					if (isNumeric(maxfiber.getText())) {
						if (Double.parseDouble(maxfiber.getText()) >= 0) {
							String numberOfRule = maxfiber.getText();
							String addedRule = "fiber <= " + numberOfRule;
							RulesList.add(addedRule);
						} else {
							a.show();
							maxfiber.clear();
						}
					} else {
						a.show();
						maxfiber.clear();
					}
				}
				if (!minfat.getText().trim().isEmpty()) {
					if (isNumeric(minfat.getText())) {
						if (Double.parseDouble(minfat.getText()) >= 0) {
							String numberOfRule = minfat.getText();
							String addedRule = "fat >= " + numberOfRule;
							RulesList.add(addedRule);
						} else {
							a.show();
							minfat.clear();
						}
					} else {
						a.show();
						minfat.clear();
					}
				}
				if (!maxfat.getText().trim().isEmpty()) {
					if (isNumeric(maxfat.getText())) {
						if (Double.parseDouble(maxfat.getText()) >= 0) {
							String numberOfRule = maxfat.getText();
							String addedRule = "fat <= " + numberOfRule;
							RulesList.add(addedRule);
						} else {
							a.show();
							maxfat.clear();
						}
					} else {
						a.show();
						maxfat.clear();
					}
				}
				if (!mincarbs.getText().trim().isEmpty()) {
					if (isNumeric(mincarbs.getText())) {
						if (Double.parseDouble(mincarbs.getText()) >= 0) {
							String numberOfRule = mincarbs.getText();
							String addedRule = "carbohydrate >= " + numberOfRule;
							RulesList.add(addedRule);
						} else {
							a.show();
							mincarbs.clear();
						}
					} else {
						a.show();
						mincarbs.clear();
					}
				}
				if (!maxcarbs.getText().trim().isEmpty()) {
					if (isNumeric(maxcarbs.getText())) {
						if (Double.parseDouble(maxcarbs.getText()) >= 0) {
							String numberOfRule = maxcarbs.getText();
							String addedRule = "carbohydrate <= " + numberOfRule;
							RulesList.add(addedRule);
						} else {
							a.show();
							maxcarbs.clear();
						}
					} else {
						a.show();
						maxcarbs.clear();
					}
				}

				List<FoodItem> filterResult = new ArrayList<FoodItem>();

				if (nameBox.getText().trim().isEmpty() && RulesList.isEmpty()) {
					data.clear();
					listToData(data, fooddata.getAllFoodItems());
				}

				else if (nameBox.getText().trim().isEmpty() && !RulesList.isEmpty()) {
					data.clear();
					filterResult = fooddata.filterByNutrients(RulesList);
					listToData(data, filterResult);
				} else if (RulesList.isEmpty() && !nameBox.getText().trim().isEmpty()) {
					data.clear();
					listToData(data, filteredNameList);
				} else if (!RulesList.isEmpty() && !nameBox.getText().trim().isEmpty()) {
					data.clear();
					filterResult = fooddata.filterByNutrients(RulesList);
					filteredNameList.retainAll(filterResult);
					listToData(data, filteredNameList);
				}

			});

			/*
			 * clear any data in the filter section and reset the file that is loaded
			 */

			b2.setOnAction((ActionEvent e) -> {

				nameBox.clear();
				mincal.clear();
				maxcal.clear();
				minprotein.clear();
				maxprotein.clear();
				minfiber.clear();
				maxfiber.clear();
				minfat.clear();
				maxfat.clear();
				mincarbs.clear();
				maxcarbs.clear();

				data.clear();
				listToData(data, fooddata.getAllFoodItems());
			});

			hb2.getChildren().addAll(b2, b3);
			hb2.setSpacing(10);
			hb2.setLayoutX(filterX + 100);
			hb2.setLayoutY(filterY + 200);

			/*
			 * add all the vbox together , set it to scene : food list table, meal list,
			 * food list label, meal list lable,
			 */

			VBox vb3 = new VBox();
			vb3.setSpacing(10);
			vb3.setLayoutX(filterX);
			vb3.setLayoutY(filterY + 450);
			vb3.getChildren().addAll(ACL1, DCL2, RCL3, ACL4);

			final VBox vbox = new VBox();
			vbox.setSpacing(5);
			vbox.setPadding(new Insets(10, 0, 0, 10));
			vbox.getChildren().addAll(IObox, label, table, hb, label2, customerTable);

			((Group) scene.getRoot()).getChildren().addAll(vb3, vbox, nameLabel, nameBox, mincal, maxcal, calLabel,
					calLabel1, minprotein, maxprotein, proteinLabel, proteinLabel1, minfiber, maxfiber, fiberLabel,
					fiberLabel1, minfat, maxfat, fatLabel, fatLabel1, mincarbs, maxcarbs, carbsLabel, carbsLabel1, hb2);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void listToData(ObservableList<FoodItem> data, List<FoodItem> list1) {
		for (FoodItem list : list1) {
			data.add(new FoodItem(list.getID(), list.getName(), list.getCalories(), list.getFat(), list.getCarb(),
					list.getProtein(), list.getFiber()));
		}

	}

	public static void dataToList(ObservableList<FoodItem> data, List<FoodItem> list) {
		for (FoodItem eachItem : data) {
			list.add(eachItem);
		}
	}

	public static void data1ToData2(ObservableList<FoodItem> data1, ObservableList<FoodItem> data2) {
		for (FoodItem item : data1) {
			data2.add(item);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * helper method test whether the type is integer or not
	 */
	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
	}
}
