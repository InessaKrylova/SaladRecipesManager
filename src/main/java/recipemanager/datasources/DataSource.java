package main.java.recipemanager.datasources;

public interface DataSource {
    /**
     * Implemented methods should return true, if connection was opened successfully.
     * @return boolean
     */
     boolean openConnection();
}
