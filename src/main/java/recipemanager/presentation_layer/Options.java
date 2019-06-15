package main.java.recipemanager.presentation_layer;

import java.util.HashMap;
import java.util.Map;

public enum Options {
    ShowAllRecipes (1),
    ShowRecipeWithId (2),
    AddRecipe(3),
    RemoveRecipe(4),
    AddIngredient(5),
    RemoveIngredient(6),
    SortIngredientsByCalories(7),
    SortIngredientsByWeight(8),
    GetIngredientsForCalories(9),
    Exit(0),
    WrongChoice(-1);

    private int value;
    private static Map map = new HashMap<>();

    Options(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    static {
        for (Options option : Options.values()) {
            map.put(option.value, option);
        }
    }

    public static Options valueOf(int value) {
        return (Options) map.get(value);
    }
}
