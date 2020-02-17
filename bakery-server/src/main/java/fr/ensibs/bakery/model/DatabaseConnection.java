package fr.ensibs.bakery.model;

import static fr.ensibs.bakery.impl.Constants.DB_URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A database connection.
 */
public class DatabaseConnection {

    /**
     * the single instance
     */
    private static DatabaseConnection instance;

    /**
     * the JDBC connection
     */
    private Connection connection;

    /**
     * Private constructor for the singleton pattern.
     */
    private DatabaseConnection() throws SQLException {
        this.connection = DriverManager.getConnection(DB_URL);
        System.out.println("Connection to SQLite has been established.");
    }

    /**
     * Instance getter for the singleton pattern.
     * @return the single instance
     */
    public static DatabaseConnection getInstance() throws SQLException {
        if (DatabaseConnection.instance == null)
            DatabaseConnection.instance = new DatabaseConnection();
        return DatabaseConnection.instance;
    }

    /**
     * Get the JDBC connection.
     * @return the JDBC connection
     */
    public Connection getConnection() {
        return this.connection;
    }

}
