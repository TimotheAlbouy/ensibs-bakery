package fr.ensibs.bakery.impl;

import fr.ensibs.bakery.model.*;
import fr.ensibs.bakery.service.PaymentsService;

import javax.jws.WebService;
import java.sql.SQLException;

/**
 * A web service that manages customers' payments.
 */
@WebService(endpointInterface = "fr.ensibs.bakery.service.PaymentsService",
        serviceName = "PaymentsService",
        portName = "PaymentsPort")
public class PaymentsServiceImpl implements PaymentsService {

    /**
     * the DAO to handle orders in the database
     */
    private OrderDAO orderDAO;

    /**
     * the DAO to handle products in the database
     */
    private ProductDAO productDAO;

    /**
     * the DAO to handle users in the database
     */
    private UserDAO userDAO;

    /**
     * Constructor.
     * @throws SQLException when an error occurs
     * @throws ClassNotFoundException when an error occurs
     */
    public PaymentsServiceImpl() throws SQLException, ClassNotFoundException {
        this.orderDAO = OrderDAO.getInstance();
        this.productDAO = ProductDAO.getInstance();
        this.userDAO = UserDAO.getInstance();
    }

    @Override
    public int getInvoice(String customerToken) throws BakeryServiceException {
        // check that the user exists
        User user = this.userDAO.getUserByToken(customerToken);
        if (user == null)
            throw new BakeryServiceException(401);

        // check that the user is customer
        if (user.getRole() != Role.CUSTOMER)
            throw new BakeryServiceException(403);

        return this.orderDAO.getInvoiceByName(user.getName());
    }

    @Override
    public void payInvoice(String customerToken) throws BakeryServiceException {
        // check that the user exists
        User user = this.userDAO.getUserByToken(customerToken);
        if (user == null)
            throw new BakeryServiceException(401);

        // check that the user is customer
        if (user.getRole() != Role.CUSTOMER)
            throw new BakeryServiceException(403);

        this.orderDAO.payInvoiceByName(user.getName());
    }

}
