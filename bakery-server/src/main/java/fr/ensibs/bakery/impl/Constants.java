package fr.ensibs.bakery.impl;

/**
 * Class containing constant values of the application.
 */
public class Constants {

    /**
     * the users web service name
     */
    public final static String USERS_SERVICE = "UsersService";

    /**
     * the orders web service name
     */
    public final static String ORDERS_SERVICE = "OrdersService";

    /**
     * the database URL
     */
    public final static String DB_URL = "jdbc:sqlite:bakery.db";

    /**
     * the secret used to sign the JWT
     */
    public final static String JWT_SECRET = "gneugneugneugneu12345 bijoul bijoul bonjoire !!!!!!!!  ! ! :DDDDDDD";

    /**
     * the issuer of the JWT
     */
    public final static String JWT_ISSUER = "ENSIBS";

    /**
     * Private constructor to enforce noninstanciability.
     */
    private Constants() {
        throw new AssertionError();
    }

}
