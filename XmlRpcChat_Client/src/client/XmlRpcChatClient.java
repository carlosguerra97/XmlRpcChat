package client;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcException;

/**
 * @author Carlos Guerra Yanez
 */
public class XmlRpcChatClient {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            String ip;
            String port;
            ArrayList<String> userName = new ArrayList<>();
            ArrayList<String> message = new ArrayList<>();
            ClientManager client;
            DaemonRefresher refresher;
            System.out.print("Bienvenido, introduzca la direccion IP del servidor: ");
            ip = sc.next();
            System.out.print("Introduzca el puerto a traves del cual se va a ejecutar la conexion: ");
            port = sc.next();
            client = new ClientManager(ip, port);
            refresher = new DaemonRefresher(client);
            System.out.println();
            System.out.println("Se han introducido los datos del servidor, pueden no ser correctos.");
            System.out.println();
            System.out.print("A continuacion introduzca su nombre de usuario: ");
            userName.add(sc.next());
            System.out.println();
            System.out.println("Puede usted comenzar a enviar mensajes con el comando send, \n"
                    + "si quiere saber qué usuarios hay conectados, introduzca el comando whoishere, \n"
                    + "para salir del chat, introduzca el comando quitchat");
            client.execute("ChatServices.login", userName);
            refresher.start();
            while (true) {
                String command = sc.next();
                switch (command) {
                    case "quitchat":
                        client.execute("ChatServices.logout", userName);
                        System.err.println("Has salido del programa correctamente.");
                        sc.close();
                        System.exit(0);
                        break;
                    case "whoishere":
                        String users = (String) client.execute("ChatServices.returnUsers", new ArrayList<>());
                        System.err.println("Los usuarios conectados actualmente son: ");
                        System.err.print(users);
                        break;
                    case "send":
                        message = new ArrayList<>();
                        message.add(sc.nextLine().substring(1));
                        message.add(userName.get(0));
                        client.execute("ChatServices.insertMessage", message);
                        break;
                    default:
                        break;
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(XmlRpcChatClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XmlRpcException ex) {
            System.err.println("Se ha producido un error en la ejecucion.");
            System.exit(0);
        }
    }

}
