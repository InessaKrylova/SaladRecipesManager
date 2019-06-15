package main.java.recipemanager.DAO;
import main.java.recipemanager.entities.Ingredient;
import main.java.recipemanager.entities.Vegetable;
import main.java.recipemanager.custom_exceptions.CreatingElementException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO extends EntityDAO {
	private static final String GET_INGREDIENTS_BY_RECIPE = "SELECT * FROM ingredient WHERE recipe_id=";
	private static final String GET_INGREDIENT_BY_ID = "SELECT * FROM ingredient where id=";
	private static final String CREATE_INGREDIENT = "INSERT INTO ingredient (vegetable_id, recipe_id, weight) VALUES(?, ?, ?) RETURNING id;";
	private static final String REMOVE_INGREDIENT = "DELETE FROM ingredient WHERE id=";
	private static final String REMOVE_INGREDIENTS_FROM_RECIPE = "DELETE FROM ingredient WHERE recipe_id=";

	public IngredientDAO() {
		super();
	}

	public List<Ingredient> getIngredientsByRecipeId(int recipeId) {
        List<Ingredient> list = new ArrayList<>();
        try (Connection connection = dataBaseConnector.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(GET_INGREDIENTS_BY_RECIPE +recipeId)) {

        	 while(resultSet.next()) {
				 Vegetable vegetable = new VegetableDAO().getById(resultSet.getInt("vegetable_id"));
            	 list.add(new Ingredient(
                		resultSet.getInt("id"),
                		vegetable,
                		resultSet.getDouble("weight"),
						recipeId
                 ));
             }
        } catch (SQLException ex) {
			System.out.println(EXCEPTION_IN_RESULT_SET);
		}
        return list;
    }

    public Ingredient getById(int id) {
		Ingredient ingredient = null;
    	try (Connection connection = dataBaseConnector.getConnection();
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
        } catch (SQLException ex) {
        	System.out.println(EXCEPTION_IN_RESULT_SET);
        }
        return ingredient;
    }
    
    public Ingredient create(int recipeId, int vegetableId, double weight) throws CreatingElementException {
		Ingredient ingredient = null;
    	try (Connection connection = dataBaseConnector.getConnection();
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
				System.out.println(EXCEPTION_IN_RESULT_SET);
			}
	    } catch (SQLException ex) {
			System.out.println(EXCEPTION_IN_STATEMENT);
		}
    	if (ingredient == null) throw new CreatingElementException("Ingredient");
        return ingredient;
    }
    
	public boolean remove(int id){
    	try (Connection connection = dataBaseConnector.getConnection();
			 Statement statement = connection.createStatement()) {
	    	statement.execute(REMOVE_INGREDIENT +id);
	    	return true;
        } catch (SQLException ex) {
            System.out.println(EXCEPTION_IN_STATEMENT);
            return false;
        }
    }

	public boolean removeIngredientsWithRecipeId(int recipeId){
		try (Connection connection = dataBaseConnector.getConnection();
			 Statement statement = connection.createStatement()) {
			statement.execute(REMOVE_INGREDIENTS_FROM_RECIPE +recipeId);
			return true;
		} catch (SQLException ex) {
			System.out.println(EXCEPTION_IN_STATEMENT);
			return false;
		}
	}
}
