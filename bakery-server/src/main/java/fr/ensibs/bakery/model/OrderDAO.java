package fr.ensibs.bakery.model;

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
     * Query a order in the database from its id.
     * @param id the id of the order
     * @return the corresponding order
     */
    public Order getOrder(int id) {
        try {
            String sql = "SELECT * FROM `Order` WHERE id = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            // if no order was found
            if (!result.next())
                return null;

            int productId = result.getInt("product_id");
            int userId = result.getInt("user_id");
            int quantity = result.getInt("quantity");
            return new Order(id, productId, userId, quantity);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Query all the orders belonging to the given user in the database.
     * @param userId the id of the user
     * @return the list of orders
     */
    public ArrayList<Order> getAllOrders(int userId) {
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
                orders.add(new Order(id, productId, userId, quantity));
            }

            return orders;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Create a new order.
     * @param productId the id of the associated product
     * @param userId the id of the associated user
     * @param quantity the quantity of the same product to order
     */
    public void createOrder(int productId, int userId, int quantity) {
        try {
            String sql = "INSERT INTO `Order` (product_id, user_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, productId);
            stmt.setInt(2, userId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) { }
    }

}
