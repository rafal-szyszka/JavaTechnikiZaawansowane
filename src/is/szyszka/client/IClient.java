package is.szyszka.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by rafal on 15.10.17.
 */
public interface IClient extends Remote {

    void setOrderId(int orderId) throws RemoteException;
    String getClientName() throws RemoteException;

}
