package fr.ensibs.bakery;

import javax.xml.ws.Endpoint;

/**
 * The launcher of the 3 web services of the bakery.
 */
public class BakeryServicesPublisher {
    
    /**
     * the host name of the web services
     */
    private final static String HOST_NAME = "localhost";
    
    /**
     * the port number of the web services
     */
    private final static int PORT_NUMBER = 5000;
    
    /**
     * the users service name
     */
    private final static String USERS_SERVICE = "UsersService";
    
    /**
     * Launch the 3 services of the bakery.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws Exception {
        String usersAddress = "http://" + HOST_NAME + ":" + PORT_NUMBER + "/ws/" + USERS_SERVICE;
        Endpoint.publish(usersAddress, new UsersServiceImpl());
        System.out.println("Web service 1 published and running at: " + usersAddress);
        
        // TODO: 2 other services
    }
    
}
