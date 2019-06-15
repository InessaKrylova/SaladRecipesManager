package main.java.recipemanager.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.recipemanager.entities.Ingredient;
import main.java.recipemanager.entities.SaladRecipe;
import main.java.recipemanager.custom_exceptions.CreatingElementException;

public class SaladRecipeDAO extends EntityDAO {
	private static final String CREATE_RECIPE = "INSERT INTO recipe(title) VALUES(?) RETURNING id;";
	private static final String GET_RECIPE_BY_ID = "SELECT * FROM recipe WHERE id=";
	private static final String REMOVE_RECIPE = "DELETE FROM recipe WHERE id=";

	public SaladRecipeDAO() {
		super();
	}

    public SaladRecipe create(String title) throws CreatingElementException {
    	SaladRecipe recipe =  null;
    	try (Connection connection = dataBaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_RECIPE)) {
    		statement.setString(1, title);
    		try (ResultSet rs = statement.executeQuery()) {
	            while(rs.next()) {
	            	 recipe = new SaladRecipe(rs.getInt("id"), title);
	            }
	    	} catch (SQLException | NullPointerException ex) {
				System.out.println(EXCEPTION_IN_RESULT_SET);
			}
	    } catch (SQLException ex) {
			System.out.println(EXCEPTION_IN_STATEMENT);
		}
    	if (recipe == null) throw new CreatingElementException("SaladRecipe");
    	return recipe;
    }
	
	public boolean remove(int id){
    	try (Connection connection = dataBaseConnector.getConnection();
             Statement statement = connection.createStatement()) {

			IngredientDAO ingredientDAO = new IngredientDAO();
			ingredientDAO.removeIngredientsWithRecipeId(id);

			statement.execute(REMOVE_RECIPE+id);
			return true;
        } catch (SQLException ex) {
			System.out.println(EXCEPTION_IN_STATEMENT);
			return false;
		}
    }               
    
    public SaladRecipe getById(int id) {
        SaladRecipe recipe = null;
        try(Connection connection = dataBaseConnector.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_RECIPE_BY_ID+id)){
            while (resultSet.next()){                    
            	List<Ingredient> ingredientList = new IngredientDAO().getIngredientsByRecipeId(id);
				recipe = new SaladRecipe(id, resultSet.getString("title"), ingredientList);
            }
        } catch (SQLException | NullPointerException e) {
        	System.out.println(EXCEPTION_IN_RESULT_SET);
        }
        return recipe;
    }
    
    public List<SaladRecipe> getAllRecipes() {
        List<SaladRecipe> list = new ArrayList<>();
        try(Connection connection = dataBaseConnector.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM recipe")){
            while (resultSet.next()){                    
            	list.add(new SaladRecipe(
            		resultSet.getInt("id"),
                    resultSet.getString("title")
            	)); 	            	
            }
		} catch (SQLException ex) {
			System.out.println(EXCEPTION_IN_RESULT_SET);
		}
        return list;
    }

}
