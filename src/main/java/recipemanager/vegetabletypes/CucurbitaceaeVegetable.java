package main.java.recipemanager.vegetabletypes;

import recipemanager.entities.Vegetable;

public class CucurbitaceaeVegetable extends Vegetable {

	public CucurbitaceaeVegetable(String title, double calories) {
		super(title, calories);
	}

	public CucurbitaceaeVegetable(int id, String title, double calories) {
		super(id, title, calories);
	}

	@Override
	public String toString() {
		return "Cucurbitaceae "+super.toString();
	}
}
