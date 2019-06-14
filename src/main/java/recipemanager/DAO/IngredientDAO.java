package main.java.recipemanager.DAO;
import main.java.recipemanager.datasources.DataBaseConnector;
import main.java.recipemanager.entities.Ingredient;
import main.java.recipemanager.entities.Vegetable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
	private static final String EXCEPTION_IN_RESULTSET = "Exception while executing query / getting result set";
	private static final String EXCEPTION_IN_STATEMENT = "Exception while creating statement";
	private static final String GET_INGREDIENTS_BY_RECIPE = "SELECT * FROM ingredient WHERE recipe_id=";
	private static final String GET_INGREDIENT_BY_ID = "SELECT * FROM ingredient where id=";
	private static final String GET_ALL_INGREDIENTS = "SELECT * FROM ingredient";
	private static final String CREATE_INGREDIENT = "INSERT INTO ingredient (vegetable_id, recipe_id, weight) VALUES(?, ?, ?) RETURNING id;";
	private static final String REMOVE_INGREDIENT = "DELETE FROM ingredient WHERE id=";
	private static final String REMOVE_INGREDIENTS_FROM_RECIPE = "DELETE FROM ingredient WHERE recipe_id=";
	
	public List<Ingredient> getIngredientsByRecipeId(int recipeId) {
        List<Ingredient> list = new ArrayList<>();
        try (Connection connection = DataBaseConnector.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(GET_INGREDIENTS_BY_RECIPE +recipeId)) {
             while(resultSet.next()) {
				 Vegetable vegetable = new VegetableDAO().getById(resultSet.getInt("vegetable"));
            	 list.add(new Ingredient(
                		resultSet.getInt("id"),
                		vegetable,
                		resultSet.getDouble("weight"),
						recipeId
                 ));
             }
        } catch (Exception ex) {
        	System.out.println(EXCEPTION_IN_RESULTSET);
        }
        
        for (Ingredient ingredient : list) {
			System.out.println(ingredient.toString());
		}
        return list;
    }

    public Ingredient getById(int id) {
		Ingredient ingredient = null;
    	try (Connection connection = DataBaseConnector.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(GET_INGREDIENT_BY_ID +id)){
            while(resultSet.next()) {
				Vegetable vegetable = new VegetableDAO().getById(resultSet.getInt("vegetable_id"));
				ingredient = new Ingredient(
            			id,
						vegetable,
						resultSet.getDouble("weight"),
            			resultSet.getInt("recipe_id")
                );
            }
        } catch (Exception ex) {
        	System.out.println(EXCEPTION_IN_RESULTSET);
        }
		System.out.println(ingredient == null
			? "Ingredient is not found"
			: ingredient.toString());
        return ingredient;
    }
    
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        try(Connection connection = DataBaseConnector.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(GET_ALL_INGREDIENTS)) {
            while(resultSet.next()) {
            	Vegetable vegetable = new VegetableDAO().getById(resultSet.getInt("vegetable_id"));
                list.add(new Ingredient(
            		resultSet.getInt("id"),
            		vegetable, resultSet.getDouble("weight"),
            		resultSet.getInt("recipe_id")
                ));
            }
        } catch (Exception ex) {
			System.out.println(EXCEPTION_IN_RESULTSET);
        }
        System.out.println("All ingredients:");
        for (Ingredient ingredient : list) {
			System.out.println(ingredient.toString());
		}
        return list;
    }
    
    public Ingredient create(int recipeId, int vegetableId, double weight) {
		Ingredient ingredient = null;
		System.out.println(CREATE_INGREDIENT);
    	try (Connection connection = DataBaseConnector.getConnection();
			 PreparedStatement statement = connection.prepareStatement(CREATE_INGREDIENT)) {
	        statement.setInt(1, vegetableId);
	        statement.setInt(2, recipeId);
	        statement.setDouble(3, weight);

    		try (ResultSet resultSet = statement.executeQuery()){
	            while(resultSet.next()) {
	            	Vegetable vegetable = new VegetableDAO().getById(vegetableId);
					ingredient = new Ingredient(
	            			resultSet.getInt("id"),
							vegetable,
							weight,
							recipeId
	            	);
	            }
	        } catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (Exception ex) {
	    	System.out.println(EXCEPTION_IN_STATEMENT);
	    }
		System.out.println(ingredient == null
				? "Ingredient is not created"
				: ingredient.toString());
        return ingredient;
    }
    
	public void remove(int id){
    	try (Connection connection = DataBaseConnector.getConnection();
			 Statement statement = connection.createStatement()) {
	    	statement.execute(REMOVE_INGREDIENT +id);
	    	System.out.println("Ingredient with id="+id+" successfully removed");
        } catch (Exception ex) {
            System.out.println(EXCEPTION_IN_STATEMENT);
        }
    }

	public void removeIngredientsWithRecipeId(int recipeId){
		try (Connection connection = DataBaseConnector.getConnection();
			 Statement statement = connection.createStatement()) {
			statement.execute(REMOVE_INGREDIENTS_FROM_RECIPE +recipeId);
			System.out.println("Ingredients successfully removed from recipe with id="+recipeId);
		} catch (Exception ex) {
			System.out.println(EXCEPTION_IN_STATEMENT);
		}
	}
}
