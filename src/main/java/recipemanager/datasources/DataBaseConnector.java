package main.java.recipemanager.datasources;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DataBaseConnector implements DataSource{

    private static Connection con = null;

    public static Connection openConnection() throws Exception {
        Properties property = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        property.load(fis);

        String url = property.getProperty("db.url");
        String login = property.getProperty("db.login");
        String password = property.getProperty("db.password");

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }      
}
