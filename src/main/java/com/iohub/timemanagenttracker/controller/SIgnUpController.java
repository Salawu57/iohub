package com.iohub.timemanagenttracker.controller;

import com.iohub.timemanagenttracker.MFXDemoResourcesLoader;
import com.iohub.timemanagenttracker.MainApplication;
import com.iohub.timemanagenttracker.apiConnection.ApiConnnection;
import com.iohub.timemanagenttracker.controller.DataTransport.DataTransport;
import com.iohub.timemanagenttracker.model.Profile;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SIgnUpController {

    @FXML
    Label btn_Sign_In;


    @FXML
    BorderPane parent;

    @FXML
    VBox content_area;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    ApiConnnection apiConnnection = new ApiConnnection();


    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");


    @FXML
    private void signInHandler() throws IOException {
        System.out.println("Am clicked !!!!! load signup");
        Parent fxml = FXMLLoader.load(MFXDemoResourcesLoader.loadURL("fxml/signup.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);
    }


    @FXML
    private void loginHandler(){

        String emailAddress = email.getText().trim();

        String passwordTxt = password.getText().trim();

        if(!emailAddress.isEmpty() && !passwordTxt.isEmpty()){

            apiConnnection.loginToServer(emailAddress,passwordTxt);

            email.setText("");

            password.setText("");

        }

    }


}
