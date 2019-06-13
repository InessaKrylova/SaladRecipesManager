package main.java.recipemanager.common;

import recipemanager.DAL.IngredientDAO;
import recipemanager.DAL.SaladRecipeDAO;
import recipemanager.DAL.VegetableDAO;
import recipemanager.entities.Vegetable;

import java.lang.reflect.Constructor;
import java.util.InputMismatchException;
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
		int choice = -1;

		System.out.println("Hello! My name is " + chefName + ". Welcome to "+restarauntTitle+"!");
		System.out.println("What do you want to cook today?");

		while (choice != 0) {
			System.out.println("\nChoose one of the options:");
			System.out.println("1. Rename salad");
			System.out.println("2. Show recipe");
			System.out.println("3. Add ingredient");
			System.out.println("4. Sort ingredients by calories");
			System.out.println("5. Sort ingredients by weight");
			System.out.println("6. Get ingredients for calories");
			System.out.println("0. Exit");

			try {
				choice = scanner.nextInt();
			}
			catch (InputMismatchException e) {
				System.out.println("Wrong option!");
				scanner.next();
				choice = -1;
			}

			switch(choice) {
				case 1:

					System.out.println("Name your salad:");
					//salad.setname(scanner.next());
					break;

				case 2:
					//salad.showRecipe();
					break;

				case 3:
					/*vegetable = getIngredient(scanner);
					if (vegetable != null) {
						if (!salad.addIngredient(vegetable)) {
							System.out.println("Cannot add ingredient!");
						}
					}*/
					break;

				case 4:
					//salad.sortIngredientsByCalories();
					break;

				case 5:
					//salad.sortIngredientsByWeight();
					break;

				case 6:
					showIngredientsForCalories(scanner);
					break;

				case 0:
					System.exit(0);
					break;

				default:
					break;
			}
		}

		scanner.close();
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