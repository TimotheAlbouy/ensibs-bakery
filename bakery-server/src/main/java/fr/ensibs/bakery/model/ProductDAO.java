package fr.ensibs.bakery.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A data access object to manage products.
 */
public class ProductDAO {

    /**
     * the single instance
     */
    private static ProductDAO instance;

    /**
     * the JDBC connection
     */
    private Connection connection;

    /**
     * Private constructor for the singleton pattern.
     */
    private ProductDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Instance getter for the singleton pattern.
     * @return the single instance
     */
    public static ProductDAO getInstance() throws SQLException {
        if (ProductDAO.instance == null)
            ProductDAO.instance = new ProductDAO();
        return ProductDAO.instance;
    }

    /**
     * Query a product in the database from its id.
     * @param id the id of the product
     * @return the corresponding product
     */
    public Product getProduct(int id) {
        try {
            String sql = "SELECT * FROM `Product` WHERE id = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            // if no product was found
            if (!result.next())
                return null;

            String name = result.getString("name");
            int price = result.getInt("price");
            return new Product(id, name, price);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Query all the products of the database.
     * @return the list of all the products
     */
    public ArrayList<Product> getAllProducts() {
        try {
            ArrayList<Product> products = new ArrayList<>();

            String sql = "SELECT * FROM `Product`";
            ResultSet result = this.connection.createStatement().executeQuery(sql);

            // for each product
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                products.add(new Product(id, name, price));
            }

            return products;
        } catch (SQLException e) {
            return null;
        }
    }

    public void createProduct(String name, int price) {
        try {
            String sql = "INSERT INTO `Product` (name, price) VALUES (?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, price);
            stmt.executeUpdate();
        } catch (SQLException e) { }
    }

}
