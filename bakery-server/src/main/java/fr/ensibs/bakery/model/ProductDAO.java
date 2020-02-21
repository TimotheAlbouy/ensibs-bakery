package fr.ensibs.bakery.model;

import fr.ensibs.bakery.impl.BakeryServiceException;

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
     * Query a product in the database.
     * @param id the id of the product
     * @return the corresponding product
     */
    public Product getProduct(int id) throws BakeryServiceException {
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
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Query a product in the database.
     * @param name the name of the product
     * @return the corresponding product
     */
    public Product getProductByName(String name) throws BakeryServiceException {
        try {
            String sql = "SELECT * FROM `Product` WHERE name = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet result = stmt.executeQuery();

            // if no product was found
            if (!result.next())
                return null;

            int id = result.getInt("id");
            int price = result.getInt("price");
            return new Product(id, name, price);
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Query all the products in the database.
     * @return the list of all the products
     */
    public ArrayList<Product> getAllProducts() throws BakeryServiceException {
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
            throw new BakeryServiceException(500);
        }
    }

    /**
     * Create a new product in the database
     * @param name the name of the product
     * @param price the price of the product
     * @throws BakeryServiceException
     */
    public void createProduct(String name, int price) throws BakeryServiceException {
        try {
            String sql = "INSERT INTO `Product` (name, price) VALUES (?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, price);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BakeryServiceException(500);
        }
    }

}
