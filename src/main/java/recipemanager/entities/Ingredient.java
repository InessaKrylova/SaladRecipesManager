package main.java.recipemanager.entities;

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

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	@Override
	public String toString() {
		return "Ingredient { " +
				"vegetableId=" + vegetable.getId() +
				", recipeId=" + recipeId +
				", caloricity=" + caloricity +
				", weight=" + weight +
				" }";
	}
}