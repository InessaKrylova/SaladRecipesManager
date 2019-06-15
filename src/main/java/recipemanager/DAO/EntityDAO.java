package main.java.recipemanager.DAO;

import main.java.recipemanager.data_source_connectors.DataBaseConnector;

public class EntityDAO {
    protected static final String EXCEPTION_IN_RESULT_SET = "Exception while executing query / getting result set";
    protected static final String EXCEPTION_IN_STATEMENT = "Exception while creating statement";

    protected DataBaseConnector dataBaseConnector;

    public EntityDAO() {
        dataBaseConnector = new DataBaseConnector();
    }
}
