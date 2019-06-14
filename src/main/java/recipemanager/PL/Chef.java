package main.java.recipemanager.PL;

import main.java.recipemanager.DAO.*;
import main.java.recipemanager.datasources.FileConnector;
import main.java.recipemanager.entities.SaladRecipe;
import main.java.recipemanager.entities.Vegetable;

import java.lang.reflect.Constructor;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Chef {
	///<editor-fold desc="ready">
	private String staffName;
	private String staffPosition;
	private String restaurantTitle;

	private SaladRecipeDAO saladRecipeDAO;
	private IngredientDAO ingredientDAO;
	private VegetableDAO vegetableDAO;
	private Scanner scanner;
	private int choice;

	public Chef() {
		choice = -1;
		initializeChef();
		this.saladRecipeDAO = new SaladRecipeDAO();
		this.ingredientDAO = new IngredientDAO();
		this.vegetableDAO = new VegetableDAO();
		scanner = new Scanner(System.in);
	}

	private void initializeChef() {
		FileConnector fileConnector = new FileConnector();
		if (fileConnector.openConnection()) {
			Map<String, String> data = fileConnector.getFileContent();
			staffName = data.get("staffFirstName")+" "+data.get("staffLastName");
			staffPosition = data.get("staffPosition");
			restaurantTitle = data.get("restaurantTitle");

			String welcome = String.format(
					"Hello! My name is %s, and I`m a %s of this restaurant. Welcome to \"%s\" restaurant cooking courses!",
					staffName, staffPosition, restaurantTitle
			);
			System.out.println(welcome);
		}
	}

	private void getUserChoice() {
		try {
			choice = scanner.nextInt();
		}
		catch (InputMismatchException e) {
			System.out.println("Wrong option!");
			scanner.next();
			choice = -1;
		}
	}

	public void showOptions() {
		while (choice != 0) {
			System.out.println("\nChoose one of the options:");
			System.out.println("\t1. Show all salad recipes");
			System.out.println("\t2. Show recipe with concrete id");
			System.out.println("\t3. Manage recipe (add/edit/remove)");
			System.out.println("\t4. Manage ingredient in concrete recipe (add/edit/remove)");
			System.out.println("\t5. Sort ingredients by calories");
			System.out.println("\t6. Sort ingredients by weight");
			System.out.println("\t7. Get ingredients for calories");
			System.out.println("\t0. Exit");

			getUserChoice();
			Options option = Options.valueOf(choice);

			switch(option) {
				case ShowAllRecipes:
					showAllRecipes();
					break;

				case ShowRecipeWithId:
					showRecipeWithId();
					break;

				case AddEditRemoveRecipe:
					manageRecipe();
					break;

				case AddEditRemoveIngredient:
					manageIngredient();
					break;

				case SortIngredientsByCalories:
					sortIngredientsByCalories();
					break;

				case SortIngredientsByWeight:
					sortIngredientsByWeight();
					break;

				case GetIngredientsForCalories:
					showIngredientsForCalories(scanner);
					break;

				case Exit:
					System.exit(0);
					break;

				default:
					break;
			}
		}

		scanner.close();
	}

	private void showAllRecipes() {
		List<SaladRecipe> list = saladRecipeDAO.getAllRecipes();
		System.out.println("All salad recipes:");
		for (SaladRecipe recipe : list) {
			System.out.println("\t"+recipe.getId()+". "+recipe.getTitle());
		}
	}

	private void showRecipeWithId() {
		System.out.print("Enter recipe id:");
		int recipeId = scanner.nextInt();
		SaladRecipe recipe = saladRecipeDAO.getById(recipeId);
		System.out.println(recipe == null
				? "SaladRecipe with id="+recipeId+" is not found"
				: recipe.toString());
	}
	///</editor-fold>

	private void sortIngredientsByWeight() {
		//TODO
		System.out.print("Enter recipe id:");
		int recipeId = scanner.nextInt();
	}

	private void sortIngredientsByCalories() {
		//TODO
		System.out.print("Enter recipe id:");
		int recipeId = scanner.nextInt();
	}

	private void manageIngredient() {
		System.out.print("Enter recipe id:");
		int recipeId = scanner.nextInt();
		System.out.print("Enter ingredient id:");
		int ingredientId = scanner.nextInt();
		//TODO


		String ingredientName;
		double weight;

		System.out.println("Enter the ingredient name:");
		ingredientName = scanner.next();

		System.out.println("Enter the weight (in gramms):");

		try {
			weight = scanner.nextDouble();
		} catch (InputMismatchException e) {
			System.out.println("Wrong weight!");
			scanner.next();

			//return null;
		}

		//return makeWithReflection(ingredientName, weight);

	}

	private void manageRecipe() {
		choice = -1;
		System.out.println("\nChoose one of the options:");
		System.out.println("\t1. Add new recipe");
		System.out.println("\t2. Edit existing recipe");
		System.out.println("\t3. Remove existing recipe");
		System.out.println("\t0. Back to all options");

		getUserChoice();

		switch (choice) {
			case 1:
				addNewRecipe();
				break;
			case 2:
				editRecipe();
				break;
			case 3:
				removeRecipe();
				break;
			case 0:
				showOptions();
				break;
			default:
				break;
		}
	}

	private void removeRecipe() {
		//TODO
		System.out.print("Enter recipe id:");
		int recipeId = scanner.nextInt();
	}

	private void editRecipe() {
		//TODO

		int recipeId = scanner.nextInt();
	}

	private void addNewRecipe() {
		System.out.print("Enter recipe title :");
		String title = scanner.nextLine(); //TODO проверочка на корректность ввода + тут лага с названием с пробелом
		SaladRecipe newRecipe = saladRecipeDAO.create(title);
		System.out.println();
		System.out.println(newRecipe == null
				? "SaladRecipe is not created"
				: "New empty " + newRecipe.toString() + " is successfully created");
	}

	@Deprecated
	public Vegetable makeWithReflection(String ingrName, double weight) {
		// create new vegetable using reflection
		try {
			Class [] parameters = {double.class};
			String ingredientName = "ua.kiev.univ.chef." + ingrName;
			Class ingredientClass = Class.forName(ingredientName);
			Constructor constructor =
					ingredientClass.getDeclaredConstructor(parameters);
			Vegetable vegetable = (Vegetable) constructor.newInstance(
					new Object[]{new Double(weight)});

			return vegetable;
		}
		catch (Exception e) {
			System.out.println("No such ingredient!");

			return null;
		}
	}

	public void showIngredientsForCalories(Scanner scanner) {
		double lowerCalories, upperCalories;
		
		System.out.print("Enter the lower limit:");
		try {
			lowerCalories = scanner.nextDouble();
		}
		catch (InputMismatchException e) {
			System.out.println("Wrong input!");
			scanner.next();
			
			return;
		}
		
		System.out.print("Enter the upper limit:");
		try {
			upperCalories = scanner.nextDouble();
		}
		catch (InputMismatchException e) {
			System.out.println("Wrong input!");
			scanner.next();
			
			return;
		}

		System.out.print("Enter recipe id:");
		int recipeId = scanner.nextInt();
		//saladRecipeDAO.showIngredientsByCalories(lowerCalories, upperCalories);
	}
}