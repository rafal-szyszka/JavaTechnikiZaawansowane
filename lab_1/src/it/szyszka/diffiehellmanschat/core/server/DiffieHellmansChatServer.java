package it.szyszka.diffiehellmanschat.core.server;

import it.szyszka.diffiehellmanschat.core.client.ChatClient;
import it.szyszka.diffiehellmanschat.core.messages.ChatMessage;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class DiffieHellmansChatServer extends UnicastRemoteObject implements ChatServer {

    public static final int ARGS_PORT = 0;
    public static final int ARGS_NAME = 1;

    private static Registry registry;
    private ArrayList<ChatClient> registeredClients;
    private String name;

    protected DiffieHellmansChatServer(int port, String name, ArrayList<ChatClient> registeredClients) throws RemoteException {
        super(port);
        this.registeredClients = registeredClients;
        this.name = name;
    }

    public static void main(String[] args) {
        Integer serverPort = Integer.parseInt(args[ARGS_PORT]);
        String serverName = args[ARGS_NAME];

        try {
            ChatServer server = new DiffieHellmansChatServer(serverPort, serverName, new ArrayList<>());
            registry = LocateRegistry.createRegistry(serverPort);
            registry.bind(serverName, server);
            System.out.println("Server ready!");
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println("Failed to run server");
            e.printStackTrace();
        }

    }

    @Override
    public Boolean registerNewClient(ChatClient client) throws RemoteException {
        try {
            registry.bind(client.getClientNickname(), client);
            registeredClients.add(client);
            //broadcastMessage(new ChatMessage(name, "Client " + client.getClientNickname() + " has joined."));
            return true;
        } catch (AlreadyBoundException e) {
            System.err.format("Failed to register client: %s", client.getClientNickname());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void receiveMessage(ChatMessage message) throws RemoteException {
        System.out.format("Server received message:\t@# %s #@\n", message.getContent());
        unbindRequest(message);
        broadcastMessage(message);
    }

    private void unbindRequest(ChatMessage message) {
        if(message.getContent().equalsIgnoreCase("q")) {
            registeredClients.forEach(client -> {
                unbindAndRemoveClient(message, client);
            });
        }
    }

    private void unbindAndRemoveClient(ChatMessage message, ChatClient client) {
        try {
            if(client.getClientNickname().equalsIgnoreCase(message.getSenderNickname())) {
                unbindClient(client);
                registeredClients.remove(client);
                broadcastMessage(new ChatMessage(name, client.getClientNickname() + " logout."));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void unbindClient(ChatClient client) {
        try {
            registry.unbind(client.getClientNickname());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
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