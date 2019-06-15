package main.java.recipemanager.entities.vegetabletypes;

import main.java.recipemanager.entities.Vegetable;

public class SpicyVegetable extends Vegetable {

	public SpicyVegetable(int id, String title, double calories) {
		super(id, title, calories);
	}

	@Override
	public String toString() {
		return "Spicy " + super.toString();
	}
}
