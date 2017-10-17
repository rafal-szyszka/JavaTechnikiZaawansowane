package it.szyszka.diffiehellmanschat.gui.client;

import it.szyszka.diffiehellmanschat.core.client.ClientConnectionInitializer;
import it.szyszka.diffiehellmanschat.core.client.DiffieHellmansChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

/**
 * Created by rafal on 17.10.17.
 */
public class ClientController {

    @FXML TextArea chatWindow;
    @FXML TextField sendMessage;
    @FXML TextField serverPort;
    @FXML TextField serverName;
    @FXML TextField nickname;

    private DiffieHellmansChatClient client;

    public void sendMessage(ActionEvent actionEvent) {
        String input = ((TextField)actionEvent.getSource()).getText();
        try {
            client.sendMessageToServer(input);
        } catch (RemoteException e) {
            e.printStackTrace();
            chatWindow.appendText("Failed to send message.\n");
        }
        ((TextField)actionEvent.getSource()).setText("");
    }

    public void connectToServer(ActionEvent actionEvent) {
        GraphicInterface reader = new GraphicInterface(serverPort, serverName, nickname, chatWindow);
        ClientConnectionInitializer initializer = new ClientConnectionInitializer();
        initializer.initialize(reader);

        client = (DiffieHellmansChatClient) initializer.getClient();
        client.setClientInterface(reader);
        client.setChatServer(initializer.getServer());
    }
}
