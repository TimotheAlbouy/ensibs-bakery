package fr.ensibs.bakery.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An order of an user.
 */
@XmlRootElement(name = "Order")
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
	 * whether the order is paid or not
	 */
	private boolean isPaid;

    /**
     * No-argument constructor.
     */
    Order() { }
	
	/**
	 * Package-only constructor.
	 * @param id the id of the order
	 * @param productId the id of the associated product
	 * @param userId the id of the associated user
	 * @param quantity the quantity of the same product to order
	 * @param isPaid whether the order is paid or not
	 */
	Order(int id, int productId, int userId, int quantity, boolean isPaid) {
		this.id = id;
		this.productId = productId;
		this.userId = userId;
		this.quantity = quantity;
		this.isPaid = isPaid;
	}

	/**
	 * Get the id of the order.
	 * @return the id of the order
	 */
	@XmlElement(name = "getId")
	public int getId() {
		return id;
	}
	
	/**
	 * Get the id of the associated product.
	 * @return the id of the associated product
	 */
    @XmlElement(name = "getProductId")
	public int getProductId() {
		return productId;
	}

	/**
	 * Get the id of the associated user.
	 * @return the id of the associated user
	 */
    @XmlElement(name = "getUserId")
	public int getUserId() {
		return userId;
	}

	/**
	 * Get the quantity of the order.
	 * @return the quantity of the order
	 */
    @XmlElement(name = "getQuantity")
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Tell if the order is paid or not.
	 * @return true iff the order is paid
	 */
    @XmlElement(name = "isPaid")
	public boolean isPaid() {
		return isPaid;
	}

}
