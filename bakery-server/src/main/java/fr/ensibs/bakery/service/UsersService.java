package fr.ensibs.bakery.service;

import fr.ensibs.bakery.impl.BakeryServiceException;
import fr.ensibs.bakery.model.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
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
    @WebMethod(operationName = "login")
    String login(@WebParam(name = "name") String name,
                 @WebParam(name = "password") String password)
            throws BakeryServiceException;

    /**
     * Register a new user.
     * @param name the name of the user
     * @param password the password of the user
     * @return the generated JWT
     */
    @WebMethod(operationName = "register")
    String register(@WebParam(name = "name") String name,
                    @WebParam(name = "password") String password)
            throws BakeryServiceException;

    /**
     * Get the list containing all the users.
     * @param token an admin token
     * @return the list of all the users
     */
    @WebMethod(operationName = "getAllUsers")
    ArrayList<User> getAllUsers(@WebParam(name = "token") String token)
            throws BakeryServiceException;
    
}
