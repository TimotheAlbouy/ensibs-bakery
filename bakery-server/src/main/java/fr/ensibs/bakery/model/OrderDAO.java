package fr.ensibs.bakery.model;

import fr.ensibs.bakery.impl.BakeryServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A data access object to manage orders.
 */
public class OrderDAO {

	/**
     * the single instance
     */
    private static OrderDAO instance;

    /**
     * the JDBC connection
     */
    private Connection connection;

    /**
     * Private constructor for the singleton pattern.
     */
    private OrderDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Instance getter for the singleton pattern.
     * @return the single instance
     */
    public static OrderDAO getInstance() throws SQLException {
        if (OrderDAO.instance == null)
            OrderDAO.instance = new OrderDAO();
        return OrderDAO.instance;
    }

    /**
     * Query all the orders belonging to the given user in the database.
     * @param userId the id of the user
     * @return the list of orders
     */
    public ArrayList<Order> getOrdersByUser(int userId) throws BakeryServiceException {
        try {
            ArrayList<Order> orders = new ArrayList<>();

            String sql = "SELECT * FROM `Order` WHERE user_id = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet result = stmt.executeQuery();

            // for each order
            while (result.next()) {
                int id = result.getInt("id");
                int productId = result.getInt("product_id");
                int quantity = result.getInt("quantity");
                boolean isPaid = result.getBoolean("is_paid");
                orders.add(new Order(id, productId, userId, quantity, isPaid));
            }

            return orders;
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Get the invoice of the user.
     * @param name the name of the user
     * @return the invoice to pay
     * @throws BakeryServiceException
     */
    public int getInvoiceByName(String name) throws BakeryServiceException {
        try {
            String sql = "SELECT SUM(price) AS sum FROM `Order`, `Product`, `User` " +
                    "WHERE product_id = product.id AND user_id = user.id " +
                    "AND user.name = '?' AND is_paid = 0";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet result = stmt.executeQuery();

            result.next();
            return result.getInt("sum");
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Pay the invoice of the user.
     * @param name the name of the user
     * @throws BakeryServiceException
     */
    public void payInvoiceByName(String name) throws BakeryServiceException {
        try {
            String sql = "UPDATE `Order` SET is_paid = 1";
            this.connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Create a new order in the database.
     * @param productId the id of the associated product
     * @param userId the id of the associated user
     * @param quantity the quantity of the same product to order
     */
    public void createOrder(int productId, int userId, int quantity) throws BakeryServiceException {
        try {
            String sql = "INSERT INTO `Order` (product_id, user_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, productId);
            stmt.setInt(2, userId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Create a new product in the database.
     * @param name the name of the product
     * @param price the price of the product
     */
    public void createProduct(String name, int price) throws BakeryServiceException {
        try {
            String sql = "INSERT INTO `Product` (name, price) VALUES (?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, price);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BakeryServiceException(500, e.getMessage());
        }
    }

}
