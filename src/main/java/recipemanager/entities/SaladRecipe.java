package main.java.recipemanager.entities;

import main.java.recipemanager.ingredient_comparators.CaloriesComparator;
import main.java.recipemanager.ingredient_comparators.WeightComparator;

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

	public SaladRecipe(int id, String title, List<Ingredient> list) {
		super(id);
		this.title = title;
		this.ingredients = list;
		for (Ingredient ingredient : list) {
			this.caloricity += ingredient.getCaloricity();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void sortIngredientsByCaloricity() {
    	Collections.sort(ingredients, new CaloriesComparator());
	}

	public void sortIngredientsByWeight() {
		Collections.sort(ingredients, new WeightComparator());
	}

	public void findIngredientsByCaloriesInterval(double lowerCalories,
                                                  double upperCalories) {
		System.out.println("Ingredients for calories with interval ["
				+ lowerCalories + "<...<" + upperCalories + "]:");
		for (Ingredient ingredient : ingredients) {
			double calories = ingredient.getCaloricity();
			if (calories >= lowerCalories && calories <= upperCalories) {
				Vegetable vegetable = ingredient.getVegetable();
				System.out.println(
						"\t" + vegetable.getTitle() + ": "
						+ vegetable.getCaloricityPer100g() + " kcal per 100g, "
						+ ingredient.getCaloricity() + " kcal in this recipe");
			}
		}
	}

	@Override
	public String toString() {
		 StringBuilder stringBuilder = new StringBuilder("");
    	 stringBuilder.append("SaladRecipe { ")
				 .append("\n\tid = ")
				 .append(super.getId())
				 .append(",")
				 .append("\n\tingredients = List{")
				 .append(ingredientsToString())
				 .append("},")
				 .append("\n\ttitle = \'")
				 .append(title)
				 .append("\',")
				 .append("\n\tcaloricity = ")
				 .append(caloricity)
				 .append("\n}");
    	 return stringBuilder.toString();
	}

	public String ingredientsToString() {
		StringBuilder stringBuilder = new StringBuilder("");
		if (!ingredients.isEmpty()) {
			stringBuilder.append("\n");
			for (Ingredient ingredient : ingredients) {
				stringBuilder.append("\t\t")
						.append(ingredient.toString())
						.append("\n");
			}
			stringBuilder.append("\t");
		}
		return stringBuilder.toString();
	}

}
