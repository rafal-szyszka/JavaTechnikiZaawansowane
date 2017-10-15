package is.szyszka.manager;

import is.szyszka.billboard.IBillboard;
import is.szyszka.order.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by rafal on 15.10.17.
 */
public interface IManager extends Remote {

    int bindBillboard(IBillboard billboard) throws RemoteException;
    boolean unbindBillboard(int billboardId) throws RemoteException;

    boolean placeOrder(Order order, int billboardId) throws RemoteException;
    boolean withdrawOrder(int orderId, int billboardId) throws RemoteException;

}
