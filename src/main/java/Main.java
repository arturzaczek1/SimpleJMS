import org.apache.activemq.broker.BrokerService;

public class Main {
    public static void main(String[] args) throws Exception{
        //adding embedded broker
        BrokerService broker = new BrokerService();
        broker.addConnector("tcp://localhost:61622");
        broker.start();

        //create sender and send message to topic
        MessageSender messageSender = new MessageSender();
        messageSender.sendMessage("My message:)");


        //create receiver and get message from topic
        MessageConsumer messageConsumer = new MessageConsumer();
        messageConsumer.getMessage();
    }
}
