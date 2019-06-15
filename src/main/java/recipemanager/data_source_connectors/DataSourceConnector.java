package main.java.recipemanager.data_source_connectors;

public interface DataSourceConnector {
    /**
     * Implemented methods should return true, if connection was opened successfully.
     * @return boolean
     */
     boolean openConnection();
}
