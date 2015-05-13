package com.dk.mensajero.Service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.format.Time;

import com.dk.mensajero.Entities.User;
import com.dk.mensajero.Interfaces.GetUserCallback;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by quimey on 10/05/15.
 */
public class Service {
    private String BASE_URL = "http://192.168.1.102:8080/";
    private String USER_URL = "usuario/";
    private String COVERSATION_URL = "conversacion/";

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;

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


    public Service(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Por favor esepere...");
    }


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

    public void storeNewUserInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new StoreNewUserAsyncTask(user, userCallback).execute();

    }

    public void fetchUserDataInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new fetchUserDataAsyncTask(user,userCallback).execute();
    }


    public class StoreNewUserAsyncTask extends AsyncTask<Void, Void, JSONObject>{

        User user;
        GetUserCallback userCallback;

        public StoreNewUserAsyncTask(User user, GetUserCallback callback){
            this.user = user;
            this.userCallback = callback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            String url = BASE_URL + USER_URL;
            RestClient client = new RestClient(url);

            client.addParam(KEY_USER_NAME, user.getName());
            client.addParam(KEY_USER_PHONE, user.getPhone());

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

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            progressDialog.dismiss();
            System.out.println("TERMINO1");
            userCallback.done(null);
            System.out.println("TERMINO2");
            super.onPostExecute(jsonObject);
        }
    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {

        User user;
        GetUserCallback userCallback;

        public fetchUserDataAsyncTask(User user, GetUserCallback callback) {
            this.user = user;
            this.userCallback = callback;
        }

        @Override
        protected User doInBackground(Void... params) {

            String url = BASE_URL + USER_URL + user.getPhone();
            RestClient client = new RestClient(url);

            try {
                client.execute(RestClient.RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            JSONObject jObject = new JSONObject();
            User returnedUser = null;
            try {
                jObject = new JSONObject(response);

                if (jObject.length() == 0){
                    returnedUser = null;
                } else {
                    String name = jObject.getString(KEY_USER_NAME);

                    returnedUser = new User(user.getPhone(), name, user.getPassword());
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }




            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(user);
        }
    }

}
