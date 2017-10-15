package is.szyszka.billboard;

import is.szyszka.order.Order;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by rafal on 15.10.17.
 */
public class IBillboardImpl extends UnicastRemoteObject implements IBillboard {

    private static final int TOTAL_CAPACITY = 0;
    private static final int FREE_SLOTS = 1;

    private static final int PORT = 0;
    private static final int NAME = 1;
    private static final int INITIAL_CAPACITY = 2;

    ArrayList<Order> orders;
    int[] capacity;

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        IBillboard billboard = new IBillboardImpl(
                new ArrayList<>(), Integer.parseInt(args[INITIAL_CAPACITY]), Integer.parseInt(args[PORT])
        );
        Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[PORT]));
        registry.bind(args[NAME], billboard);
        System.out.println("Billboard in registry under name: " + args[NAME]);
    }

    public IBillboardImpl(ArrayList<Order> orders, int capacity, int port) throws RemoteException {
        super(port);
        this.orders = orders;
        this.capacity = new int[]{capacity, capacity};
    }

    @Override
    public boolean addAdvertisement(Order order) throws RemoteException {
        if(orders != null && orders.size() < capacity[FREE_SLOTS]) {
            orders.add(order);
            capacity[FREE_SLOTS]--;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAdvertisement(int orderId) throws RemoteException {
        if(orders.size() > 0) {
            orders.remove(orderId);
            capacity[FREE_SLOTS]++;
            return true;
        }
        return false;
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        return capacity;
    }

    @Override
    public void setDisplayInterval(Duration displayInterval) throws RemoteException {

    }

    @Override
    public boolean start() throws RemoteException {
        if(orders.size() > 0) {
            orders.forEach(order -> System.out.println(order.toString()));
            return true;
        }
        return false;
    }

    @Override
    public boolean stop() throws RemoteException {
        return false;
    }
}
