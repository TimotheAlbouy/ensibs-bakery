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
     * @throws SQLException when an error occurs
     * @throws ClassNotFoundException when an error occurs
     */
    private UserDAO() throws SQLException, ClassNotFoundException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Instance getter for the singleton pattern.
     * @return the single instance
     * @throws SQLException when an error occurs
     * @throws ClassNotFoundException when an error occurs
     */
    public static UserDAO getInstance() throws SQLException, ClassNotFoundException {
        if (UserDAO.instance == null)
            UserDAO.instance = new UserDAO();
        return UserDAO.instance;
    }

    /**
     * Query a user by name in the database.
     * @param name the name of the user
     * @return the corresponding user
     * @throws BakeryServiceException when an error occurs
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
            String token = result.getString("token");
            return new User(id, name, passwordHash, role, token);
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Query a user by token in the database.
     * @param token the token of the user
     * @return the corresponding user
     * @throws BakeryServiceException when an error occurs
     */
    public User getUserByToken(String token) throws BakeryServiceException {
        try {
            String sql = "SELECT * FROM `User` WHERE token = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, token);
            ResultSet result = stmt.executeQuery();

            // if no user was found
            if (!result.next())
                return null;

            int id = result.getInt("id");
            String name = result.getString("name");
            String passwordHash = result.getString("password_hash");
            Role role = "ADMIN".equals(result.getString("role")) ? Role.ADMIN : Role.CUSTOMER;
            return new User(id, name, passwordHash, role, token);
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Query all the users in the database.
     * @return the list of all the users
     * @throws BakeryServiceException when an error occurs
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
                String token = result.getString("token");
                users.add(new User(id, name, passwordHash, role, token));
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
     * @param token the token of the user
     * @throws BakeryServiceException when an error occurs
     */
    public void createUser(String name, String passwordHash, String token) throws BakeryServiceException {
        try {
            String sql = "INSERT INTO `User` (name, password_hash, token) VALUES (?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, passwordHash);
            stmt.setString(3, token);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Update the token of the user.
     * @param name the name of the user
     * @param newToken the new token of the user
     * @throws BakeryServiceException when an error occurs
     */
    public void updateToken(String name, String newToken) throws BakeryServiceException {
        try {
            String sql = "UPDATE `User` SET token = ? WHERE name = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, newToken);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Set the administrator rights of an user.
     * @param name the name of the user
     * @param isAdmin whether the user must have admin rights or not
     * @throws BakeryServiceException when an error occurs
     */
    public void setAdmin(String name, boolean isAdmin) throws BakeryServiceException {
        try {
            String sql = "UPDATE `User` SET is_admin = ? WHERE name = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setBoolean(1, isAdmin);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Delete an user.
     * @param name the name of the user
     * @throws BakeryServiceException when an error occurs
     */
    public void deleteUser(String name) throws BakeryServiceException {
        try {
            String sql = "DELETE FROM `User` WHERE name = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

}
