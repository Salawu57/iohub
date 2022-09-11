package com.iohub.timemanagenttracker.model;

import io.vertx.core.json.JsonObject;

public class Profile {

    private String fullName;
    private String profileImg;
    private String role;
    private String  email;
    private String userId;

    private String token;

    public Profile(String fullName, String profileImg,  String role, String email, String userId, String token) {
        this.fullName = fullName;
        this.profileImg = profileImg;
        this.role = role;
        this.email = email;
        this.userId = userId;
        this.token=token;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProfileImg() {
        return profileImg;
    }


    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }
//
//    "user": {
//        "photo": "default.jpg",
//                "role": "user",
//                "_id": "630b996616601e0a2d3c7cc7",
//                "name": "Test User2",
//                "email": "user2@gmail.com",
//                "__v": 0
//    }

    public static Profile saveProfile(JsonObject object){
      return new Profile(object.getString("name"), object.getString("photo"), object.getString("role"), object.getString("email"),object.getString("_id"),object.getString("token"));
    }

    @Override
    public String toString() {
        return "Profile{" +
                "fullName='" + fullName + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
