package com.iohub.timemanagenttracker.apiConnection;

import com.iohub.timemanagenttracker.MFXDemoResourcesLoader;
import com.iohub.timemanagenttracker.MainApplication;
import com.iohub.timemanagenttracker.controller.DataTransport.DataTransport;
import com.iohub.timemanagenttracker.model.Profile;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ApiConnnection {

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


    public Promise<JsonObject> getFromServer(String requestURI) {
        Promise<JsonObject> result = Promise.promise();
        this
                .getWebClient()
                .get(requestURI)
                .bearerTokenAuthentication(DataTransport.getInstance().getUser().getToken())
                .send()
                .onSuccess(event -> {
                    result.complete(event.bodyAsJsonObject());
                })
                .onFailure(event -> {
                    event.printStackTrace();
                    result.fail(event);
                });
        return result;
    }


    public Promise<JsonObject> postToServer(String requestURI,JsonObject jsonObject ) {
        Promise<JsonObject> result = Promise.promise();
        this
                .getWebClient()
                .post(requestURI)
                .bearerTokenAuthentication(DataTransport.getInstance().getUser().getToken())
                .sendJson(jsonObject)
                .onSuccess(event -> {
                    result.complete(event.bodyAsJsonObject());
                })
                .onFailure(event -> {
                    event.printStackTrace();
                    result.fail(event);
                });
        return result;
    }



    public void loginToServer(String email, String password) {
        JsonObject profile = new JsonObject().put("email", email).put("password", password);
        this
                .getWebClient()
                .post("/api/v1/users/login")
                .sendJson(profile)
                .onSuccess(event -> {
                    final Buffer response = event.bodyAsBuffer();

                    if(response.toJsonObject().getString("status").equals("fail")){

                        System.out.println(response.toJsonObject().getString("message"));

                        return;
                    }

                    JsonObject resultObject = response.toJsonObject().getJsonObject("data").getJsonObject("user");

                    resultObject.put("token",response.toJsonObject().getString("token") );

                    System.out.println(response);

                    Parent fxml = null;

                    try {

                        DataTransport dataTransport = DataTransport.getInstance();
                        dataTransport.setUser(Profile.saveProfile(resultObject));

                        System.out.println("Saved data ====> "+ dataTransport.getUser());

                        fxml = FXMLLoader.load(MFXDemoResourcesLoader.loadURL("fxml/dashboardContainer.fxml"));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MainApplication.stage.getScene().setRoot(fxml);

                    System.out.println("skhsjh");
                })
                .onFailure(Throwable::printStackTrace);
    }


    public void getAllUser(String token){

        this
                .getWebClient()
                .get("/api/v1/users")
                .send()
                .onSuccess(event -> {

                    final Buffer response = event.bodyAsBuffer();

                    if(response.toJsonObject().getString("status").equals("fail")){

                        System.out.println(response.toJsonObject().getString("message"));

                        return;
                    }

                    System.out.println(response);

                    JsonObject resultObject = response.toJsonObject().getJsonObject("data").getJsonObject("data");

                    System.out.println(response.toJsonObject().getJsonObject("data"));

                    System.out.println(resultObject);


                })

                .onFailure(Throwable::printStackTrace);

    }



    public void createProject(String projectName, String estimatedTimeline, String member, Instant dueDate){

        JsonObject profile = new JsonObject().put("projectName",projectName).put("estimatedTimeline",estimatedTimeline).put("member",member).put("dueDate",dueDate);

        this
                .getWebClient()
                .post("/api/v1/projects")
                .bearerTokenAuthentication(DataTransport.getInstance().getUser().getToken())
                .sendJson(profile)
                .onSuccess(event -> {

                    final Buffer response = event.bodyAsBuffer();

                    System.out.println(response);;

                })

                .onFailure(Throwable::printStackTrace);

    }







}
