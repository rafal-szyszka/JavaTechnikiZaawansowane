package it.szyszka.diffiehellmanschat;

import java.util.Scanner;

/**
 * Created by rafal on 17.10.17.
 */
public class TerminalReader {

    private Scanner in;

    public static TerminalReader getInstance() {
        return new TerminalReader(new Scanner(System.in));
    }

    protected TerminalReader(Scanner in) {
        this.in = in;
    }

    public String readTerminalLine() {
        return in.nextLine();
    }

    public Integer readInt(String message) {
        System.out.print(message);
        return Integer.parseInt(readTerminalLine());
    }

    public String readString(String message) {
        System.out.print(message);
        return readTerminalLine();
    }
}
