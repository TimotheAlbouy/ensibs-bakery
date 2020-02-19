package fr.ensibs.bakery.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            String sql = "SELECT * FROM Order WHERE id = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            // if no order was found
            if (!result.next())
                return null;
            
            String name = result.getString("name");
            int productId = result.getInt("productId");
            int quantity = result.getInt("quantity");
            int price = result.getInt("price");
            return new Order(id, name, productId, quantity, price);
        } catch (SQLException e) {
            return null;
        }
    }

    public void createOrder(String name, int productId, int quantity, int price) {
        try {
            String sql = "INSERT INTO Order (name, productId, quantity, price) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setInt(4, price);
            stmt.executeUpdate();
        } catch (SQLException e) { }
    }
}
