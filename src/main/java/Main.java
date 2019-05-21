import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


public class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Integer port = 8085;
        if (args.length != 0) {
            port = Integer.parseInt(args[0]);
        }
        startWebServer(port);
    }

    public static void startWebServer(Integer port) {
        Server server = new Server(port);

        System.out.println("Start server on: " + port);
        logger.info("Start server on: " + port);
        ServletContextHandler sContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        server.setHandler(sContextHandler);

        sContextHandler.addServlet(ServletDispatcher.class, "/");
        startMessageBroker();
        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            server.destroy();
        }
    }
    private static void startMessageBroker(){
        //adding embedded broker
        BrokerService broker = new BrokerService();
        try {
            broker.addConnector("tcp://localhost:61628");
            broker.start();
            System.out.println("broker start at address: \"tcp://localhost:61628\"");
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }

        try {
            //create sender and send message to topic
            MessageSender messageSender = new MessageSender();
            messageSender.sendMessage("My message:)");
            //create receiver and get message from topic
            MessageConsumer messageConsumer = new MessageConsumer();
            messageConsumer.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
