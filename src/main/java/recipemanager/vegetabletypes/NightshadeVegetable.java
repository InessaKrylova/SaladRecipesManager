package main.java.recipemanager.vegetabletypes;

import recipemanager.entities.Vegetable;

public class NightshadeVegetable extends Vegetable {
	
	public NightshadeVegetable(String title, double calories) {
		super(title, calories);
	}

	public NightshadeVegetable(int id, String title, double calories) {
		super(id, title, calories);
	}

	@Override
	public String toString() {
		return "Nightshade "+super.toString();
	}
}