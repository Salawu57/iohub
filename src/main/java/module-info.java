module com.iohub.timemanagenttracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires io.vertx.core;
    requires io.vertx.web.common;
    requires io.vertx.web.client;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.naming;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires com.github.kwhat.jnativehook;
    requires com.dlsc.formsfx;


    opens com.iohub.timemanagenttracker to javafx.fxml;
    exports com.iohub.timemanagenttracker;
    opens com.iohub.timemanagenttracker.controller to javafx.fxml;

}