package it.szyszka.diffiehellmanschat.core.client;

import it.szyszka.diffiehellmanschat.core.server.ChatServer;
import it.szyszka.diffiehellmanschat.core.server.Credentials;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by rafal on 17.10.17.
 */
public class ClientConnectionInitializer {

    private static final String READ_SERVER_PORT = "Enter server's port number: ";
    private static final String READ_SERVER_NAME = "Enter server's name: ";
    private static final String READ_USER_NICKNAME = "Enter your nickname: ";

    private Credentials serverCredentials;
    private ClientInterface reader;

    private ChatClient client;
    private ChatServer server;

    public ChatServer initialize(ClientInterface reader) {
        this.reader = reader;
        serverCredentials = readServerCredentials();
        requestServerConnection();
        return null;
    }

    private void requestServerConnection() {
        Registry registry = null;
        try {
            reader.writeMessage("Connecting to " + serverCredentials.getName() + "...\n");
            registry = LocateRegistry.getRegistry(serverCredentials.getPort());
        } catch (RemoteException e) {
            reader.writeMessage("Connection on port " + serverCredentials.getPort() + " rejected.\n");
            e.printStackTrace();
        }
        try {
            server = (ChatServer) registry.lookup(serverCredentials.getName());
            registerNewClient(server);
        } catch (RemoteException | NotBoundException e) {
            reader.writeMessage("Server with name " + serverCredentials.getName() + " doesn't exist.\n");
            e.printStackTrace();
        }
    }

    private void registerNewClient(ChatServer chatServer) {
        try {
            client = new DiffieHellmansChatClient(
                    reader.readNickname(READ_USER_NICKNAME)
            );
            reader.writeMessage("Registering client " + client.getClientNickname() + "...\n");
            Boolean isClientRegistered = chatServer.registerNewClient(client);
            if (isClientRegistered) {
                reader.writeMessage(client.getClientNickname() + " connected to " + serverCredentials.getName() + "\n");
            } else {
                reader.writeMessage("Failed to connect to: " + serverCredentials.getName() + "\n");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Credentials readServerCredentials() {
        return new Credentials(
                reader.readPort(READ_SERVER_PORT),
                reader.readName(READ_SERVER_NAME)
        );
    }

    public ChatClient getClient() {
        return client;
    }

    public ChatServer getServer() {
        return server;
    }

}
