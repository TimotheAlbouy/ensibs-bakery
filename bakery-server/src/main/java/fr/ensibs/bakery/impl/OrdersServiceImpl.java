package fr.ensibs.bakery.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import com.auth0.jwt.JWT;
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
     * Constructor.
     */
    public OrdersServiceImpl() throws SQLException, ClassNotFoundException {
        this.orderDAO = OrderDAO.getInstance();
        this.productDAO = ProductDAO.getInstance();
    }

    @Override
    public void addOrder(String token, int id, int quantity) {
        // verify the token
		User user = Auth.verify(token);
		if (user == null)
		    return;

        Product product = this.productDAO.getProduct(id);

		// if the user with the given id does not exist
		if (product == null)
			return;

		this.orderDAO.createOrder(product.getId(), user.getId(), quantity);
    }

    @Override
    public ArrayList<Order> getAllOrders(String token) {
        // verify the token
        User user = Auth.verify(token);
        if (user == null)
            return null;

        return this.orderDAO.getAllOrders(user.getId());
    }

    @Override
    public ArrayList<String> getAllProducts() {
        return this.productDAO.getAllProducts();
    }

}
