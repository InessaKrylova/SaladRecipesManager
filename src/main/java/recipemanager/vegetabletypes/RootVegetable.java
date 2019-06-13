package main.java.recipemanager.vegetabletypes;

import recipemanager.entities.Vegetable;

public class RootVegetable extends Vegetable {

	public RootVegetable(String title, double calories) {
		super(title, calories);
	}

	public RootVegetable(int id, String title, double calories) {
		super(id, title, calories);
	}

	@Override
	public String toString() {
		return "Root "+super.toString();
	}
}