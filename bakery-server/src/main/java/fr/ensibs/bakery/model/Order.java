package fr.ensibs.bakery.model;

/**
 * An order from an user.
 */
public class Order {
	
	/**
	 * a unique id for the order.
	 */
	private int id;
	
	/**
	 * the id of the product.
	 */
	private int productId;
	
	/**
	 * the amount of productId the user ordered.
	 */
	private int quantity;
	
	/**
	 * The name used by the user who made the order.
	 */
	private String name;
	
	/**
	 * the final price of the order.
	 */
	private int price;
	
	/**
	 * a boolean, true if the order has been paid.
	 */
	private boolean paid;
	
	/**
	 * Constructor
	 */
	Order(int id, String name, int productId, int quantity, int price){
		this.name = name;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.paid = false;
	}
	
	/**
	 * Set a new payment status for the current order.
	 * @param paid the new payment status.
	 */
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	/**
	 * Set a new quantity for the current id.
	 * @param quantity the new quantity of the current id.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Set a new id to define a new product.
	 * @param productId the id of the new product.
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
}
