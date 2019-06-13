package main.java.recipemanager.vegetabletypes;

import main.java.recipemanager.entities.Vegetable;

public class CabbageVegetable extends Vegetable {
    public CabbageVegetable(String title, double calories) {
        super(title, calories);
    }

    public CabbageVegetable(int id, String title, double calories) {
        super(id, title, calories);
    }

    @Override
    public String toString() {
        return "Cabbage "+super.toString();
    }
}
