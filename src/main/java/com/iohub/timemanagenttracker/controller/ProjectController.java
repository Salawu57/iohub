package com.iohub.timemanagenttracker.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void addProject(MouseEvent event){
        DashboardController.getInstance().loadPage("addProject");
    }
}
