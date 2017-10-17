package it.szyszka.diffiehellmanschat.core.client;

import it.szyszka.diffiehellmanschat.core.messages.ChatMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by rafal on 17.10.17.
 */
public interface ChatClient extends Remote {

    void receiveMessage(ChatMessage message) throws RemoteException;
    String getClientNickname() throws RemoteException;

}
