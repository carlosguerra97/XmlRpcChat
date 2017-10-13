package client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcException;

/**
 * @author Carlos Guerra Yanez
 */
public class DaemonRefresher extends Thread {

    private final ClientManager client;
    private String messageLog;

    public DaemonRefresher(ClientManager client) {
        this.client = client;
        this.messageLog = new String();
    }
    
    public void addMessage(String message, String userName) {
        messageLog = messageLog + userName + ": " + message + " \n";
    }

    @Override
    public void run() {
        String print;
        String memoryBuffer = "";
        while (true) {
            try {
                memoryBuffer = (String) client.execute("ChatServices.returnMemory", new ArrayList<>());
                if (!messageLog.equals(memoryBuffer)) {
                    print = memoryBuffer.replaceFirst(messageLog, "");
                    System.out.print(print);
                    messageLog = memoryBuffer;
                }                
                sleep(100);
            } catch (XmlRpcException | InterruptedException ex) {
                System.err.println("Se ha producido un error en la ejecucion.");
                System.exit(0);
            }
        }
    }

}
