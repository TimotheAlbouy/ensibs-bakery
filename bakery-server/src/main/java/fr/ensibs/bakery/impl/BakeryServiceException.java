package fr.ensibs.bakery.impl;

import javax.xml.ws.WebFault;

/**
 * An exception occuring in a web method of the bakery services.
 */
@WebFault(name = "BakeryServiceException")
public class BakeryServiceException extends Exception {

    /**
     * the HTTP status code of the error
     */
    private int httpCode;

    /**
     * Constructor.
     * @param message the message of the error
     * @param httpCode the HTTP status code
     */
    public BakeryServiceException(int httpCode, String message) {
        super("HTTP " + httpCode + ": " + message);
        this.httpCode = httpCode;
    }

    /**
     * Constructor.
     * @param httpCode the HTTP status code
     */
    public BakeryServiceException(int httpCode) {
        super("HTTP " + httpCode);
    }

    /**
     * Get the HTTP status code of the error.
     * @return the HTTP status code
     */
    public int getHttpCode() {
        return httpCode;
    }

}
