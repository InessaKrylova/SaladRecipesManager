package main.java.recipemanager.entities.vegetable_types;

import main.java.recipemanager.entities.Vegetable;

public class NightshadeVegetable extends Vegetable {

	public NightshadeVegetable(int id, String title, double calories) {
		super(id, title, calories);
	}

	@Override
	public String toString() {
		return "Nightshade "+super.toString();
	}
}