package main.java.recipemanager.DAL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import recipemanager.entities.Ingredient;
import recipemanager.entities.Vegetable;

public class IngredientDAO {
	private static final String EXCEPTION_IN_RESULTSET = "Exception while executing query / getting result set";
	private static final String EXCEPTION_IN_STATEMENT = "Exception while creating statement";
	private static final String GET_INGREDIENTS_BY_RECIPE = "SELECT * FROM ingredient WHERE recipe_id=";
	private static final String GET_INGREDIENT_BY_ID = "SELECT * FROM ingredient where id=";
	private static final String GET_ALL_INGREDIENTS = "SELECT * FROM ingredient";
	private static final String CREATE_INGREDIENT = "INSERT INTO ingredient(vegetable, recipe_id, count) VALUES(?, ?, ?) RETURNING id;";
	private static final String REMOVE_INGREDIENT = "DELETE FROM ingredient WHERE id=";
	private static final String REMOVE_INGREDIENTS_FROM_RECIPE = "DELETE FROM ingredient WHERE recipe_id=";
	
	public List<Ingredient> getIngredientsByRecipeId(int recipeId) {
        List<Ingredient> list = new ArrayList<>();
        try (Connection connection = DBConnector.openConnection();
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
    	try (Connection connection = DBConnector.openConnection();
    		 Statement statement = connection.createStatement();
    		 ResultSet resultSet = statement.executeQuery(GET_INGREDIENT_BY_ID +id)){
            while(resultSet.next()) {
				Vegetable vegetable = new VegetableDAO().getById(resultSet.getInt("vegetable"));
				ingredient = new Ingredient(
            			id,
						vegetable,
						resultSet.getDouble("weight"),
            			resultSet.getInt("recipeId")
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
        try(Connection connection = DBConnector.openConnection();
        	Statement statement = connection.createStatement();
        	ResultSet resultSet = statement.executeQuery(GET_ALL_INGREDIENTS)) {
            while(resultSet.next()) {
            	Vegetable vegetable = new VegetableDAO().getById(resultSet.getInt("vegetable"));
                list.add(new Ingredient(
            		resultSet.getInt("id"),
            		vegetable, resultSet.getDouble("weight"),
            		resultSet.getInt("recipe_id")
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
    
    /*public Ingredient create(int recipeId, int vegetableId, double weight) {
		Ingredient ingredient = null;
    	try (Connection connection = DBConnector.openConnection();
    		PreparedStatement statement = connection.prepareStatement(CREATE_INGREDIENT)) {
	        statement.setInt(1, ingredientId);
	        statement.setInt(2, recipeId);
	        statement.setDouble(3, count);
    		try (ResultSet resultSet = statement.executeQuery()){
	            while(resultSet.next()) {
					ingredient = new Ingredient(
	            			resultSet.getInt("id"),
	            			recipeId,
	            		new VegetableDAO().getById(ingredientId),
	            		count
	            	);
	            }
	        } catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (Exception ex) {
	    	System.out.println(EXCEPTION_IN_STATEMENT);
	    }
		System.out.println(rate == null
				? "Rate is not created"
				: rate.toString());
        return rate;
    }*/
    
	public void remove(int id){
    	try (Connection connection = DBConnector.openConnection();
    		 Statement statement = connection.createStatement()) {
	    	statement.execute(REMOVE_INGREDIENT +id);
	    	System.out.println("Rate with id="+id+" successfully removed");
        } catch (Exception ex) {
            System.out.println(EXCEPTION_IN_STATEMENT);
        }
    }

	public void removeIngredientsWithRecipeId(int recipeId){
		try (Connection connection = DBConnector.openConnection();
			 Statement statement = connection.createStatement()) {
			statement.execute(REMOVE_INGREDIENTS_FROM_RECIPE +recipeId);
			System.out.println("Ingredients successfully removed from recipe with id="+recipeId);
		} catch (Exception ex) {
			System.out.println(EXCEPTION_IN_STATEMENT);
		}
	}
}
