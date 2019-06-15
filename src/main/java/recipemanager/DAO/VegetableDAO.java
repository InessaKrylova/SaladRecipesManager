package main.java.recipemanager.DAO;

import java.sql.*;
import java.util.*;

import main.java.recipemanager.datasources.DataBaseConnector;
import main.java.recipemanager.entities.Vegetable;

public class VegetableDAO {
   
	private static final String GET_VEGETABLE_BY_ID = "SELECT * FROM vegetable WHERE id=";
	private static final String EXCEPTION_IN_RESULTSET = "Exception while executing query / getting result set";
	private static final String EXCEPTION_IN_STATEMENT = "Exception while creating statement";
	private static final String GET_ALL_VEGETABLES = "SELECT * FROM vegetable";
	private static final String CREATE_VEGETABLE = "INSERT INTO vegetable (title, caloricity, category) VALUES(?, ?, ?) RETURNING id;";
	private static final String REMOVE_VEGETABLE = "DELETE FROM vegetable WHERE id=";
	private static final String GET_BY_TITLE = "SELECT * FROM vegetable WHERE title=?;";
	
	public Vegetable getById(int id) {
		Vegetable vegetable = null;
        try (Connection connection = DataBaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_VEGETABLE_BY_ID +id)) {
             while(resultSet.next()) {
				vegetable = Vegetable.getVegetableWithCategory(
						resultSet.getInt("category"),
						id,
						resultSet.getString("title"),
						resultSet.getInt("caloricity")
				);
             }
        } catch (SQLException ex) {
			System.out.println(EXCEPTION_IN_RESULTSET);
		}
        return vegetable;
    }

    public List<Vegetable> getAllVegetables() {
    	List<Vegetable> list = new ArrayList<>();
    	try (Connection connection = DataBaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_VEGETABLES)) {
            while(resultSet.next()) {   
                list.add(Vegetable.getVegetableWithCategory(
						resultSet.getInt("category"),
                		resultSet.getInt("id"), 
                		resultSet.getString("title"), 
                		resultSet.getInt("caloricity")
                ));               
            }
        } catch (SQLException ex) {
			System.out.println(EXCEPTION_IN_RESULTSET);
		}
        return list;
    }

    public Vegetable getByTitle(String title) {
		Vegetable vegetable = null;
        try (Connection connection = DataBaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_TITLE))  {
	        statement.setString(1, title);
        	try (ResultSet resultSet = statement.executeQuery()) {
	            while(resultSet.next()) {
					vegetable = new Vegetable(
							resultSet.getInt("id"),
							title,
							resultSet.getInt("caloricity")
					);
	            }
	        } catch (SQLException ex) {
	            System.out.println(EXCEPTION_IN_RESULTSET);
	        }
	    } catch (SQLException ex) {
	        System.out.println(EXCEPTION_IN_STATEMENT);
	    }
        return vegetable;
    }
    
    public Vegetable create(String title, int caloricity, int category) {
		Vegetable vegetable = null;
		try (Connection connection = DataBaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_VEGETABLE)) {
			statement.setString(1, title);
			statement.setInt(2, caloricity);
			statement.setInt(3, category);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					vegetable = Vegetable.getVegetableWithCategory(category, resultSet.getInt("id"), title, caloricity);
				}
			} catch (SQLException ex) {
				System.out.println(EXCEPTION_IN_RESULTSET);
			}
		} catch (SQLException ex) {
			System.out.println(EXCEPTION_IN_STATEMENT);
		}
		return vegetable;
    }     
    
    public boolean remove(int id){
    	try (Connection connection = DataBaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
    		statement.execute(REMOVE_VEGETABLE +id);
    		return true;
        } catch (Exception ex) {
            System.out.println(EXCEPTION_IN_STATEMENT);
            return false;
        }
    }
}
