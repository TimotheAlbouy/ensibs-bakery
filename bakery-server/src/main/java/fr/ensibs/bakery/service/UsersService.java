package fr.ensibs.bakery.service;

import fr.ensibs.bakery.impl.BakeryServiceException;
import fr.ensibs.bakery.model.User;

import javax.jws.WebService;
import java.util.ArrayList;

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
    String login(String name, String password) throws BakeryServiceException;

    /**
     * Register a new user.
     * @param name the name of the user
     * @param password the password of the user
     * @return the generated JWT
     */
    String register(String name, String password) throws BakeryServiceException;

    /**
     * Get the list containing all the users.
     * @param token an admin token
     * @return the list of all the users
     */
    ArrayList<User> getAllUsers(String token) throws BakeryServiceException;
    
}
