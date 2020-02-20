package fr.ensibs.bakery.model;

import fr.ensibs.bakery.impl.BakeryServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A data access object to manage users.
 */
public class UserDAO {

    /**
     * the single instance
     */
    private static UserDAO instance;

    /**
     * the JDBC connection
     */
    private Connection connection;

    /**
     * Private constructor for the singleton pattern.
     */
    private UserDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Instance getter for the singleton pattern.
     * @return the single instance
     */
    public static UserDAO getInstance() throws SQLException {
        if (UserDAO.instance == null)
            UserDAO.instance = new UserDAO();
        return UserDAO.instance;
    }

    /**
     * Query a user by name in the database.
     * @param name the name of the user
     * @return the corresponding user
     */
    public User getUserByName(String name) throws BakeryServiceException {
        try {
            String sql = "SELECT * FROM `User` WHERE name = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet result = stmt.executeQuery();

            // if no user was found
            if (!result.next())
                return null;

            int id = result.getInt("id");
            String passwordHash = result.getString("password_hash");
            Role role = "ADMIN".equals(result.getString("role")) ? Role.ADMIN : Role.CUSTOMER;
            return new User(id, name, passwordHash, role);
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Query all the users in the database.
     */
    public ArrayList<User> getAllUsers() throws BakeryServiceException {
        try {
            ArrayList<User> users = new ArrayList<>();

            String sql = "SELECT * FROM `User`";
            ResultSet result = this.connection.createStatement().executeQuery(sql);

            // for each product
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String passwordHash = result.getString("password_hash");
                Role role = "ADMIN".equals(result.getString("password_hash")) ? Role.ADMIN : Role.CUSTOMER;
                users.add(new User(id, name, passwordHash, role));
            }

            return users;
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Create a new user in the database
     * @param name the name of the user
     * @param passwordHash the hash of the password of the user
     */
    public void createUser(String name, String passwordHash) throws BakeryServiceException {
        try {
            String sql = "INSERT INTO `User` (name, password_hash) VALUES (?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, passwordHash);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

}
