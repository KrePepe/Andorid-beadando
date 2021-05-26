package com.example.neverforget.model;

import com.example.neverforget.helper.Identifiable;

import java.io.Serializable;

public class Password extends Identifiable implements Serializable {
    private String name;
    private String password;
    private String userName;

    public Password(String name, String password, String userName){
        this.name = name;
        this.password = password;
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }*/
}
