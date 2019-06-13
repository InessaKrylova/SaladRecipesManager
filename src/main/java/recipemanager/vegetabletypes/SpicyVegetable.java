package main.java.recipemanager.vegetabletypes;

import recipemanager.entities.Vegetable;

public class SpicyVegetable extends Vegetable {
	
	public SpicyVegetable(String title, double calories) {
		super(title, calories);
	}

	public SpicyVegetable(int id, String title, double calories) {
		super(id, title, calories);
	}

	@Override
	public String toString() {
		return "Spicy " + super.toString();
	}
}
