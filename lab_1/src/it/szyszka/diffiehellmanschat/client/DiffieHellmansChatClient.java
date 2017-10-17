package it.szyszka.diffiehellmanschat.client;

import it.szyszka.diffiehellmanschat.TerminalReader;
import it.szyszka.diffiehellmanschat.messages.ChatMessage;
import it.szyszka.diffiehellmanschat.server.ChatServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class DiffieHellmansChatClient extends UnicastRemoteObject implements ChatClient{

    private String nickname;
    private static ChatServer chatServer;

    protected DiffieHellmansChatClient(String nickname) throws RemoteException {
        this.nickname = nickname;
    }

    public static void main(String argv[]) throws Exception {
        TerminalReader reader = TerminalReader.getInstance();
        System.out.print("Enter server's port number: ");
        Integer serverPort = Integer.parseInt(
                reader.readTerminalLine()
        );
        System.out.print("Enter server's name: ");
        String serverName = reader.readTerminalLine();
        Registry registry = LocateRegistry.getRegistry(serverPort);
        chatServer = (ChatServer) registry.lookup(serverName);
        System.out.format("\nConnected to server on port: %d\nYou can chat now!\n", serverPort);
    }

    private void run() {
        while(true) {

        }
    }

    @Override
    public void receiveMessage(ChatMessage message) throws RemoteException {
        System.out.format("%s >>>:\t%s", message.getSenderNickname(), message.getContent());
    }
}