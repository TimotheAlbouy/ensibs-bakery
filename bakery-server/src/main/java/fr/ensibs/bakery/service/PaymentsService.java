package fr.ensibs.bakery.service;

import fr.ensibs.bakery.impl.BakeryServiceException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * A web service that payments of orders.
 */
@WebService(name = "PaymentsService", targetNamespace = "http://bakery.ensibs.fr")
public interface PaymentsService {

    /**
     * Get the invoice that the user has to pay.
     * @param token the token of the user
     * @return the invoice
     */
    @WebMethod(operationName = "getInvoice")
    int getInvoice(@WebParam(name = "token") String token)
            throws BakeryServiceException;

    /**
     * Pay the invoice that the user has to pay.
     * @param token the token of the user
     */
    @WebMethod(operationName = "payInvoice")
    void payInvoice(@WebParam(name = "token") String token)
            throws BakeryServiceException;

}
