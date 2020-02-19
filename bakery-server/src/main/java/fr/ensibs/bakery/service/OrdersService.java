package fr.ensibs.bakery.service;

import java.util.ArrayList;

import javax.jws.WebService;

import fr.ensibs.bakery.model.Order;

@WebService(name = "OrdersService", targetNamespace = "http://bakery.ensibs.fr")
public interface OrdersService {
	
	/**
	 * adds an order to the list of orders.
	 */
	void addOrder();
	
	/**
	 * creates an order.
	 */
	Order createOrder();
	
	/**
	 * gives to the user the list of his orders.
	 * @return a list of orders.
	 */
	ArrayList<Order> viewOrders();
	
	/**
	 * gives the list of products the user can order.
	 * @return an array of available products.
	 */
	ArrayList<String> getList();
	
}
