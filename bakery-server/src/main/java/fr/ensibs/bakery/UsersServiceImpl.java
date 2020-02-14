package fr.ensibs.bakery;

import javax.jws.WebService;

/**
 * A web service that manages users and permissions.
 */
@WebService(endpointInterface = "fr.ensibs.bakery.UsersService",
            serviceName = "UsersService",
            portName = "UsersPort")
public class UsersServiceImpl implements UsersService {
    
    /**
     * the DAO to handle users in the database
     */
    private UserDAO userDAO;
    
    /**
     * Constructor.
     */
    public UsersServiceImpl() {
        this.userDAO = UserDAO.getInstance();
    }
    
    @Override
    public User getUser(String name) {
        return null;
    }
    
}
