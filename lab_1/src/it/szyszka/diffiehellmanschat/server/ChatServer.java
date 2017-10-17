package it.szyszka.diffiehellmanschat.server;

import it.szyszka.diffiehellmanschat.client.ChatClient;
import it.szyszka.diffiehellmanschat.messages.ChatMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by rafal on 17.10.17.
 */
public interface ChatServer extends Remote {

    Boolean registerNewClient(ChatClient client) throws RemoteException;
    void receiveMessage(ChatMessage message) throws RemoteException;
    void sendMessageTo(ChatClient client) throws RemoteException;
    void broadcastMessage(ChatMessage message) throws RemoteException;

}
