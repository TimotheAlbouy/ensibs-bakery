package fr.ensibs.bakery.impl;

import fr.ensibs.bakery.model.Role;
import fr.ensibs.bakery.model.User;
import fr.ensibs.bakery.model.UserDAO;
import fr.ensibs.bakery.service.UsersService;

import org.mindrot.jbcrypt.BCrypt;

import javax.jws.WebService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A web service that manages users and permissions.
 */
@WebService(endpointInterface = "fr.ensibs.bakery.service.UsersService",
            serviceName = "UsersService",
            portName = "UsersPort")
public class UsersServiceImpl implements UsersService {
    
    /**
     * the DAO to handle users in the database
     */
    private UserDAO userDAO;
    
    /**
     * Constructor.
     * @throws SQLException when an error occurs
     */
    public UsersServiceImpl() throws SQLException {
        this.userDAO = UserDAO.getInstance();
    }
    
    @Override
    public String login(String name, String password) throws BakeryServiceException {
        // check that the user exists
        User user = this.userDAO.getUserByName(name);
        if (user == null)
            throw new BakeryServiceException(401);

        // check that the password is correct
        if (!BCrypt.checkpw(password, user.getPasswordHash()))
            throw new BakeryServiceException(401);

        // update the token
        String token = UUID.randomUUID().toString();
        this.userDAO.updateToken(user.getName(), token);
        return token;
    }

    @Override
    public void logout(String token) throws BakeryServiceException {
        // check that the user exists
        User user = this.userDAO.getUserByToken(token);
        if (user == null)
            throw new BakeryServiceException(401);

        this.userDAO.updateToken(user.getName(), null);
    }

    @Override
    public String register(String name, String password) throws BakeryServiceException {
        // check the validity of the parameters
        if (name == null || password == null || name.length() < 3 || password.length() < 3)
            throw new BakeryServiceException(400);

        // check that the user does not already exist
        User existingUser = this.userDAO.getUserByName(name);
        if (existingUser != null)
            throw new BakeryServiceException(409);

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        String token = UUID.randomUUID().toString();
        this.userDAO.createUser(name, passwordHash, token);
        return token;
    }

    @Override
    public ArrayList<User> getAllUsers(String adminToken) throws BakeryServiceException {
        // verify the token
        User user = this.userDAO.getUserByToken(adminToken);
        if (user == null)
            throw new BakeryServiceException(401);

        // check that the user is admin
        if (user.getRole() != Role.ADMIN)
            throw new BakeryServiceException(403);

        return this.userDAO.getAllUsers();
    }

    @Override
    public void setAdmin(String adminToken, String name, boolean isAdmin) throws BakeryServiceException {
        // verify the token
        User user = this.userDAO.getUserByToken(adminToken);
        if (user == null)
            throw new BakeryServiceException(401);

        // check that the user is admin
        if (user.getRole() != Role.ADMIN)
            throw new BakeryServiceException(403);

        this.userDAO.setAdmin(name, isAdmin);
    }

    @Override
    public void deleteUser(String adminToken, String name) throws BakeryServiceException {
        // verify the token
        User user = this.userDAO.getUserByToken(adminToken);
        if (user == null)
            throw new BakeryServiceException(401);

        // check that the user is admin
        if (user.getRole() != Role.ADMIN)
            throw new BakeryServiceException(403);

        this.userDAO.deleteUser(name);
    }

}
