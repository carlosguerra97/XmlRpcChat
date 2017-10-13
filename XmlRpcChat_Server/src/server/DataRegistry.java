package server;

import java.util.ArrayList;

/**
 * @author Carlos Guerra Yanez
 */
public class DataRegistry {
    
    private String messageMemory;
    private ArrayList<String> userRegistry;
    
    public DataRegistry() {
        messageMemory = "\n==== Comienzo del registro de mensajes ==== \n \n";
        userRegistry = new ArrayList<>();
    }
    
    public void append(String messageAppended) {
        messageMemory = messageMemory.concat(messageAppended + " \n");
    }

    public String getMessageMemory() {
        return messageMemory;
    }

    public void setMessageMemory(String messageMemory) {
        this.messageMemory = messageMemory;
    }

    public ArrayList<String> getUserRegistry() {
        return userRegistry;
    }

    public void setUserRegistry(ArrayList<String> userRegistry) {
        this.userRegistry = userRegistry;
    }

    void cleanMessageMemory() {
        messageMemory = "";
    }
    
}
