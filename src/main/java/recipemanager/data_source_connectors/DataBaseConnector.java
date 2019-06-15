package main.java.recipemanager.data_source_connectors;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnector implements DataSourceConnector {

    private static Connection connection = null;

    public boolean openConnection() {
        Properties property = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);

        } catch (IOException e) {
            System.out.println(e.toString());
            return false;
        }

        String url = property.getProperty("db.url");
        String login = property.getProperty("db.login");
        String password = property.getProperty("db.password");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
            return false;
        }
        try {
            connection = DriverManager.getConnection(url, login, password);
            return true;
        }catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public Connection getConnection() {
        return openConnection() ? connection : null;
    }
}
