package main.java.recipemanager.ingredient_comparators;

import main.java.recipemanager.entities.Ingredient;

public class CaloriesComparator implements java.util.Comparator<Ingredient>{
	
	public int compare(Ingredient ingredient1, Ingredient ingredient2) {
        return (int) (ingredient1.getCaloricity() - ingredient2.getCaloricity());
    }
}