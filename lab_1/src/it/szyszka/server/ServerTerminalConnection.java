package it.szyszka.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerTerminalConnection {

    public static final String MSG_TAG = "Server";

    private BufferedReader input;
    private DataOutputStream output;

    public ServerTerminalConnection(Socket socket) throws IOException {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());
    }

    public String readInput() throws IOException {
        System.out.print(MSG_TAG + " read: ");
        String readInput = input.readLine();
        System.out.println(input.readLine());
        return readInput;
    }

    public void sendMessage(String message) throws IOException {
        output.writeBytes(MSG_TAG + " send: " + message);
    }
}
