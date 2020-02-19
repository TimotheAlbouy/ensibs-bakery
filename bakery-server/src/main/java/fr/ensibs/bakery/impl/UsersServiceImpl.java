package fr.ensibs.bakery.impl;

import fr.ensibs.bakery.model.User;
import fr.ensibs.bakery.model.UserDAO;
import fr.ensibs.bakery.service.UsersService;

import org.mindrot.jbcrypt.BCrypt;

import javax.jws.WebService;
import java.sql.SQLException;

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
    public UsersServiceImpl() throws SQLException, ClassNotFoundException {
        this.userDAO = UserDAO.getInstance();
    }
    
    @Override
    public String login(String name, String password) {
        User user = this.userDAO.getUserByName(name);

        // if the user with the given name does not exist
        if (user == null)
            return null;

        // if the given password is incorrect
        if (BCrypt.checkpw(password, user.getPasswordHash()))
            return null;

        return Auth.sign(user.getName());
    }

    @Override
    public String register(String name, String password) {
        User existingUser = this.userDAO.getUserByName(name);

        // if the user with the given name already exists
        if (existingUser != null)
            return null;

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        this.userDAO.createUser(name, passwordHash);
        User user = this.userDAO.getUserByName(name);

        // if an error occurred during the user's creation
        if (user == null)
            return null;

        return Auth.sign(user.getName());
    }

}
