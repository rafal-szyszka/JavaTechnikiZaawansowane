package it.szyszka.server;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer {

    public static final String HOST = "localhost";
    public static final int PORT = 9009;

    private ServerSocket serverSocket;
    private ServerTerminalConnection terminalConnection;

    public static void main(String argv[]) throws Exception {

        TCPServer server = new TCPServer(PORT);
        server.run();
    }

    public TCPServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        terminalConnection = new ServerTerminalConnection(serverSocket.accept());
    }

    private void run() {
        String input;
        while(true) {
            try {
                input = terminalConnection.readInput();
                terminalConnection.sendMessage(input.toUpperCase());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}