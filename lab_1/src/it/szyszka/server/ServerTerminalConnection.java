package it.szyszka.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTerminalConnection {

    public static final String MSG_TAG = "Server";

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader input;
    private DataOutputStream output;

    public ServerTerminalConnection(ServerSocket socket) throws IOException {
        serverSocket = socket;
    }

    public String readInput() throws IOException {
        acceptSocket();
        initReader();
        String readInput = input.readLine();
        System.out.println(MSG_TAG + " " + readInput);
        return readInput;
    }

    public void sendMessage(String message) throws IOException {
        System.out.println("Sending message to clients: " + message.trim().toUpperCase());
        acceptSocket();
        initOutput();
        output.writeBytes(message.trim().toUpperCase() + "\n");
    }

    private void initOutput() throws IOException {
        output = new DataOutputStream(socket.getOutputStream());
    }

    private void initReader() throws IOException {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void acceptSocket() throws IOException {
        socket = serverSocket.accept();
    }
}
