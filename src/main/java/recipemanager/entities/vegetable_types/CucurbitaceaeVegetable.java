package main.java.recipemanager.entities.vegetable_types;

import main.java.recipemanager.entities.Vegetable;

public class CucurbitaceaeVegetable extends Vegetable {

	public CucurbitaceaeVegetable(int id, String title, double calories) {
		super(id, title, calories);
	}

	@Override
	public String toString() {
		return "Cucurbitaceae "+super.toString();
	}
}
