package main.java.recipemanager.entities.vegetabletypes;

import main.java.recipemanager.entities.Vegetable;

public class RootVegetable extends Vegetable {

	public RootVegetable(int id, String title, double calories) {
		super(id, title, calories);
	}

	@Override
	public String toString() {
		return "Root "+super.toString();
	}
}