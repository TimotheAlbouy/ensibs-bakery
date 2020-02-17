package fr.ensibs.bakery.service;

import fr.ensibs.bakery.model.User;

import javax.jws.WebService;

/**
 * A web service that manages users and permissions.
 */
@WebService(name = "UsersService", targetNamespace = "http://bakery.ensibs.fr")
public interface UsersService {

    /**
     * Get an user according to their name.
     * @param name the name of the user
     * @param password the password of the user
     * @return the generated JWT
     */
    String login(String name, String password);

    /**
     * Register a new user.
     * @param name the name of the user
     * @param password the password of the user
     * @return the generated JWT
     */
    String register(String name, String password);
    
}
