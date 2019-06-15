package main.java.recipemanager.ingredient_comparators;

import main.java.recipemanager.entities.Ingredient;

public class WeightComparator implements java.util.Comparator<Ingredient>{
	public int compare(Ingredient ingredient1, Ingredient ingredient2) {
        return (int) (ingredient1.getWeight() - ingredient2.getWeight());
    }
}