package fr.ensibs.run;

import fr.ensibs.notifications.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;

/**
 * A subscriber that uses a consumer and a broker in order to receive messages
 * from topics.
 *
 * The {@link #run()} method allows a user to enter commands in order to
 * register/unregister the producer and publish messages. This method uses the
 * System.in instance (should not be used in a program that also
 * accesses this instance).
 */
public class Subscriber extends NotificationsApp
{

    /**
     * the prompt message displayed before asking for user commands
     */
    private static final String PROMPT = "Enter TOPICS, SUBSCRIBE <path>, UNSUBSCRIBE <id>, QUIT commands to manage the messages publications";

    /**
     * the consumer of notification messages
     */
    private final NotificationConsumer consumer;

    /**
     * the currently set registrations for this consumer
     */
    private final Map<String, ConsumerSubscription> subscriptions;

    /**
     * Constructor.
     *
     * @param broker the broker that manages the registrations and messages
     * @param name the consumer name
     */
    public Subscriber(NotificationBroker broker, String name)
    {
        super(broker, PROMPT);
        this.subscriptions = new HashMap<>();
        this.consumer = new NotificationConsumer();
        this.consumer.setName(name);
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
        if (tokens.length > 1) {
            switch (tokens[0]) {
                case "subscribe":
                case "SUBSCRIBE":
                    subscribe(tokens[1]);
                    return true;
                case "unsubscribe":
                case "UNSUBSCRIBE":
                    unsubscribe(tokens[1]);
                    return true;
            }
        }
        return false;
    }

    /**
     * Register this consumer for the topics represented by the given path
     * expression.
     *
     * @param path a path expression (possibly containing wildcards)
     */
    private void subscribe(String path)
    {
        try {
            TopicPath topicPath = makeTopicPath(path);
            ConsumerSubscription subscription = broker.subscribe(consumer, topicPath);
            if (subscription != null) {
                String id = subscription.getSubscriptionId();
                subscriptions.put(id, subscription);
                consumer.getSubscriptions().add(subscription);

                AsyncHandler<FetchMessageResponse> handler = new AsyncHandler<FetchMessageResponse>() {
                    @Override
                    public void handleResponse(Response<FetchMessageResponse> messageResponse) {
                        try {
                            // to check if the user didn't unsubscribe
                            if (subscriptions.containsValue(subscription)) {
                                NotificationMessage message = messageResponse.get().getReturn();
                                String messagePath = pathToString(message.getTopicPath());
                                System.out.println("[" + messagePath + "]: " + message.getPayload());
                                broker.fetchMessageAsync(subscription, this);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                broker.fetchMessageAsync(subscription, handler);
                System.out.println("Consumer successfully subscribed. Registration id: " + id);
            } else {
                System.err.println("Consumer subscription failed");
            }
        } catch (Exception e) {
            System.err.println("Error while subscribing the consumer: " + e.getClass().getName() + ". " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Unregister this consumer.
     *
     * @param id the registration id provided at the registration
     */
    private void unsubscribe(String id)
    {
        try {
            ConsumerSubscription subscription = subscriptions.remove(id);
            if (subscription != null) {
                consumer.getSubscriptions().remove(subscription);
                broker.unsubscribe(subscription);
                System.out.println("Consumer successfully unsubscribed");
            } else {
                System.err.println("Unknown subscription id: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error while unsubscribing the consumer: " + e.getClass().getName() + ". " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Unregister the consumer and stop processing the client commands.
     */
    @Override
    protected void close()
    {
        super.close();
        try {
            for (ConsumerSubscription subscription : subscriptions.values()) {
                broker.unsubscribe(subscription);
            }
            System.out.println("Consumer closed");
        } catch (Exception e) {
            System.err.println("Error while closing the consumer: " + e.getClass().getName() + ". " + e.getMessage());
            e.printStackTrace();
        }
    }
}
