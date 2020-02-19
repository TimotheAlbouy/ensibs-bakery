package fr.ensibs.bakery.impl;

import javax.xml.ws.Endpoint;

import java.sql.SQLException;

import static fr.ensibs.bakery.impl.Constants.USERS_SERVICE;
import static fr.ensibs.bakery.impl.Constants.ORDERS_SERVICE;

/**
 * The launcher of the 3 web services of the bakery.
 */
public class BakeryServicesPublisher {
    
    /**
     * Launch the 3 services of the bakery.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args.length < 2 || "-h".equals(args[0]))
            BakeryServicesPublisher.usage();

        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            if (port < 0 || port > 65535)
                throw new NumberFormatException();

            String usersAddress = "http://" + host + ":" + port + "/ws/" + USERS_SERVICE;
            Endpoint.publish(usersAddress, new UsersServiceImpl());
            System.out.println("[UsersService]: WSDL published and web service running at: " + usersAddress);

            String ordersAddress = "http://" + host + ":" + port + "/ws/" + ORDERS_SERVICE;
            Endpoint.publish(ordersAddress, new OrdersServiceImpl());
            System.out.println("[OrdersService]: WSDL published and web service running at: " + ordersAddress);

            // TODO: 1 other services
        } catch (NumberFormatException e) {
            System.out.println("The port number must be an integer between 0 and 65535.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while retrieving the database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Print a usage message and exit
     */
    private static void usage() {
        System.out.println("Usage: java BakeryServicesPublisher <host> <port>");
        System.out.println("Launch the 3 web services of the bakery");
        System.exit(0);
    }

}
