package fr.ensibs.bakery.impl;

import fr.ensibs.bakery.model.OrderDAO;
import fr.ensibs.bakery.model.ProductDAO;
import fr.ensibs.bakery.model.UserDAO;
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
     * @throws SQLException
     */
    public PaymentsServiceImpl() throws SQLException {
        this.orderDAO = OrderDAO.getInstance();
        this.productDAO = ProductDAO.getInstance();
        this.userDAO = UserDAO.getInstance();
    }

    @Override
    public int getInvoice(String token) throws BakeryServiceException {
        // verify the token
        String name = Auth.verify(token, false);
        if (name == null)
            throw new BakeryServiceException(401);

        return this.orderDAO.getInvoiceByName(name);
    }

    @Override
    public void payInvoice(String token) throws BakeryServiceException {
        // verify the token
        String name = Auth.verify(token, false);
        if (name == null)
            throw new BakeryServiceException(401);

        this.orderDAO.payInvoiceByName(name);
    }

}
