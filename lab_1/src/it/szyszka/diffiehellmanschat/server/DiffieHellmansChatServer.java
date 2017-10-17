package it.szyszka.diffiehellmanschat.server;

import it.szyszka.diffiehellmanschat.client.ChatClient;
import it.szyszka.diffiehellmanschat.messages.ChatMessage;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class DiffieHellmansChatServer extends UnicastRemoteObject implements ChatServer {

    public static final int ARGS_PORT = 0;
    public static final int ARGS_NAME = 1;

    private ArrayList<ChatClient> registeredClients;

    protected DiffieHellmansChatServer(int port, ArrayList<ChatClient> registeredClients) throws RemoteException {
        super(port);
        this.registeredClients = registeredClients;
    }

    public static void main(String[] args) {
        Integer serverPort = Integer.parseInt(args[ARGS_PORT]);
        String serverName = args[ARGS_NAME];

        try {
            ChatServer server = new DiffieHellmansChatServer(serverPort, new ArrayList<>());
            Registry registry = LocateRegistry.createRegistry(serverPort);
            registry.bind(serverName, server);
            System.out.println("Server ready!");
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println("Failed to run server");
            e.printStackTrace();
        }

    }

    @Override
    public Boolean registerNewClient(ChatClient client) throws RemoteException {
        return null;
    }

    @Override
    public void receiveMessage(ChatMessage message) throws RemoteException {
        System.out.format("Server received message:\n---%s\n---\n", message.getContent());
        broadcastMessage(message);
    }

    @Override
    public void sendMessageTo(ChatClient client) throws RemoteException {

    }

    @Override
    public void broadcastMessage(ChatMessage message) throws RemoteException {
        if(registeredClients != null && !registeredClients.isEmpty()) {
            registeredClients.forEach(chatClient -> {
                try {
                    chatClient.receiveMessage(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}