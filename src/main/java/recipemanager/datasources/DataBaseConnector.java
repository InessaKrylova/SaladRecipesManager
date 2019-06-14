package main.java.recipemanager.datasources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnector {

    private static Connection connection = null;
    private static String EXC_LOCATION = " while openConnection() in DataBaseConnector !";

    public static boolean openConnection() {
        Properties property = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException"+EXC_LOCATION);
            return false;
        } catch (IOException e) {
            System.out.println("IOException"+EXC_LOCATION);
            return false;
        }

        String url = property.getProperty("db.url");
        String login = property.getProperty("db.login");
        String password = property.getProperty("db.password");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException"+EXC_LOCATION);
            return false;
        }
        try {
            connection = DriverManager.getConnection(url, login, password);
            return true;
        }catch (SQLException e) {
            System.out.println("SQLException"+EXC_LOCATION);
            return false;
        }
    }

    public static Connection getConnection() {
        return openConnection() ? connection : null;
    }
}
