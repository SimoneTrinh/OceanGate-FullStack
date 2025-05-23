package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/master";
    private static final String USER = "uitjava@1";
    private static final String PASSWORD = "sunh@12";
    private static Connection connection = null;
    public static void connect() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        if(connection != null){
            return connection;
        }
        return null;
    }
}
