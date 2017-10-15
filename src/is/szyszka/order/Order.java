package is.szyszka.order;

import is.szyszka.client.IClient;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.Duration;

/**
 * Created by rafal on 15.10.17.
 */
public class Order implements Serializable {

    public String advertText;
    public Duration displayPeriod;
    public IClient client;

    public Order(String advertText, Duration displayPeriod, IClient client) {
        this.advertText = advertText;
        this.displayPeriod = displayPeriod;
        this.client = client;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("\nCONTENT: ").append(advertText)
                    .append("\nCLIENT'S NAME: ").append(client.getClientName())
                    .append("\n");
        } catch (RemoteException e) {
            e.printStackTrace();
            return "";
        }
        return builder.toString();
    }
}
