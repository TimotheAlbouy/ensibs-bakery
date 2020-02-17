package fr.ensibs.bakery.run;

import fr.ensibs.bakery.service.UsersService;
import fr.ensibs.bakery.service.UsersService_Service;

import java.util.Scanner;

/**
 * An application entry point used to launch a bakery client.
 */
public class BakeryApp {

    /**
     * the web service managing users of the bakery
     */
    protected final UsersService usersService;

    /**
     * true if the application has been closed
     */
    protected boolean closed;

    /**
     * the prompt message displayed before asking for user commands
     */
    protected static final String PROMPT = "Gneugneu";

    /**
     * Print a usage message and exit
     */
    private static void usage() {
        System.out.println("Usage: java BakeryApp");
        System.out.println("Launch a bakery client");
        System.exit(-1);
    }

    /**
     * Application entry point
     *
     * @param args see usage
     */
    public static void main(String[] args) {
        if (args.length != 2 || "-h".equals(args[0]))
            usage();

        UsersService_Service service = new UsersService_Service();
        UsersService usersService = service.getUsersPort();

        BakeryApp app = new BakeryApp(usersService);
        app.run();
    }

    /**
     * Constructor.
     *
     * @param usersService the web service managing users of the bakery
     */
    public BakeryApp(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Ask the user to enter commands.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (!closed) {
            System.out.println(PROMPT);
            String line = scanner.nextLine();
            boolean processed = false;
            if (!processed) {
                System.err.println("Unable to process command: " + line);
            }
        }
    }

}
