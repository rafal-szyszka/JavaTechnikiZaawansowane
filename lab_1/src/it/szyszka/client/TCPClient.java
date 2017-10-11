package it.szyszka.client;

import java.io.IOException;
import java.net.Socket;

import static it.szyszka.server.TCPServer.HOST;
import static it.szyszka.server.TCPServer.PORT;

public class TCPClient {

    private ClientTerminalConnection terminalConnection;

    public static void main(String argv[]) throws Exception {

        TCPClient client = new TCPClient();
        client.initClient();

        client.run();

    }

    private void initClient() {
        try {
            terminalConnection = new ClientTerminalConnection(new Socket(HOST, PORT));
        } catch (IOException e) {
            System.err.println("Failed to open connection.");
            e.printStackTrace();
        }
    }

    private void run() {
        while(true) {
            try {
                terminalConnection.sendStringToServer();
                terminalConnection.readFromServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}