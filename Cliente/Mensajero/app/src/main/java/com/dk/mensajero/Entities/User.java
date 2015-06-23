package com.dk.mensajero.Entities;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.dk.mensajero.DB.DbHelper;

/**
 * @author DK
 * Representa a un usuario registrado en el sistema.
 */
public class User implements Parcelable{

    private long id = 0;
    private String userId = "";
    /**
     * Numero de telefono de un usuario, no puede ser modificado.
     */
    private String phone = "";
    /**
     * Foto de perfil de un usuario, por defecto, un usuario no tiene asignada ninguna foto.
     */
    private String profilePicture = "default";
    /**
     * Nick de un usuario, puede modificarlo.
     */
    private String name = "";
    /**
     * Contrasena de un usuario.
     * Permite al usuario, junto con su telefono, loguearse en el sistema.
     */
    private String location = "desconocida";
    private String password = "";
    private String tokenSesion = "";
    private boolean connected = true;
    private int isLogged;
    private boolean exist;
    private String status;



    /**
     * Crea un usuario vacio.
     */
    public User(){
    }

    /**
     * Crea un objeto usuario parcelable.
     * @param in Objeto Parcelable
     */
    public User(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Crea un usuario con id, id de usuario, telefono, foto de perfil y nombre.
     * @param id identificador para la base de datos.
     * @param userId telefono hasheado.
     * @param phone numero de telefono.
     * @param profilePicture foto de perfil.
     * @param name nick del usuario.
     */
    public User(long id, String userId, String phone, String profilePicture, String name){
        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.name = name;
    }

    /**
     * Crea un usuario con nombre, telefono, contrasena y token de sesion.
     * @param phone numero de telefono.
     * @param password contrasena para loguearse.
     * @param name nick del usuario.
     * @param tokenSesion token para la seguridad de la sesion.
     */
    public User(String phone, String name, String password, String tokenSesion){
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.tokenSesion = tokenSesion;
    }

    /**
     * Crea un usuario con un numero de telefono y una contrasena
     * @param phone numero de telfono.
     * @param password contrasena para loguerase.
     */
    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }


    //Properties

    /**
     * Getter.
     * @return id: identificador para la base de datos
     */
    public long getId(){
        return this.id;
    }
    /**
     * Getter.
     * @return userId: telefono hasheado.
     */
    public String getUserId(){
        return this.userId;
    }

    /**
     * Getter.
     * @return tokenSesion: token de seguridad.
     */
    public String getTokenSesion() {return this.tokenSesion; }
    /**
     * Getter.
     * @return phone: numero de telefono.
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
     * @return connected: verdadero si el usuario se encuentra logueado, falso sino.
     */
    public boolean isConnected(){return this.connected; }

    /**
     * Setter.
     * @param id identificador de la base de datos.
     */
    public void setId(long id){
        this.id = id;
    }
    /**
     * Setter
     * @param userId
     */
    public void setUserId(String userId){
        this.userId = userId;
    }
    /**
     * Setter
     * @param phone
     */
    public void setPhone(String phone){
        this.phone = phone;
    }
    /**
     * Setter
     * @param profilePicture
     */
    public void setProfilePicture(String profilePicture){
        this.profilePicture = profilePicture;
    }
    /**
     * Setter
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * Setter
     * @param password
     */
    public void setPassword(String password){this.password = password; }

    /**
     * Getter
     * @return status: verdadero si esta conectado, falso sino.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter
     * @param status verdadero si esta conectado, falso sino.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Setter
     * @param connected verdadero si esta conectado, falso sino.
     */
    public void setConnected(boolean connected){this.connected = connected; }
    /**
     * Setter
     * @param exist verdadero si el usuario existe, falso sino.
     */
    public void setExist(boolean exist) {
        this.exist = exist;
    }
    /**
     * Setter
     * @return exist: verdadero si el usuario existe, falso sino.
     */
    public boolean isExist() {
        return exist;
    }

    public void setLocation(String location) { this.location = location; }

    public String getLocation() { return this.location; }
//Methods

    /**
     * Almacena un usuario en la base de datos.
     * @param context this.
     */
    public void save(Context context) {
        DbHelper helper = new DbHelper(context);
        helper.insertUser(this);
    }

    /**
     * Devuelve un usuario de la base de datos.
     * @param context this.
     * @return User.
     */
    public static User getUser(Context context){
        DbHelper helper = new DbHelper(context);
        return helper.getUser();
    }

    /**
     * Getter.
     * @return isLogged: verdadero si el usuario esta logueado, falso sino.
     */
    public int getIsLogged() {
        return isLogged;
    }
    /**
     * Setter.
     * @param isLogged isLogged: verdadero si el usuario esta logueado, falso sino.
     */
    public void setIsLogged(int isLogged) {
        this.isLogged = isLogged;
    }


    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.profilePicture);
        dest.writeString(this.phone);
        dest.writeString(this.status);
    }

    /**
     * Obtiene los atributos almacenados en el objeto parcelable.
     * @param in usuario parcelable
     */
    private void readFromParcel(Parcel in) {
        name = in.readString();
        profilePicture = in.readString();
        phone = in.readString();
        status = in.readString();
    }

    /**
     * Metodo estatico que crea un usuario parcelable
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in); }
        public User[] newArray(int size) {
            return new User[size]; }
    };
}
