package it.szyszka.diffiehellmanschat;

import it.szyszka.diffiehellmanschat.core.client.ClientInterface;

import java.util.Scanner;

/**
 * Created by rafal on 17.10.17.
 */
public class TerminalInterface implements ClientInterface {

    private Scanner in;

    public static TerminalInterface getInstance() {
        return new TerminalInterface(new Scanner(System.in));
    }

    protected TerminalInterface(Scanner in) {
        this.in = in;
    }

    public String readTerminalLine() {
        return in.nextLine();
    }

    @Override
    public Integer readPort(String message) {
        return readInt(message);
    }

    @Override
    public String readName(String message) {
        return readString(message);
    }

    @Override
    public String readNickname(String message) {
        return readString(message);
    }

    @Override
    public String readString(String message) {
        System.out.print(message);
        return readTerminalLine();
    }

    @Override
    public void writeMessage(String message) {
        System.out.print(message);
    }

    private Integer readInt(String message) {
        System.out.print(message);
        return Integer.parseInt(readTerminalLine());
    }
}
