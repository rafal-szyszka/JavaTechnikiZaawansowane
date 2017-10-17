package it.szyszka.diffiehellmanschat.client;

import it.szyszka.diffiehellmanschat.messages.ChatMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by rafal on 17.10.17.
 */
public interface ChatClient extends Remote {

    void receiveMessage(ChatMessage message) throws RemoteException;
    String getClientNickname() throws RemoteException;

}
