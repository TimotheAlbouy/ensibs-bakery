package fr.ensibs.bakery;

import javax.jws.WebService;

/**
 * A web service that manages users and permissions.
 */
@WebService(name = "UsersService", targetNamespace = "http://bakery.ensibs.fr")
public interface UsersService {

    /**
     * Get the user according to their name.
     *
     * @param name the name of the user
     * 
     * @return the user
     */
    User getUser(String name);
    
}
