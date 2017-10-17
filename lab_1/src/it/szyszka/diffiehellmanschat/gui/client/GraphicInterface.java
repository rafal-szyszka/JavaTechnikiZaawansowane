package it.szyszka.diffiehellmanschat.gui.client;

import it.szyszka.diffiehellmanschat.core.client.ClientInterface;
import javafx.scene.control.*;



/**
 * Created by rafal on 17.10.17.
 */
public class GraphicInterface implements ClientInterface {

    private static final String ERROR = "Not supported in GUIMode";
    private TextField port;
    private TextField name;
    private TextField nickname;
    private TextArea output;

    public GraphicInterface(TextField port, TextField name, TextField nickname, TextArea output) {
        this.port = port;
        this.name = name;
        this.nickname = nickname;
        this.output = output;
    }

    @Override
    public Integer readPort(String message) {
        output.appendText(message+"\n");
        return Integer.valueOf(port.getText());
    }

    @Override
    public String readName(String message) {
        output.appendText(message+"\n");
        return name.getText();
    }

    @Override
    public String readNickname(String message) {
        output.appendText(message+"\n");
        return nickname.getText();
    }

    @Override
    public void writeMessage(String message) {
        output.appendText(message);
    }

    @Override
    public String readString(String message) {
        return ERROR;
    }
}
