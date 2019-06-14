package main.java.recipemanager.PL;

import java.util.HashMap;
import java.util.Map;

public enum Options {
    ShowAllRecipes (1),
    ShowRecipeWithId (2),
    AddEditRemoveRecipe(3),
    AddEditRemoveIngredient(4),
    SortIngredientsByCalories(5),
    SortIngredientsByWeight(6),
    GetIngredientsForCalories(7),
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
