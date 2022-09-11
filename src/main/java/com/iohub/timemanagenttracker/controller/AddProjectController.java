package com.iohub.timemanagenttracker.controller;

import com.iohub.timemanagenttracker.apiConnection.ApiConnnection;
import com.iohub.timemanagenttracker.model.Profile;
import io.github.palexdev.materialfx.beans.Alignment;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class AddProjectController implements Initializable {


    @FXML
    MFXFilterComboBox member;

    @FXML
    TextField projectName, estimatedTimeline;

    @FXML
    MFXDatePicker dueDate;

    @FXML
    VBox membersContainer;

    @FXML
    Label dropValue;

    ApiConnnection apiConnnection = new ApiConnnection();

    HashMap<String, String> model = new HashMap<>();

    ArrayList<Profile> membersList;

    ArrayList<String> membersID;

    HBox hBox = new HBox();

    VBox vBox = new VBox();

    Label name = new Label();

    Label role = new Label();


    Image image;

    ImageView imageView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        membersID = new ArrayList<>();

        member.setPromptText("Add Members");

       dueDate.setPopupOffsetX(60.0);

        populateList();

        member.setOnAction(e -> {

            for (Profile profile : membersList) {

                if (profile.getFullName().equals(member.getValue())) {

                    if(!membersID.contains(profile.getUserId())){

                        membersID.add(profile.getUserId());

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                if(!membersContainer.isVisible()){

                                    membersContainer.setVisible(true);

                                }

                                VBox.setMargin(membersContainer, new Insets(0, 0, 0, 0));

                                membersContainer.getChildren().add(getSelectedMember(profile.getFullName(),profile.getRole(), profile.getProfileImg(), profile.getUserId()));

                            }
                        });

                    }

                }
            }

        });

    }

    @FXML
    private void goTo(MouseEvent event) {
        DashboardController.getInstance().loadPage("project");
    }

    @FXML
    private void addProject(MouseEvent event) {

        String memberName = "";

        String memberID = "";

        String name = projectName.getText().trim();

        String timeLine = estimatedTimeline.getText().trim();

        if (member.getValue() != null) {

            memberName = member.getValue().toString();

            memberID = model.get(memberName);

        }

        LocalDate localDate = dueDate.getValue();

        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));

        String dueDateV = instant.toString();

        System.out.println("value ======> " + name + " " + timeLine + " " + memberName + " =>" + membersID + " " + instant);

        JsonObject project = new JsonObject().put("projectName", name).put("estimatedTimeline", timeLine).put("members", membersID).put("dueDate", instant);

        System.out.println("MembersID =======>" + membersID.size());

        System.out.println("MembersID =======>" + membersID.toString());





        Promise<JsonObject> jsonObject = apiConnnection.postToServer("/api/v1/projects",project);

        jsonObject.future().onSuccess(r -> {

            if(r.getString("status").equals("success")){
                projectName.setText("");
                estimatedTimeline.setText("");
                dueDate.setText("");
                member.clear();
                membersID.clear();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        VBox.setMargin(membersContainer, new Insets(-20, 0, 0, 0));
                        membersContainer.getChildren().clear();
                        membersContainer.setVisible(false);
                    }
                });


            }

        });

    }

    public void populateList() {

        membersList = new ArrayList<>();

        Promise<JsonObject> jsonObject = apiConnnection.getFromServer("/api/v1/users");


        jsonObject.future().onSuccess(r -> {

            if (r.getString("status").equals("success")) {

                //System.out.println(r.getJsonObject("data").getJsonArray("data"));

                JsonArray jsonArray = r.getJsonObject("data").getJsonArray("data");

                for (int i = 0; i < jsonArray.size(); i++) {

                    System.out.println(jsonArray.getJsonObject(i).getString("name"));

                    membersList.add(
                            new Profile(
                                    jsonArray.getJsonObject(i).getString("name"),
                                    jsonArray.getJsonObject(i).getString("photo"),
                                    jsonArray.getJsonObject(i).getString("role"),
                                    jsonArray.getJsonObject(i).getString("email"),
                                    jsonArray.getJsonObject(i).getString("_id"),
                                    ""
                            ));

                    model.put(jsonArray.getJsonObject(i).getString("name"), jsonArray.getJsonObject(i).getString("_id"));

                    System.out.println(model.keySet());

                    ObservableList<String> listItem = FXCollections.observableArrayList(model.keySet());

                    member.setItems(listItem);

                }


            }
        });


    }


    private HBox getSelectedMember(String memberName, String memberRole, String memberImg, String memberId) {

        hBox = new HBox();
        vBox = new VBox();

         name = new Label();
         role =new Label();

        hBox.setSpacing(20.0);
        vBox.setSpacing(5.0);
        vBox.setAlignment(Pos.TOP_RIGHT);

        vBox.setPrefWidth(110.0);

        name.setText(memberName);

        name.getStyleClass().add("nameLbl");
        role.getStyleClass().add("roleLbl");

        role.setText(memberRole);

        image = new Image(memberImg);

        imageView = new ImageView(image);

        imageView.setFitHeight(70.0);
        imageView.setFitWidth(70.0);


        vBox.getChildren().add(name);
        vBox.getChildren().add(role);
        hBox.getChildren().add(vBox);

        hBox.getChildren().add(imageView);

        hBox.setId(memberId);

        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Am Clicked !!!!!");
                Node picked = event.getPickResult().getIntersectedNode();

                System.out.println("What you clicked ======> "+ picked);

                if(picked instanceof HBox){
                    membersID.remove(picked.getId());
                    membersContainer.getChildren().remove(picked);
                }
            }
        });

        return hBox;


    }
}