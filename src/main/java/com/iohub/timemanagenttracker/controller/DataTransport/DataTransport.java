package com.iohub.timemanagenttracker.controller.DataTransport;

import com.iohub.timemanagenttracker.model.Profile;

public class DataTransport {

    private Profile profile;
    private final static DataTransport INSTANCE = new DataTransport();

    private DataTransport() {}

    public static DataTransport getInstance() {
        return INSTANCE;
    }
    public void setUser(Profile profile) {
        this.profile = profile;
    }

    public Profile getUser() {
        return this.profile;
    }

}
