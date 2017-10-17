package it.szyszka.diffiehellmanschat.messages;

/**
 * Created by rafal on 17.10.17.
 */
public class ChatMessage {

    private String senderNickname;
    private String content;

    public ChatMessage(String senderNickname, String content) {
        this.senderNickname = senderNickname;
        this.content = content;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public String getContent() {
        return content;
    }
}
