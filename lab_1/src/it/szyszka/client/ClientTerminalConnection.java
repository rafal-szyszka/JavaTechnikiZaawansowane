package it.szyszka.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientTerminalConnection {

    private Socket socket;
    private BufferedReader userInput;
    private DataOutputStream userOutput;
    private BufferedReader serverInput;

    public ClientTerminalConnection(Socket socket) throws IOException {
        this.socket = socket;
        userInput = new BufferedReader(new InputStreamReader(System.in));
        userOutput = new DataOutputStream(socket.getOutputStream());
        serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendStringToServer() throws IOException {
        String message = writeMessage();
        send(message);
    }

    public void readFromServer() throws IOException {
        System.out.println(serverInput.readLine());
    }

    private void send(String message) {
        try {
            userOutput.writeBytes(message);
            System.out.println("Message send!");
        } catch (IOException e) {
            System.err.println("Failed to send message: " + message);
        }
    }

    private String writeMessage() throws IOException {
        System.out.println("\nWrite your message (one line only): ");
        String message = userInput.readLine();
        return message;
    }

}
