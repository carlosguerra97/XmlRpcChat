package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClientRequestImpl;
import org.apache.xmlrpc.common.XmlRpcHttpRequestConfigImpl;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;

/**
 * @author Carlos Guerra Yanez
 */
public class XmlRpcChatServer {

    public static void main(String[] args) {
        try {
            // Inicializando el Scannery el ChatService
            Scanner sc = new Scanner(System.in);
            ChatService log = new ChatService();

            // Parametros del servidor
            int port;
            System.out.println("Introduce el puerto sobre el que quieres lanzar el servidor:");
            do {
                System.out.print(">> ");
                port = sc.nextInt();
            } while (port < 1025);
            WebServer webServer = new WebServer(port);
            XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
            PropertyHandlerMapping phm = new PropertyHandlerMapping();
            phm.addHandler("ChatServices", server.ChatService.class);
            xmlRpcServer.setHandlerMapping(phm);

            // Inicializar el servidor
            webServer.start();

            // Mensaje de inicializacion correcta
            System.out.println();
            System.out.println("Se ha inicializado correctamente el servidor.");

            // Instrucciones para borrado de memoria o salida del servidor
            System.out.println();
            System.out.println("A partir de ahora usted puede limpiar la memoria de mensajes \n"
                    + "utilizando el comando clean, elaborar una lista con los usuarios actuales, \n"
                    + " o cerrar el servidor con el comando shutdown.");
            while (true) {
                System.out.print(">> ");
                String command = sc.next();
                if (command.equalsIgnoreCase("clean")) {
                    xmlRpcServer.execute(new XmlRpcClientRequestImpl(new XmlRpcHttpRequestConfigImpl(), "ChatServices.cleanMemory", new ArrayList<>()));
                    System.out.println("Se ha limpiado el registro de mensajes.");
                } else if (command.equalsIgnoreCase("shutdown")) {
                    webServer.shutdown();
                    Thread.sleep(500);
                    System.out.println("El servidor se ha cerrado correctamente.");
                    sc.close();
                    System.exit(0);
                } else if (command.equalsIgnoreCase("whoishere")) {
                    String users = (String) xmlRpcServer.execute(new XmlRpcClientRequestImpl(new XmlRpcHttpRequestConfigImpl(), "ChatServices.returnUsers", new ArrayList<>()));
                        System.err.println("Los usuarios conectados actualmente son: ");
                        System.err.print(users);
                } else {
                    System.out.println("Ha introducido un comando no especificado.");
                }
            }

        } catch (XmlRpcException | IOException | InterruptedException ex) {
            Logger.getLogger(XmlRpcChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
