package fr.ensibs.bakery.service;

import java.util.ArrayList;

import javax.jws.WebService;

import fr.ensibs.bakery.model.Order;

/**
 * A web service that manages customers' orders.
 */
@WebService(name = "OrdersService", targetNamespace = "http://bakery.ensibs.fr")
public interface OrdersService {
	
	/**
	 * Add an order to the list of orders of the user.
	 * @param token the token of the user
	 * @param id the id of the product to order
	 * @param quantity the quantity of the same product to order
	 */
	void addOrder(String token, int id, int quantity);
	
	/**
	 * Get the list of orders of the user.
	 * @param token the token of the user
	 * @return the list of orders of the user
	 */
	ArrayList<Order> getAllOrders(String token);
	
	/**
	 * Get the list of products that the user can order.
	 * @return the list of available products
	 */
	ArrayList<String> getAllProducts();
	
}
