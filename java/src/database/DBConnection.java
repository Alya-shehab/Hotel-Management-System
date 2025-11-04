// File: database/DBConnection.java

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Update these constants to match your environment
    private static final String URL = "jdbc:mysql://localhost:3306/HotelManagementDB?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "banana123";

    static {
        try {
            // Explicitly load the JDBC driver (not strictly required in modern Java)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Make sure it's added to the project libraries.");
            e.printStackTrace();
        }
    }

    // Get a connection to the database
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection failed:");
            e.printStackTrace();
            return null;
        }
    }
}