package it.szyszka.diffiehellmanschat.client;

import it.szyszka.diffiehellmanschat.TerminalReader;
import it.szyszka.diffiehellmanschat.server.ChatServer;
import it.szyszka.diffiehellmanschat.server.Credentials;

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
    private TerminalReader reader;

    private ChatClient client;
    private ChatServer server;

    public ChatServer initialize(TerminalReader reader) {
        this.reader = reader;
        serverCredentials = readServerCredentials();
        requestServerConnection();
        return null;
    }

    private void requestServerConnection() {
        Registry registry = null;
        try {
            System.out.format("Connecting to %s...\n", serverCredentials.getName());
            registry = LocateRegistry.getRegistry(serverCredentials.getPort());
        } catch (RemoteException e) {
            System.err.format("Connection on port %d rejected.\n", serverCredentials.getPort());
            e.printStackTrace();
        }
        try {
            server = (ChatServer) registry.lookup(serverCredentials.getName());
            registerNewClient(server);
        } catch (RemoteException | NotBoundException e) {
            System.err.format("Server with name %s doesn't exist.\n", serverCredentials.getName());
            e.printStackTrace();
        }
    }

    private void registerNewClient(ChatServer chatServer) {
        try {
            client = new DiffieHellmansChatClient(
                    reader.readString(READ_USER_NICKNAME)
            );
            System.out.format("Registering client %s...\n", client.getClientNickname());
            Boolean isClientRegistered = chatServer.registerNewClient(client);
            if (isClientRegistered) {
                System.out.format("%s connected to %s.\n", client.getClientNickname(), serverCredentials.getName());
            } else {
                System.err.format("Failed to connect to: %s.\n", serverCredentials.getName());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Credentials readServerCredentials() {
        return new Credentials(
                reader.readInt(READ_SERVER_PORT),
                reader.readString(READ_SERVER_NAME)
        );
    }

    public ChatClient getClient() {
        return client;
    }

    public ChatServer getServer() {
        return server;
    }

}
