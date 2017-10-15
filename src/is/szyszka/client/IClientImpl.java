package is.szyszka.client;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by rafal on 15.10.17.
 */
public class IClientImpl extends UnicastRemoteObject implements IClient {

    public static final int PORT = 0;
    public static final int NAME = 1;
    public static final int CLIENT_NAME = 2;

    String clientName;

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        IClient adas = new IClientImpl(args[CLIENT_NAME], Integer.parseInt(args[PORT]));
        Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[PORT]));
        registry.bind(args[NAME], adas);
        System.out.println("Client in registry under name: " + args[NAME]);
    }

    public IClientImpl(String clientName, int port) throws RemoteException {
        super(port);
        this.clientName = clientName;
        System.out.println("Client " + clientName + " created!");
    }

    @Override
    public void setOrderId(int orderId) throws RemoteException {

    }

    @Override
    public String getClientName() throws RemoteException {
        return clientName;
    }
}
