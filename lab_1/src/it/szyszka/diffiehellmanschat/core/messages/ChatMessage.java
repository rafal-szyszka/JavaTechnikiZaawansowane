package it.szyszka.diffiehellmanschat.core.messages;

import java.io.Serializable;

/**
 * Created by rafal on 17.10.17.
 */
public class ChatMessage implements Serializable {

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

    public ChatMessage setContent(String content) {
        this.content = content;
        return this;
    }
}
