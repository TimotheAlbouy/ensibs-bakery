package fr.ensibs.bakery.impl;

import fr.ensibs.bakery.model.Role;
import fr.ensibs.bakery.model.User;
import fr.ensibs.bakery.model.UserDAO;
import fr.ensibs.bakery.service.UsersService;

import org.mindrot.jbcrypt.BCrypt;

import javax.jws.WebService;
import java.sql.SQLException;
import java.util.ArrayList;

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
     */
    public UsersServiceImpl() throws SQLException {
        this.userDAO = UserDAO.getInstance();
    }
    
    @Override
    public String login(String name, String password) throws BakeryServiceException {
        User user = this.userDAO.getUserByName(name);

        // if the user with the given name does not exist
        if (user == null)
            throw new BakeryServiceException(404);

        // if the given password is incorrect
        if (!BCrypt.checkpw(password, user.getPasswordHash()))
            throw new BakeryServiceException(401);

        return Auth.sign(user.getName(), user.getRole() == Role.ADMIN);
    }

    @Override
    public String register(String name, String password) throws BakeryServiceException {
        User existingUser = this.userDAO.getUserByName(name);

        // if the user with the given name already exists
        if (existingUser != null)
            throw new BakeryServiceException(409);

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        this.userDAO.createUser(name, passwordHash);
        User user = this.userDAO.getUserByName(name);
        return Auth.sign(user.getName(), user.getRole() == Role.ADMIN);
    }

    @Override
    public ArrayList<User> getAllUsers(String token) throws BakeryServiceException {
        // verify the token
        String name = Auth.verify(token, true);
        if (name == null)
            throw new BakeryServiceException(403);

        return this.userDAO.getAllUsers();
    }

}
