package server;

/**
 * @author Carlos Guerra Yanez
 */
public class ChatService {

    private static final DataRegistry DATA = new DataRegistry();
    
    public Boolean login(String userName) {
        ChatService.DATA.append("El usuario " + userName + " se ha conectado.");
        ChatService.DATA.getUserRegistry().add(userName);
        return true;
    }

    public Boolean logout(String userName) {
        ChatService.DATA.append("El usuario " + userName + " ha abandonado el chat.");
        ChatService.DATA.getUserRegistry().remove(userName);
        return true;
    }

    public Boolean insertMessage(String message, String userName) {
        ChatService.DATA.append("-> " + userName + ": " + message);
        return true;
    }

    public String returnMemory() {
        return ChatService.DATA.getMessageMemory();
    }
    
    public String returnUsers() {
        String users = "";
        for (String user:ChatService.DATA.getUserRegistry()) {
            users = users + user + " \n";
        }
        return users;
    }

    public Boolean cleanMemory() throws InterruptedException {
        ChatService.DATA.cleanMessageMemory();
        ChatService.DATA.append("\n==== Comienzo del registro de mensajes ==== \n");
        return true;
    }

}
