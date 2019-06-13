package main.java.recipemanager.entities;

import main.java.recipemanager.helpers.CaloriesComparator;
import main.java.recipemanager.helpers.WeightComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaladRecipe extends Entity {

    private List<Ingredient> ingredients;
    private String title;
    private int caloricity;
    
    public SaladRecipe(int id, String title) {
		super(id);
		this.title = title;
		this.ingredients = new ArrayList<>();
	}

    public SaladRecipe() {
    	super();
		this.ingredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient) {
    	this.ingredients.add(ingredient);
    }
    
    public void removeIngredient(int ingredientId) {
    	 for (int i = 0; i < this.ingredients.size(); i++) {
             if (this.ingredients.get(i).getId() == ingredientId) {
            	 this.ingredients.remove(i); break;
             }
         }
    }

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCaloricity() {
		for (Ingredient ingredient : ingredients) {
			caloricity += ingredient.getCaloricity();
		}
		return caloricity;
	}

	public void sortIngredientsByCalories() {
		Collections.sort(ingredients, new CaloriesComparator());
	}

	public void sortIngredientsByWeight() {
		Collections.sort(ingredients, new WeightComparator());
	}

	public void showIngredientsByCalories(double lowerCalories,
										  double upperCalories) {
		System.out.println("Ingredients for calories ["
				+ lowerCalories + ", " + upperCalories + "]");
		for (Ingredient ingredient : ingredients) {
			double calories = ingredient.getCaloricity();
			if (calories >= lowerCalories && calories <= upperCalories) {
				Vegetable vegetable = ingredient.getVegetable();
				System.out.println(
						vegetable.getTitle() + ", "
						+ vegetable.getCaloricityPer100g() + "kcal per 100g, "
						+ ingredient.getCaloricity() + "kcal in this recipe");
			}
		}
	}

	@Override
	public String toString() {
		return "SaladRecipe { " +
				"id=" + super.getId() +
				", ingredients=" + ingredients +
				", title='" + title + '\'' +
				", caloricity=" + caloricity +
				" }";
	}
}
