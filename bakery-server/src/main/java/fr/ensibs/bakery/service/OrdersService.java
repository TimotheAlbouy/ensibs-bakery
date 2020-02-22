package fr.ensibs.bakery.service;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import fr.ensibs.bakery.impl.BakeryServiceException;
import fr.ensibs.bakery.model.Order;
import fr.ensibs.bakery.model.Product;

/**
 * A web service that manages customers' orders.
 */
@WebService(name = "OrdersService", targetNamespace = "http://bakery.ensibs.fr")
public interface OrdersService {

    /**
     * Add an order to the list of the customer.
     * @param customerToken the token of the customer
     * @param productId the id of the product to order
     * @param quantity the quantity of the same product to order
	 * @throws BakeryServiceException when an error occurs
     */
    @WebMethod(operationName = "addOrder")
    void addOrder(@WebParam(name = "customerToken") String customerToken,
                  @WebParam(name = "productId") int productId,
                  @WebParam(name = "quantity") int quantity)
            throws BakeryServiceException;

    /**
     * Get the list of orders of the customer.
     * @param customerToken the token of the customer
     * @return the list of orders of the customer
	 * @throws BakeryServiceException when an error occurs
     */
    @WebMethod(operationName = "getOrders")
    ArrayList<Order> getOrders(@WebParam(name = "customerToken") String customerToken)
            throws BakeryServiceException;

    /**
     * Get the list of products that the customer can order.
     * @param customerToken the token of the customer
     * @return the list of available products
	 * @throws BakeryServiceException when an error occurs
     */
    @WebMethod(operationName = "getProducts")
    ArrayList<Product> getProducts(String customerToken) throws BakeryServiceException;

    /**
     * Add a new product to the bakery.
	 * @param adminToken the token of the admin
     * @param name the name of the product
     * @param price the price of the product
     * @throws BakeryServiceException when an error occurs
     */
    @WebMethod(operationName = "addProduct")
    void addProduct(@WebParam(name = "adminToken") String adminToken,
					@WebParam(name = "name") String name,
					@WebParam(name = "price") int price)
			throws BakeryServiceException;

}
