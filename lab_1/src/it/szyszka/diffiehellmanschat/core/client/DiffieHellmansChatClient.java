package it.szyszka.diffiehellmanschat.core.client;

import it.szyszka.diffiehellmanschat.TerminalInterface;
import it.szyszka.diffiehellmanschat.core.messages.ChatMessage;
import it.szyszka.diffiehellmanschat.core.server.ChatServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DiffieHellmansChatClient extends UnicastRemoteObject implements ChatClient{

    private String nickname;
    private ChatServer chatServer;
    private ClientInterface clientInterface;

    protected DiffieHellmansChatClient(String nickname) throws RemoteException {
        this.nickname = nickname;
    }

    public static void main(String argv[]) throws Exception {
        TerminalInterface reader = TerminalInterface.getInstance();
        ClientConnectionInitializer initializer = new ClientConnectionInitializer();
        initializer.initialize(reader);

        DiffieHellmansChatClient client = (DiffieHellmansChatClient) initializer.getClient();
        client.setChatServer(initializer.getServer());
        client.setClientInterface(reader);
        client.run(reader);
    }

    public void run(TerminalInterface reader) throws RemoteException {
        String input = "";
        while (!input.equalsIgnoreCase("q")) {
            input = reader.readString("");
            sendMessageToServer(input);
        }
    }

    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    public void sendMessageToServer(String message) throws RemoteException {
        if(chatServer != null) {
            chatServer.receiveMessage(new ChatMessage(nickname, message));
            if(message.equalsIgnoreCase("q")) dropConnection();
        } else {
            clientInterface.writeMessage("No connection to server.");
        }
    }

    private void dropConnection() {
        chatServer = null;
    }

    public void setChatServer(ChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public String getClientNickname() throws RemoteException {
        return nickname;
    }

    @Override
    public void receiveMessage(ChatMessage message) throws RemoteException {
        clientInterface.writeMessage("<" + message.getSenderNickname() + "> " + message.getContent() + "\n");
    }
}