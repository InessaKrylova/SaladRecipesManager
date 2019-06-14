package main.java.recipemanager.PL;

import main.java.recipemanager.DAO.*;
import main.java.recipemanager.entities.SaladRecipe;
import main.java.recipemanager.entities.Vegetable;

import java.lang.reflect.Constructor;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Chef {
	private String chefName;
	private String restarauntTitle;
	private SaladRecipeDAO saladRecipeDAO;
	private IngredientDAO ingredientDAO;
	private VegetableDAO vegetableDAO;

	public Chef() {
		initializeChef();
		this.saladRecipeDAO = new SaladRecipeDAO();
		this.ingredientDAO = new IngredientDAO();
		this.vegetableDAO = new VegetableDAO();
	}



	private void initializeChef() {

		//TODO: считать из файла chefName и restarauntTitle
	}

	public void showOptions() {
		Scanner scanner = new Scanner(System.in);
		Options choice = Options.valueOf(-1);

		System.out.println("Hello! My name is " + chefName + ". Welcome to "+restarauntTitle+"!");
		System.out.println("What do you want to cook today?");

		while (!choice.equals(Options.Exit)) {
			System.out.println("\nChoose one of the options:");
			System.out.println("1. Show all salad recipes");
			System.out.println("2. Show recipe with concrete id");
			System.out.println("3. Add/edit/remove recipe");
			System.out.println("4. Add/edit/remove ingredient in concrete recipe");
			System.out.println("5. Sort ingredients by calories");
			System.out.println("6. Sort ingredients by weight");
			System.out.println("7. Get ingredients for calories");
			System.out.println("0. Exit");

			try {
				choice = Options.valueOf(scanner.nextInt());
			}
			catch (InputMismatchException e) {
				System.out.println("Wrong option!");
				scanner.next();
				choice = Options.valueOf(-1);
			}

			switch(choice) {
				case ShowAllRecipes:
					showAllRecipes();
					break;

				case ShowRecipeWithId:
					System.out.print("Enter recipe id:");
					int id = scanner.nextInt();
					showRecipeWithId(id);
					break;

				case AddEditRemoveRecipe:

					break;

				case AddEditRemoveIngredient:

					break;

				case SortIngredientsByCalories:
					showIngredientsForCalories(scanner);
					break;

				case SortIngredientsByWeight:
					showIngredientsForCalories(scanner);
					break;

				case GetIngredientsForCalories:

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

	private void showRecipeWithId(int id) {
		SaladRecipe recipe = saladRecipeDAO.getById(id);
		System.out.println(recipe == null
				? "SaladRecipe with id="+id+" is not found"
				: recipe.toString());
	}

	public Vegetable getIngredient(Scanner scanner) {
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

			return null;
		}

		return makeWithReflection(ingredientName, weight);
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
		
		System.out.println("Enter the lower limit:");
		try {
			lowerCalories = scanner.nextDouble();
		}
		catch (InputMismatchException e) {
			System.out.println("Wrong input!");
			scanner.next();
			
			return;
		}
		
		System.out.println("Enter the upper limit:");
		try {
			upperCalories = scanner.nextDouble();
		}
		catch (InputMismatchException e) {
			System.out.println("Wrong input!");
			scanner.next();
			
			return;
		}
		
		//salad.showIngredientsByCalories(lowerCalories, upperCalories);
	}
}