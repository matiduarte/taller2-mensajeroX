package com.dk.mensajero.Entities;

import android.content.Context;

import com.dk.mensajero.DB.DbHelper;

/**
 * @author DK
 * Represents a registered User in the system.
 */
public class User {

    private long id = 0;
    private String userId = "";
    /**
     * User's phone number, it can't be modified
     */
    private String phone = "";
    /**
     * User's profile picture, it's set null by default
     */
    private String profilePicture = "default";
    /**
     * User's nickname, it can be modified
     */
    private String name = "";
    /**
     * User's password, it can be modified
     * Let the user to log in the system
     */
    private String password = "";
    private String tokenSesion = "";
    private boolean state = true;
    private int isLogged;
    private boolean exist;

    /**
     * Creates an empty user.
     */
    public User(){
    }

    /**
     * Creates an User with id, userId, phone, profilePicture and a name.
     * @param id: id for the DB.
     * @param userId: hash phone number.
     * @param phone: phone number.
     * @param profilePicture: profile picture.
     * @param name: nickname.
     */
    public User(long id, String userId, String phone, String profilePicture, String name){
        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.name = name;
    }

    /**
     * Creates an User with phone, profilePicture and a name.
     * @param phone: phone number.
     * @param name: nickname.
     * @param password: pass to log in.
     * @param tokenSesion: security field.
     */
    public User(String phone, String name, String password, String tokenSesion){
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.tokenSesion = tokenSesion;
    }

    /**
     * Creates an User with a phone number and a password.
     * @param phone: phone number.
     * @param password: pass to log in.
     */
    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    //Properties

    /**
     * Getter.
     * @return id: id for the DB.
     */
    public long getId(){
        return this.id;
    }
    /**
     * Getter.
     * @return userId: hash phone number.
     */
    public String getUserId(){
        return this.userId;
    }

    /**
     * Getter.
     * @return tokenSesion: security field.
     */
    public String getTokenSesion() {return this.tokenSesion; }
    /**
     * Getter.
     * @return phone: phone number.
     */
    public String getPhone(){
        return this.phone;
    }
    /**
     * Getter.
     * @return profilePicture.
     */
    public String getProfilePicture(){
        return this.profilePicture;
    }
    /**
     * Getter.
     * @return name.
     */
    public String getName(){
        return this.name;
    }
    /**
     * Getter.
     * @return password.
     */
    public String getPassword(){return this.password; }
    /**
     * Getter.
     * @return state: true if the user is logged in, otherwise false.
     */
    public boolean getState(){return this.state; }

    /**
     * Setter.
     * @param id: DB id.
     */
    public void setId(long id){
        this.id = id;
    }
    /**
     * Setter
     * @param userId: new user id.
     */
    public void setUserId(String userId){
        this.userId = userId;
    }
    /**
     * Setter
     * @param phone: user's phone number.
     */
    public void setPhone(String phone){
        this.phone = phone;
    }
    /**
     * Setter
     * @param profilePicture: user's profile picture.
     */
    public void setProfilePicture(String profilePicture){
        this.profilePicture = profilePicture;
    }
    /**
     * Setter
     * @param name: user's new nickname.
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * Setter
     * @param password: user's new password.
     */
    public void setPassword(String password){this.password = password; }
    /**
     * Setter
     * @param state: user's new state, true or false.
     */
    public void setState(boolean state){this.state = state; }
    /**
     * Setter
     * @param exist: true if the user exists, otherwise false.
     */
    public void setExist(boolean exist) {
        this.exist = exist;
    }
    /**
     * Setter
     * @return exist: true if the user exists, otherwise false.
     */
    public boolean isExist() {
        return exist;
    }
//Methods

    /**
     * Save User's data on the Data Base.
     * @param context: this.
     */
    public void save(Context context) {
        DbHelper helper = new DbHelper(context);
        helper.insertUser(this);
    }

    /**
     * Get User's data from the Data Base.
     * @param context: this.
     * @return User.
     */
    public static User getUser(Context context){
        DbHelper helper = new DbHelper(context);
        return helper.getUser();
    }

    /**
     * Getter.
     * @return isLogged: true if user is logged, otherwise false.
     */
    public int getIsLogged() {
        return isLogged;
    }
    /**
     * Setter.
     * @param isLogged: true if user is logged, otherwise false.
     */
    public void setIsLogged(int isLogged) {
        this.isLogged = isLogged;
    }
}
