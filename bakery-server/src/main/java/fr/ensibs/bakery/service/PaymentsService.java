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
     * Get the invoice that the customer has to pay.
     * @param customerToken the token of the customer
     * @return the invoice
     * @throws BakeryServiceException
     */
    @WebMethod(operationName = "getInvoice")
    int getInvoice(@WebParam(name = "customerToken") String customerToken)
            throws BakeryServiceException;

    /**
     * Pay the invoice that the customer has to pay.
     * @param customerToken the token of the customer
     * @throws BakeryServiceException
     */
    @WebMethod(operationName = "payInvoice")
    void payInvoice(@WebParam(name = "customerToken") String customerToken)
            throws BakeryServiceException;

}
