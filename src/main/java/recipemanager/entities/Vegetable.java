package main.java.recipemanager.entities;

import recipemanager.vegetabletypes.*;

public class Vegetable extends Entity {
	private String title;
	private double caloricityPer100g;
	private String category;
	
	public Vegetable(String title, double calories) {
		if (calories < 0) {
			throw new IllegalArgumentException("Cannot create vegetable with " + calories + "kcal");
		}
		
		this.title = title;
		this.caloricityPer100g = calories;
	}

	 public Vegetable(int id, String title, double calories) {
		super(id);
		if (calories < 0) {
			 throw new IllegalArgumentException("Cannot create vegetable with " + calories + "kcal");
		 }

		 this.title = title;
		 this.caloricityPer100g = calories;
	 }

	 public static Vegetable getVegetableWithCategory(int category, int id, String title, int caloricityPer100g) {
		Vegetable vegetable = null;
		switch(category) {
			case 1:
				vegetable = new CucurbitaceaeVegetable(id, title, caloricityPer100g);
				break;
			case 2:
				vegetable = new NightshadeVegetable(id, title, caloricityPer100g);
				break;
			case 3:
				vegetable = new RootVegetable(id, title, caloricityPer100g);
				break;
			case 4:
				vegetable = new SpicyVegetable(id, title, caloricityPer100g);
				break;
				default:
					vegetable = new Vegetable(id, title, caloricityPer100g);
					break;
		}
		return vegetable;
	 }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getCaloricityPer100g() {
		return caloricityPer100g;
	}

	public void setCaloricityPer100g(double caloricityPer100g) {
		this.caloricityPer100g = caloricityPer100g;
	}

	 public String getCategory() {
		 return category;
	 }

	 public void setCategory(String category) {
		 this.category = category;
	 }

	@Override
	public String toString() {
		return "Vegetable{ " +
				"title='" + title + '\'' +
				", caloricityPer100g=" + caloricityPer100g +
				" }";
	}
}