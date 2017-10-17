package it.szyszka.diffiehellmanschat.client;

import it.szyszka.diffiehellmanschat.TerminalReader;
import it.szyszka.diffiehellmanschat.messages.ChatMessage;
import it.szyszka.diffiehellmanschat.server.ChatServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DiffieHellmansChatClient extends UnicastRemoteObject implements ChatClient{

    private String nickname;
    private ChatServer chatServer;

    protected DiffieHellmansChatClient(String nickname) throws RemoteException {
        this.nickname = nickname;
    }

    public static void main(String argv[]) throws Exception {
        TerminalReader reader = TerminalReader.getInstance();
        ClientConnectionInitializer initializer = new ClientConnectionInitializer();
        initializer.initialize(reader);

        DiffieHellmansChatClient client = (DiffieHellmansChatClient) initializer.getClient();
        client.setChatServer(initializer.getServer());
        client.run(reader);
    }

    public void run(TerminalReader reader) throws RemoteException {
        String input = "";
        ChatMessage message = new ChatMessage(nickname, input);
        while (!input.equalsIgnoreCase("q")) {
            input = reader.readString("");
            chatServer.receiveMessage(message.setContent(input));
        }
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
        System.out.format("%s@$:\t%s\n", message.getSenderNickname(), message.getContent());
    }
}