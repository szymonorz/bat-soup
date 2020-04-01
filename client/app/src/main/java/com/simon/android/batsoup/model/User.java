package com.simon.android.batsoup.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.io.Serializable;


public class User implements Serializable {

    private ObjectId id;

    private int status;
    private String login;
    private String password;
    private String email;

    private GeoJsonPoint location;

    public User(){}

    public User(ObjectId id,int status, String login, String password, String email, GeoJsonPoint location) {
        this.id = id;
        this.status = status;
        this.login = login;
        this.password = password;
        this.email = email;
        this.location = location;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }
}
