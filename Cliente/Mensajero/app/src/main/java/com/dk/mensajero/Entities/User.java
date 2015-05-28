package com.dk.mensajero.Entities;

import android.content.Context;

import com.dk.mensajero.DB.DbHelper;


public class User {

    private long id = 0;
    private String userId = "";
    private String phone = "";
    private String profilePicture = "";
    private String name = "";
    private String password = "";
    private String tokenSesion = "";
    private boolean state = true;
    private int isLogged;

    public User(){
    }

    public User(long id, String userId, String phone, String profilePicture, String name){
        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.name = name;
    }

    public User(String phone, String name, String password, String tokenSesion){
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.tokenSesion = tokenSesion;
    }

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    //Properties

    public long getId(){
        return this.id;
    }

    public String getUserId(){
        return this.userId;
    }

    public String getTokenSesion() {return this.tokenSesion; }

    public String getPhone(){
        return this.phone;
    }

    public String getProfilePicture(){
        return this.profilePicture;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){return this.password; }

    public boolean getState(){return this.state; }

    public void setId(long id){
        this.id = id;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setProfilePicture(String profilePicture){
        this.profilePicture = profilePicture;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){this.password = password; }

    public void setState(boolean state){this.state = state; }


    //Methods

    public void save(Context context) {
        DbHelper helper = new DbHelper(context);
        helper.insertUser(this);
    }

    public static User getUser(Context context){
        DbHelper helper = new DbHelper(context);
        return helper.getUser();
    }

    public int getIsLogged() {
        return isLogged;
    }

    public void setIsLogged(int isLogged) {
        this.isLogged = isLogged;
    }
}
