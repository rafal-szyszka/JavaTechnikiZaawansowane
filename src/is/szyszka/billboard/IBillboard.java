package is.szyszka.billboard;

import is.szyszka.order.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Duration;

/**
 * Created by rafal on 15.10.17.
 */
public interface IBillboard extends Remote {

    boolean addAdvertisement(Order order) throws RemoteException;
    boolean removeAdvertisement(int orderId) throws RemoteException;
    int[] getCapacity() throws RemoteException; // returns table of two integers {totalSlots, freeSlots}
    void setDisplayInterval(Duration displayInterval) throws RemoteException;
    boolean start() throws RemoteException;
    boolean stop() throws RemoteException;

}
