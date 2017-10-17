package it.szyszka.diffiehellmanschat.gui.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by rafal on 17.10.17.
 */
public class ClientFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("clientfx.fxml"));
        primaryStage.setTitle("Diffie-Hellman Chat");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }
}
