import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageConsumer {

    private static final String URL = "failover://tcp://localhost:61628";

    private static final String SUBJECT = "MY_QUEUE";

    public void getMessage() throws Exception {
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue(SUBJECT);

        // Create a MessageConsumer from the Session to the Topic or Queue
        javax.jms.MessageConsumer consumer = session.createConsumer(destination);

        // Wait for a message
        Message receivedMessage = consumer.receive();


        if (receivedMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) receivedMessage;
            String text = textMessage.getText();
            System.out.println("Received: " + text);
        } else {
            System.out.println("Received: " + receivedMessage);
        }

        consumer.close();
        session.close();
        connection.close();
    }
}
