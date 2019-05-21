import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageSender {

    //URL of the JMS server.
    //DEFAULT_BROKER_URL( tcp://localhost:61616") will just mean that JMS server is on localhost
    private static final String URL = "failover://tcp://localhost:61622";

    // default broker URL is : tcp://localhost:61616"
    private static final String SUBJECT = "MY_QUEUE";

    public void sendMessage(String messageContent) throws Exception{
        //        creating connection
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(SUBJECT);

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        String text = Thread.currentThread().getName() + " Artur Zaczek ,send message: "+  messageContent;
        TextMessage message = session.createTextMessage(text);

        // Tell the producer to send the message
        System.out.println("Sent message: "+ message.getText() + " : " + Thread.currentThread().getName());
        producer.send(message);

        // Clean up
        session.close();
        connection.close();
    }
}
