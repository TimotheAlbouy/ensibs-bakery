package fr.ensibs.run;

import fr.ensibs.notifications.NotificationBroker;
import fr.ensibs.notifications.NotificationMessage;
import fr.ensibs.notifications.NotificationProducer;
import fr.ensibs.notifications.ProducerRegistration;
import fr.ensibs.notifications.TopicPath;
import java.util.HashMap;
import java.util.Map;

/**
 * A publisher that uses a producer and a broker in order to publish messages on
 * topics.
 *
 * The {@link #run()} method allows a user to enter commands in order to
 * register/unregister the producer and publish messages. This method uses the
 * System.in instance (should not be used in a program that also
 * accesses this instance).
 */
public class Publisher extends NotificationsApp
{

    /**
     * the prompt message displayed before asking for user commands
     */
    private static final String PROMPT = "Enter TOPICS, REGISTER <path>, UNREGISTER <id>, PUBLISH <path> <message>, QUIT commands to manage the messages publications";

    /**
     * the producer of notification messages
     */
    private final NotificationProducer producer;

    /**
     * the currently set registrations for this producer
     */
    private final Map<String, ProducerRegistration> registrations;

    /**
     * Constructor.
     *
     * @param broker the broker that manages the registrations and messages
     * @param name the producer name
     */
    public Publisher(NotificationBroker broker, String name)
    {
        super(broker, PROMPT);
        this.registrations = new HashMap<>();
        this.producer = new NotificationProducer();
        this.producer.setName(name);
    }

    //--------------------------------------------------------------
    // Private methods
    //--------------------------------------------------------------
    @Override
    protected boolean process(String line)
    {
        if (super.process(line)) {
            return true;
        }
        String[] tokens = line.split(" +");
        if (tokens.length > 2) {
            switch (tokens[0]) {
                case "publish":
                case "PUBLISH":
                    int idx = line.indexOf(tokens[2]);
                    publish(tokens[1], line.substring(idx));
                    return true;
            }
        } else if (tokens.length > 1) {
            switch (tokens[0]) {
                case "register":
                case "REGISTER":
                    register(tokens[1]);
                    return true;
                case "unregister":
                case "UNREGISTER":
                    unregister(tokens[1]);
                    return true;
            }
        }
        return false;
    }

    /**
     * Register this producer for the topics represented by the given path
     * expression.
     *
     * @param path a path expression (possibly containing wildcards)
     */
    private void register(String path)
    {
        try {
            TopicPath topicPath = makeTopicPath(path);
            ProducerRegistration registration = broker.register(producer, topicPath);
            if (registration != null) {
                String id = registration.getRegistrationId();
                registrations.put(id, registration);
                producer.getRegistrations().add(registration);
                System.out.println("Producer successfully registered. Registration id: " + id);
            } else {
                System.err.println("Producer registration failed");
            }
        } catch (Exception e) {
            System.err.println("Error while registering the producer: " + e.getClass().getName() + ". " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Unregister this producer.
     *
     * @param id the registration id provided at the registration
     */
    private void unregister(String id)
    {
        try {
            ProducerRegistration registration = registrations.remove(id);
            if (registration != null) {
                producer.getRegistrations().remove(registration);
                broker.unregister(registration);
                System.out.println("Producer successfully unregistered");
            } else {
                System.err.println("Unknown registration id: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error while unregistering the producer: " + e.getClass().getName() + ". " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Publish a message on a topic. The topic must be supported by the
     * producer.
     *
     * @param path a concrete path representing a topic
     * @param payload the message payload
     */
    private void publish(String path, String payload)
    {
        try {
            TopicPath topicPath = makeTopicPath(path);
            NotificationMessage message = broker.createMessage(topicPath, producer, payload);
            broker.notify(message);
            System.out.println("Message successfully published");
        } catch (Exception e) {
            System.err.println("Error while publishing the message: " + e.getClass().getName() + ". " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Unregister the producer and stop processing the client commands.
     */
    @Override
    protected void close()
    {
        super.close();
        try {
            for (ProducerRegistration registration : registrations.values()) {
                broker.unregister(registration);
            }
            System.out.println("Producer closed");
        } catch (Exception e) {
            System.err.println("Error while closing the producer: " + e.getClass().getName() + ". " + e.getMessage());
            e.printStackTrace();
        }
    }
}
