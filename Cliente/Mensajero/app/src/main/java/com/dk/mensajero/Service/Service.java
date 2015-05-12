package com.dk.mensajero.Service;

import android.text.format.Time;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by quimey on 10/05/15.
 */
public class Service {
    private String BASE_URL = "http://localhost:8080/";
    private String USER_URL = "usuario/";
    private String COVERSATION_URL = "conversacion/";

    //KEYS
    private String KEY_ID_LAST_MESSAGE = "/idUltimoMensaje/";
    private String KEY_PHONE_USER = "IdUsuarioEmisor";
    private String KEY_PHONE_USER_RECEIVER = "IdUsuarioReceptor";
    private String KEY_MESSAGE_BODY = "Cuerpo";
    private String KEY_MESSAGE_DATE = "Fecha";
    private String KEY_USER_NAME = "Nombre";
    private String KEY_USER_PICTURE = "FotoDePerfil";
    private String KEY_USER_STATE = "EstadoDeConexion";
    private String KEY_USER_PHONE = "Telefono";

    public JSONObject getConversation(String conversationId, String lastMessageId){
        String url = BASE_URL + COVERSATION_URL + conversationId + KEY_ID_LAST_MESSAGE + lastMessageId;
        RestClient client = new RestClient(url);

        try {
            client.execute(RestClient.RequestMethod.GET);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = client.getResponse();
        JSONObject jObject = new JSONObject();
        try {
            jObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jObject;
    }

    public JSONObject saveConversation(String phone, String receiverPhone, String message){
        String url = BASE_URL + COVERSATION_URL;
        RestClient client = new RestClient(url);

        Time time = new Time();
        time.setToNow();
        String date = Long.toString(time.toMillis(false));

        client.addParam(KEY_PHONE_USER, phone);
        client.addParam(KEY_PHONE_USER_RECEIVER, receiverPhone);
        client.addParam(KEY_MESSAGE_BODY, message);
        client.addParam(KEY_MESSAGE_DATE, date);

        try {
            client.execute(RestClient.RequestMethod.POST);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = client.getResponse();
        JSONObject jObject = new JSONObject();
        try {
            jObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jObject;
    }

    public JSONObject saveProfile(String phone, String name, String picturePath, String state){
        String url = BASE_URL + USER_URL;
        RestClient client = new RestClient(url);

        client.addParam(KEY_USER_NAME, name);
        client.addParam(KEY_USER_PHONE, phone);
        client.addParam(KEY_USER_PICTURE, picturePath);
        client.addParam(KEY_USER_STATE, state);

        try {
            client.execute(RestClient.RequestMethod.PUT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = client.getResponse();
        JSONObject jObject = new JSONObject();
        try {
            jObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jObject;
    }

}
