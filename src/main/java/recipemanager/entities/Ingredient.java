package main.java.recipemanager.entities;

import java.util.List;

public class Ingredient extends Entity {
   	private Vegetable vegetable;
   	private int recipeId;
    private int caloricity;
	private double weight;

    public Ingredient(int id, Vegetable vegetable, double weight, int recipeId) {
		super(id);
		this.vegetable = vegetable;
		this.weight = weight;
		this.recipeId = recipeId;
		this.caloricity = (int) (vegetable.getCaloricityPer100g() * (weight/100));
	}

	public Vegetable getVegetable() {
		return vegetable;
	}

	public void setVegetable(Vegetable vegetable) {
		this.vegetable = vegetable;
	}

	public int getCaloricity() {
		return caloricity;
	}

	public double getWeight() {
		return weight;
	}

	public int getRecipeId() {
		return recipeId;
	}

	@Override
	public String toString() {
		 StringBuilder stringBuilder = new StringBuilder("");
		 stringBuilder.append("Ingredient { ")
					   .append("vegetableId=")
				       .append(vegetable.getId())
				       .append(", recipeId=")
				 	   .append(recipeId)
					   .append(", caloricity=")
				 	   .append(caloricity)
					   .append(", weight=")
				 	   .append(weight)
				 	   .append(" }");
		 return stringBuilder.toString();
	}
}