package fr.ensibs.bakery;

public class UserDAO {

    /**
     * the single instance
     */
    private static UserDAO instance;

    /**
     * Private constructor for the singleton pattern.
     */
    private UserDAO() {
        //
    }
    
    /**
     * Instance getter for the singleton pattern.
     */
    public static UserDAO getInstance() {
        if (UserDAO.instance == null)
            UserDAO.instance = new UserDAO();
        return UserDAO.instance;
    }

}
