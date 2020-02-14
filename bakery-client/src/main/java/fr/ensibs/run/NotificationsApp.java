package fr.ensibs.run;

import fr.ensibs.notifications.NotificationBroker;
import fr.ensibs.notifications.NotificationBrokerImplService;
import fr.ensibs.notifications.TopicPath;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An application entry point used to launch a publisher or a subscriber.
 */
public class NotificationsApp
{

    /**
     * the broker that manages the registrations and messages
     */
    protected final NotificationBroker broker;

    /**
     * true if the application has been closed
     */
    protected boolean closed;

    /**
     * the prompt message displayed before asking for user commands
     */
    protected String prompt;

    /**
     * Print a usage message and exit
     */
    private static void usage()
    {
        System.out.println("Usage: java NotificationsApp [pub|sub] <name>");
        System.out.println("Launch a publisher or subscriber");
        System.exit(-1);
    }

    /**
     * Application entry point
     *
     * @param args see usage
     */
    public static void main(String[] args)
    {
        if (args.length != 2 || "-h".equals(args[0])) {
            usage();
        }

        NotificationBrokerImplService service = new NotificationBrokerImplService();
        NotificationBroker broker = service.getNotificationBrokerPort();
        String name = args[1];

        switch (args[0]) {
            case "pub":
                Publisher publisher = new Publisher(broker, name);
                publisher.run();
                break;

            case "sub":
                Subscriber subscriber = new Subscriber(broker, name);
                subscriber.run();
                break;

            default:
                usage();
        }
    }

    /**
     * Constructor.
     *
     * @param broker the broker that manages the registrations and messages
     */
    public NotificationsApp(NotificationBroker broker, String prompt)
    {
        this.broker = broker;
        this.prompt = prompt;
    }

    /**
     * Ask the user to enter commands to add or remove registrations and publish
     * messages and process the commands.
     */
    public void run()
    {
        Scanner scanner = new Scanner(System.in);

        while (!closed) {
            System.out.println(prompt);
            String line = scanner.nextLine();
            boolean processed = process(line);
            if (!processed) {
                System.err.println("Unable to process command: " + line);
            }
        }
    }

    //--------------------------------------------------------------
    // Private (and protected) methods
    //--------------------------------------------------------------
    /**
     * Process a user command
     *
     * @param line the line that contains the user command
     */
    protected boolean process(String line)
    {
        String[] tokens = line.split(" +");
        if (tokens.length == 1) {
            switch (tokens[0]) {
                case "topics":
                case "TOPICS":
                    topics();
                    return true;
                case "quit":
                case "QUIT":
                    close();
                    return true;
            }
        }
        return false;
    }

    /**
     * Displays the topics available on the topics space
     */
    protected void topics()
    {
        try {
            StringBuilder builder = new StringBuilder();
            for (TopicPath path : broker.getPaths()) {
                builder.append("\n").append(pathToString(path));
            }
            System.out.println(builder.toString());
        } catch (Exception e) {
            System.err.println("Error while getting the topics space: " + e.getClass().getName() + ". " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Stop processing the client commands.
     */
    protected void close()
    {
        closed = true;
    }

    protected TopicPath makeTopicPath(String path)
    {
        List<String> paths = new ArrayList<>(); // topicPath.getPaths();
        for (String name : path.split("/")) {
            if (!name.isEmpty()) {
                paths.add(name);
            }
        }
        String[] array = new String[paths.size()];
        paths.toArray(array);
        TopicPath topicPath = new TopicPath();
        for (String el : array)
            topicPath.getPaths().add(el);
        return topicPath;
    }

    /**
     * Give a string representation of a topic path expression
     *
     * @param path the topic path
     * @return string representation of the path
     */
    protected String pathToString(TopicPath path)
    {
        StringBuilder builder = new StringBuilder();
        for (String name : path.getPaths()) {
            builder.append("/").append(name);
        }
        return builder.toString();
    }

}
