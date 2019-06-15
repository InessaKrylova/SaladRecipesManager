package main.java.recipemanager.PL;

import main.java.recipemanager.DAO.*;
import main.java.recipemanager.datasources.FileConnector;
import main.java.recipemanager.entities.Ingredient;
import main.java.recipemanager.entities.SaladRecipe;
import main.java.recipemanager.entities.Vegetable;

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
			System.out.println("\t3. Add new recipe");
			System.out.println("\t4. Remove existing recipe");
			System.out.println("\t5. Add new ingredient to recipe");
			System.out.println("\t6. Remove ingredient from recipe");
			System.out.println("\t7. Sort ingredients by calories");
			System.out.println("\t8. Sort ingredients by weight");
			System.out.println("\t9. Get ingredients for calories");
			System.out.println("\t0. Exit");

			getUserChoice();
			Options option = Options.valueOf(choice);

			switch(option) {
				case ShowAllRecipes:
					showAllRecipes();
					break;

				case ShowRecipeWithId:
					System.out.print("Enter recipe id: ");
					int recipeId = scanner.nextInt();
					showRecipeWithId(recipeId);
					break;

				case AddRecipe:
					addNewRecipe();
					break;

				case RemoveRecipe:
					removeRecipe();
					break;

				case AddIngredient:
					addNewIngredient();
					break;

				case RemoveIngredient:
					removeIngredient();
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

	private void showRecipeWithId(int recipeId) {
		SaladRecipe recipe = saladRecipeDAO.getById(recipeId);
		System.out.println(recipe == null
				? "SaladRecipe with id="+recipeId+" is not found"
				: recipe.toString());
	}

	private void removeRecipe() {
		System.out.print("Enter recipe id: ");
		int recipeId = scanner.nextInt();
		System.out.println(saladRecipeDAO.remove(recipeId)
				? "SaladRecipe with id="+recipeId+" successfully removed"
				: "SaladRecipe with id="+recipeId+" is not removed !"
		);
	}

	private void sortIngredientsByWeight() {
		System.out.print("Enter recipe id: ");
		int recipeId = scanner.nextInt();
		SaladRecipe salad = saladRecipeDAO.getById(recipeId);
		salad.sortIngredientsByWeight();
		System.out.println("Ingredients in recipe with id=" + recipeId + " successfully sorted by weight:");
		System.out.println(salad.ingredientsToString());
	}

	private void sortIngredientsByCalories() {
		System.out.print("Enter recipe id: ");
		int recipeId = scanner.nextInt();
		SaladRecipe salad = saladRecipeDAO.getById(recipeId);
		salad.sortIngredientsByCalories();
		System.out.println("Ingredients in recipe with id=" + recipeId + " successfully sorted by calories:");
		System.out.println(salad.ingredientsToString());
	}

	private void addNewIngredient() {
		showAllRecipes();
		System.out.print("Enter recipe id: ");
		int recipeId = scanner.nextInt();

		double weight = 0.0;
		showRecipeWithId(recipeId);

		List<Vegetable> vegetableList = vegetableDAO.getAllVegetables();
		System.out.println("All vegetables:");
		for (Vegetable vegetable : vegetableList) {
			System.out.println("\t"+vegetable.toString());
		}
		System.out.print("Enter vegetable id: ");
		int vegetableId = scanner.nextInt();
		if (vegetableDAO.getById(vegetableId)== null) {
			System.out.println("Wrong vegetable id!");
		} else {
			System.out.print("Enter the weight of new ingredient (in gramms): ");
			try {
				weight = scanner.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("Wrong weight!");
				scanner.next();
			}

			Ingredient newIngredient = ingredientDAO.create(recipeId, vegetableId, weight);
			System.out.println(newIngredient == null
					? "Ingredient is not created!"
					: "Ingredient is successfully created: \n\t" + newIngredient.toString());
		}
	}

	private void removeIngredient() {
		showAllRecipes();
		System.out.print("Enter recipe id: ");
		int recipeId = scanner.nextInt();
		showRecipeWithId(recipeId);

		System.out.print("Enter ingredient id: ");
		int ingredientId = scanner.nextInt();
		System.out.println(ingredientDAO.remove(ingredientId)
				? "Ingredient successfully removed from recipe with id="+recipeId
				: "Ingredient is not removed!");
	}
	///</editor-fold>

	private void addNewRecipe() {
		System.out.print("Enter recipe title: ");
		String title = new Scanner(System.in).nextLine();
		SaladRecipe newRecipe = saladRecipeDAO.create(title);
		System.out.println();
		System.out.println(newRecipe == null
				? "SaladRecipe is not created"
				: "New empty " + newRecipe.toString() + " is successfully created");
	}

	public void showIngredientsForCalories(Scanner scanner) {
		//TODO проверить исправить
		double lowerCalories, upperCalories;
		
		System.out.print("Enter the lower limit: ");
		try {
			lowerCalories = scanner.nextDouble();
		}
		catch (InputMismatchException e) {
			System.out.println("Wrong input!");
			scanner.next();
			
			return;
		}
		
		System.out.print("Enter the upper limit: ");
		try {
			upperCalories = scanner.nextDouble();
		}
		catch (InputMismatchException e) {
			System.out.println("Wrong input!");
			scanner.next();
			
			return;
		}

		System.out.print("Enter recipe id: ");
		int recipeId = scanner.nextInt();


	}
}