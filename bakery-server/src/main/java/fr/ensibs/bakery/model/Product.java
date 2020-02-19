package fr.ensibs.bakery.model;

/**
 * A product of the bakery.
 */
public class Product {

    /**
     * the id of the product
     */
    private int id;

    /**
     * the name of the product
     */
    private String name;

    /**
     * the price of the product
     */
    private int price;

    /**
     * Package-only constructor.
     * @param id the id of the product
     * @param name the name of the product
     * @param price the price of the product
     */
    Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Get the id of the product.
     * @return the id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the product.
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Get the price of the product.
     * @return the price of the product
     */
    public int getPrice() {
        return price;
    }

}
