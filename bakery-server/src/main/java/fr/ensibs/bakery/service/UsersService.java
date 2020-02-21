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
     * Log the user in.
     * @param name the name of the user
     * @param password the password of the user
     * @return the generated JWT
     * @throws BakeryServiceException when an error occurs
     */
    @WebMethod(operationName = "login")
    String login(@WebParam(name = "name") String name,
                 @WebParam(name = "password") String password)
            throws BakeryServiceException;

    /**
     * Log the user out.
     * @param token the token of the user
     * @throws BakeryServiceException when an error occurs
     */
    @WebMethod(operationName = "logout")
    void logout(@WebParam(name = "token") String token)
            throws BakeryServiceException;

    /**
     * Register a new user.
     * @param name the name of the user
     * @param password the password of the user
     * @return the generated JWT
     * @throws BakeryServiceException when an error occurs
     */
    @WebMethod(operationName = "register")
    String register(@WebParam(name = "name") String name,
                    @WebParam(name = "password") String password)
            throws BakeryServiceException;

    /**
     * Get the list containing all the users.
     * @param token an admin token
     * @return the list of all the users
     * @throws BakeryServiceException when an error occurs
     */
    @WebMethod(operationName = "getAllUsers")
    ArrayList<User> getAllUsers(@WebParam(name = "token") String token)
            throws BakeryServiceException;

    /**
     * Set the administrator rights of an user.
     * @param adminToken the admin token
     * @param name the name of the user
     * @param isAdmin whether the user must have admin rights or not
     * @throws BakeryServiceException when an error occurs
     */
    @WebMethod(operationName = "setAdmin")
    void setAdmin(@WebParam(name = "adminToken") String adminToken,
                         @WebParam(name = "name") String name,
                         @WebParam(name = "isAdmin") boolean isAdmin)
            throws BakeryServiceException;

    /**
     * Delete an user.
     * @param adminToken the admin token
     * @param name the name of the user
     * @throws BakeryServiceException when an error occurs
     */
    @WebMethod(operationName = "deleteUser")
    void deleteUser(@WebParam(name = "adminToken") String adminToken,
                           @WebParam(name = "name") String name)
            throws BakeryServiceException;

}
