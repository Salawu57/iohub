package com.iohub.timemanagenttracker.controller;


import com.iohub.timemanagenttracker.MFXDemoResourcesLoader;
import com.iohub.timemanagenttracker.apiConnection.ApiConnnection;
import com.iohub.timemanagenttracker.controller.DataTransport.DataTransport;
import com.iohub.timemanagenttracker.model.Profile;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    @FXML
    BorderPane parent;
    @FXML
    Label role, profileName;

    @FXML
    AnchorPane content_pane ;


    @FXML
    ImageView profileImg;

    private MFXGenericDialog dialogContent;
    private static DashboardController instance;

    public DashboardController() {

        instance = this;
    }

    public static DashboardController getInstance(){

        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadPage("dashboard");

        DataTransport dataTransport = DataTransport.getInstance();

        System.out.println(dataTransport.getUser());

        Profile profile = dataTransport.getUser();

        profileName.setText(profile.getFullName());


        role.setText(profile.getRole());


        System.out.println(profile.getProfileImg());

        Image image = new Image(getClass().getResource("/com/iohub/timemanagenttracker/img/default.jpg").toExternalForm());

        profileImg.setImage(image);

    }



    @FXML
    private void loadDashboard(MouseEvent event){
        loadPage("dashboard");
    }

    @FXML
    private void loadActivity(MouseEvent event){
        loadPage("activity");
    }


    @FXML
    private void loadProject(MouseEvent event){

        loadPage("project");

    }


    @FXML
    private void loadTimesheet(MouseEvent event){
        loadPage("timesheets");
    }

    @FXML
    private void loadMembers(MouseEvent event){
        loadPage("members");
    }

    @FXML
    private void loadSettings(MouseEvent event){
        loadPage("settings");
    }



    public void loadPage(String pageName){

        Parent fxml = null;
        try{

             fxml = FXMLLoader.load(MFXDemoResourcesLoader.loadURL("fxml/"+pageName+".fxml"));

            //root = FXMLLoader.load(getClass().getResource("/com/iohub/timemanagenttracker/fxml/"+pageName+".fxml"));
        }catch (IOException ex){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,null, ex);
        }

        AnchorPane.setTopAnchor(fxml, 0.0);
        AnchorPane.setBottomAnchor(fxml, 0.0);
        AnchorPane.setLeftAnchor(fxml, 0.0);
        AnchorPane.setRightAnchor(fxml, 0.0);

        content_pane.getChildren().removeAll();
        content_pane.getChildren().setAll(fxml);


    }


}
