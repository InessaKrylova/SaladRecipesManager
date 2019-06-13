package main.java.recipemanager.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.recipemanager.entities.Ingredient;
import main.java.recipemanager.entities.SaladRecipe;

public class SaladRecipeDAO {
	
	private static final String EXCEPTION_IN_RESULT_SET = "Exception while executing query / getting result set";
	private static final String EXCEPTION_IN_STATEMENT = "Exception while creating statement";
	private static final String CREATE_RECIPE = "INSERT INTO recipe(title) VALUES(?) RETURNING id;";
	private static final String GET_RECIPE_BY_ID = "SELECT * FROM recipe WHERE id=";
	private static final String REMOVE_RECIPE = "DELETE FROM recipe WHERE id=";
	
    public SaladRecipe create(String title) {
    	SaladRecipe recipe =  null;
    	try (Connection connection = DBConnector.openConnection();
    		 PreparedStatement statement = connection.prepareStatement(CREATE_RECIPE)) {
    		statement.setString(1, title);
    		try (ResultSet rs = statement.executeQuery()) {
	            while(rs.next()) {
	            	 recipe = new SaladRecipe(rs.getInt("id"), title);
	            }
	    	} catch (SQLException | NullPointerException ex) {
	        	System.out.println(EXCEPTION_IN_RESULT_SET);
	        }
	    } catch (Exception ex) {
	    	System.out.println(EXCEPTION_IN_STATEMENT);
	    }
		System.out.println(recipe == null
			? "SaladRecipe is not created"
			: recipe.toString());
    	return recipe;
    }
	
	public void remove(int id){
    	try (Connection connection = DBConnector.openConnection();
			 Statement statement = connection.createStatement()) {

			IngredientDAO ingredientDAO = new IngredientDAO();
			ingredientDAO.removeIngredientsWithRecipeId(id);

			statement.execute(REMOVE_RECIPE+id);
	    	System.out.println("SaladRecipe with id="+id+" successfully removed");
        } catch (Exception ex) {
            System.out.println(EXCEPTION_IN_STATEMENT);
        }
    }               
    
    public SaladRecipe getById(int id) {
        SaladRecipe recipe = null;
        try(Connection connection = DBConnector.openConnection();
        	Statement statement = connection.createStatement();
        	ResultSet resultSet = statement.executeQuery(GET_RECIPE_BY_ID+id)){
            while (resultSet.next()){                    
            	recipe = new SaladRecipe(id, resultSet.getString("title"));
            }
        } catch (Exception ex) {
        	System.out.println(EXCEPTION_IN_RESULT_SET);
        }

		System.out.println(recipe == null
			? "SaladRecipe is not found"
			: recipe.toString());
        return recipe;
    }
    
    public List<SaladRecipe> getAllRecipes() {
        List<SaladRecipe> list = new ArrayList<>();
        try(Connection connection = DBConnector.openConnection();
        	Statement statement = connection.createStatement();
        	ResultSet resultSet = statement.executeQuery("SELECT * FROM recipe")){
            while (resultSet.next()){                    
            	list.add(new SaladRecipe(
            		resultSet.getInt("id"),
                    resultSet.getString("title")
            	)); 	            	
            }
        } catch (Exception ex) {
        	System.out.println(EXCEPTION_IN_RESULT_SET);
        }
        System.out.println("All salad recipes:");
        for (SaladRecipe recipe : list) {
        	System.out.println("\t"+recipe.getId()+". "+recipe.getTitle());
		}
        return list;
    }

}
