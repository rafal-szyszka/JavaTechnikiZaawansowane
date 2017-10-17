package it.szyszka.diffiehellmanschat.core.client;

/**
 * Created by rafal on 17.10.17.
 */
public interface ClientInterface {

    Integer readPort(String message);
    String readName(String message);
    String readNickname(String message);
    String readString(String message);

    void writeMessage(String message);

}
