package com.iohub.timemanagenttracker.model;

public class MemberList {

    private String id;
    private String name;

    public MemberList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
