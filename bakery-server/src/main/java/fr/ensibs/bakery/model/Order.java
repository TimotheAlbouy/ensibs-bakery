package fr.ensibs.bakery.model;

/**
 * An order of an user.
 */
public class Order {
	
	/**
	 * the id of the order
	 */
	private int id;
	
	/**
	 * the id of the corresponding product
	 */
	private int productId;

	/**
	 * the id of the corresponding user
	 */
	private int userId;
	
	/**
	 * the quantity of the same product the user ordered
	 */
	private int quantity;
	
	/**
	 * Package-only constructor.
	 * @param id the id of the order
	 * @param productId the id of the associated product
	 * @param userId the id of the associated user
	 * @param quantity the quantity of the same product to order
	 */
	Order(int id, int productId, int userId, int quantity) {
		this.id = id;
		this.productId = productId;
		this.userId = userId;
		this.quantity = quantity;
	}

	/**
	 * Get the id of the order.
	 * @return the id of the order
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the id of the associated product.
	 * @return the id of the associated product
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * Get the id of the associated user.
	 * @return the id of the associated user
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Get the quantity of the order.
	 * @return the quantity of the order
	 */
	public int getQuantity() {
		return quantity;
	}

}
