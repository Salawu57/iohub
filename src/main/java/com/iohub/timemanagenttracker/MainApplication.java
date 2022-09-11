package com.iohub.timemanagenttracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static Stage stage = null;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MFXDemoResourcesLoader.loadURL("fxml/signIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        this.stage = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}