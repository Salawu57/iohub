package com.iohub.timemanagenttracker.controller;

import com.iohub.timemanagenttracker.MFXDemoResourcesLoader;
import com.iohub.timemanagenttracker.MainApplication;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SignInController {

    @FXML
    private TextField first_name, last_name, email;

    @FXML
    private PasswordField password, confirmPassword;


    private static final Vertx vertx = Vertx.vertx();
    public static Vertx getVertx() {
        return vertx;
    }
    private static WebClient webClient;


    public WebClient getWebClient() {
        WebClientOptions webClientOptions = new WebClientOptions();
        webClientOptions.setIdleTimeout(3).setLogActivity(true)
                .setSsl(false).setTrustAll(true).setVerifyHost(false)
                .setDefaultHost("127.0.0.1").setDefaultPort(8000);
        webClient = WebClient.create(this.getVertx(), webClientOptions);
        return webClient;
    }


    public void registerToServer(String name, String email, String password, String passwordConfirm) {
        JsonObject profile = new JsonObject().put("name", name).put("email", email).put("password", password).put("passwordConfirm", passwordConfirm);
        System.out.println(profile);
        this
                .getWebClient()
                .post("/api/v1/users/signup")
                .sendJson(profile)
                .onSuccess(event -> {
                    final Buffer response = event.bodyAsBuffer();

                    JsonObject resultObject = response.toJsonObject();

                    Parent fxml = null;
                    try {

                        fxml = FXMLLoader.load(MFXDemoResourcesLoader.loadURL("fxml/signIn.fxml"));

                    } catch (IOException e) {

                        throw new RuntimeException(e);

                    }

                    MainApplication.stage.getScene().setRoot(fxml);

                    System.out.println("skhsjh");
                })
                .onFailure(Throwable::printStackTrace);
    }


    @FXML
    private void signUpHandler() throws IOException {
        System.out.println("Am clicked !!!!! load sign in ");
        Parent fxml = FXMLLoader.load(MFXDemoResourcesLoader.loadURL("fxml/signIn.fxml"));
        MainApplication.stage.getScene().setRoot(fxml);
    }


    @FXML
    private void registrationHandler(){

        String emailAddress = email.getText().trim();

        String firstTxt = first_name.getText().trim();

        String lastTxt = last_name.getText().trim();

        String confirmTxt = confirmPassword.getText().trim();

        String passwordTxt = password.getText().trim();

        String fullName = firstTxt + " " + lastTxt;

        if(!emailAddress.isEmpty() && !passwordTxt.isEmpty() && !confirmTxt.isEmpty() && !lastTxt.isEmpty() && !firstTxt.isEmpty()){

            registerToServer(fullName,emailAddress,passwordTxt,confirmTxt);

            email.setText("");
            first_name.setText("");
            last_name.setText("");
            password.setText("");
            confirmPassword.setText("");

        }



    }
}
