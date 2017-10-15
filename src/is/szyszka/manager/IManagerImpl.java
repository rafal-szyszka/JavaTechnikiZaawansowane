package is.szyszka.manager;

import is.szyszka.billboard.IBillboard;
import is.szyszka.billboard.IBillboardImpl;
import is.szyszka.client.IClient;
import is.szyszka.client.IClientImpl;
import is.szyszka.order.Order;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Scanner;

public class IManagerImpl implements IManager {

    ArrayList<IBillboard> billboards;
    public static final int ADAS_PORT = 2001;
    public static final String ADAS_NAME = "K_ADAS";
    public static final int LUCJAN_PORT = 2002;
    public static final String LUCJAN_NAME = "K_LUCJAN";
    public static final int BILLBOARD_PORT = 3001;
    public static final String BILLBOARD_NAME = "B_1";

    public static void main(String[] args) throws RemoteException, NotBoundException {

        IClient client1 = (IClient) LocateRegistry.getRegistry(ADAS_PORT).lookup(ADAS_NAME);
        IClient client2 = (IClient) LocateRegistry.getRegistry(LUCJAN_PORT).lookup(LUCJAN_NAME);
        IBillboard billboard1 = (IBillboard) LocateRegistry.getRegistry(BILLBOARD_PORT).lookup(BILLBOARD_NAME);

        System.out.println(billboard1.getCapacity()[0]);

        IManagerImpl manager = new IManagerImpl(new ArrayList<>());
        manager.bindBillboard(billboard1);

        manager.run(client1, client2);
    }

    private void run(IClient client1, IClient client2) throws RemoteException {
        Scanner in = new Scanner(System.in);
        String input = "";
        while(!input.equalsIgnoreCase("Q")) {
            System.out.println(">> " + client1.getClientName() + " place your order (or press \"n\"): ");
            input = in.nextLine();
            if(!input.equalsIgnoreCase("n")) {
                placeOrder(new Order(input, null, client1), 0);
            }
            System.out.println(">> " + client2.getClientName() + " place your order (or press \"n\"): ");
            input = in.nextLine();
            if(!input.equalsIgnoreCase("n")) {
                placeOrder(new Order(input, null, client2), 0);
            }
            System.out.println(billboards.get(0).getCapacity()[1]);
            billboards.get(0).start();
            System.out.println("You wish to quit? (q): ");
            input = in.nextLine();
        }
    }

    public IManagerImpl(ArrayList<IBillboard> billboards) {
        this.billboards = billboards;
    }

    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {
        billboards.add(billboard);
        return billboards.size() - 1;
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        if(billboardId < billboards.size()) {
            billboards.remove(billboardId);
            return true;
        }
        return false;
    }

    @Override
    public boolean placeOrder(Order order, int billboardId) throws RemoteException {
        if(billboardId < billboards.size()) {
            return billboards.get(billboardId).addAdvertisement(order);
        }
        return false;
    }

    @Override
    public boolean withdrawOrder(int orderId, int billboardId) throws RemoteException {
        if(billboardId < billboards.size()) {
            return billboards.get(billboardId).removeAdvertisement(orderId);
        }
        return false;
    }
}
