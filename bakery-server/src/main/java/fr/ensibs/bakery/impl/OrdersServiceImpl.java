package fr.ensibs.bakery.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import fr.ensibs.bakery.model.*;
import fr.ensibs.bakery.service.OrdersService;

import javax.jws.WebService;

/**
 * A web service that manages customers' orders.
 */
@WebService(endpointInterface = "fr.ensibs.bakery.service.OrdersService",
        serviceName = "OrdersService",
        portName = "OrdersPort")
public class OrdersServiceImpl implements OrdersService {

    /**
     * the DAO to handle orders in the database
     */
    private OrderDAO orderDAO;

    /**
     * the DAO to handle products in the database
     */
    private ProductDAO productDAO;

    /**
     * the DAO to handle users in the database
     */
    private UserDAO userDAO;

    /**
     * Constructor.
     */
    public OrdersServiceImpl() throws SQLException {
        this.orderDAO = OrderDAO.getInstance();
        this.productDAO = ProductDAO.getInstance();
        this.userDAO = UserDAO.getInstance();
    }

    @Override
    public void addOrder(String customerToken, int productId, int quantity) throws BakeryServiceException {
        // check the validity of the parameters
        if (quantity <= 0)
            throw new BakeryServiceException(400);

        // check that the user exists
        User user = this.userDAO.getUserByToken(customerToken);
        if (user == null)
            throw new BakeryServiceException(401);

        // check that the user is customer
        if (user.getRole() != Role.CUSTOMER)
            throw new BakeryServiceException(403);

		// check that the product exists
        Product product = this.productDAO.getProduct(productId);
		if (product == null)
			throw new BakeryServiceException(404);

		this.orderDAO.createOrder(product.getId(), user.getId(), quantity);
    }

    @Override
    public ArrayList<Order> getOrdersByUser(String name) throws BakeryServiceException {
        User user = this.userDAO.getUserByName(name);
        if (user == null)
            throw new BakeryServiceException(404);

        return this.orderDAO.getOrdersByUser(user.getId());
    }

    @Override
    public ArrayList<Product> getAllProducts() throws BakeryServiceException {
        return this.productDAO.getAllProducts();
    }

    @Override
    public void addProduct(String adminToken, String name, int price) throws BakeryServiceException {
        // check the validity of the parameters
        if (name == null || price <= 0)
            throw new BakeryServiceException(400);

        // check that the user exists
        User user = this.userDAO.getUserByToken(adminToken);
        if (user == null)
            throw new BakeryServiceException(401);

        // check that the user is admin
        if (user.getRole() != Role.ADMIN)
            throw new BakeryServiceException(403);

        this.orderDAO.createProduct(name, price);
    }

}
