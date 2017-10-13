package client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * @author Carlos Guerra Yanez
 */
public class ClientManager {
    
    XmlRpcClient client;
    
    public ClientManager(String ip,  String port) throws MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://" + ip + ":" + port + "/xmlrpc"));
        this.client = new XmlRpcClient();
        this.client.setConfig(config);
    }
    
    public Object execute(String pMethod, List params) throws XmlRpcException {
        return client.execute(pMethod, params);
    }
    
}
