package main.java.recipemanager.common;

import recipemanager.DAL.SaladRecipeDAO;
import recipemanager.DAL.VegetableDAO;
import recipemanager.entities.SaladRecipe;

public class Main
{
    public static void main( String[] args )
    {
        //VegetableDAO vegetableDAO = new VegetableDAO();
        //vegetableDAO.create("Морковь", 25, 3); - работает
        //vegetableDAO.getById(2); - работает
        //vegetableDAO.getByTitle("Морковь"); - работает
        //vegetableDAO.getAllVegetables(); - работает
        //vegetableDAO.remove(2); - работает

        SaladRecipeDAO recipeDAO = new SaladRecipeDAO();
        //recipeDAO.getAllRecipes(); - работает
        //recipeDAO.create("Винегрет"); - работает, но проверить ещё
        //recipeDAO.remove(7); - работает
        //recipeDAO.getById(8); - работает


        //Chef chef = new Chef();
        //chef.showOptions();
    }
}
