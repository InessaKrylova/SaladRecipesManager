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

	@Override
	public String toString() {
		 StringBuilder stringBuilder = new StringBuilder("");
		 stringBuilder.append("Ingredient { ")
				       .append("id=")
				       .append(super.getId())
					   .append(", vegetable=")
				       .append(vegetable.getTitle())
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