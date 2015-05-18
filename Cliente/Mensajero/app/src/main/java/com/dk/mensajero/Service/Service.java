package com.dk.mensajero.Service;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;

import com.dk.mensajero.Entities.Conversation;
import com.dk.mensajero.Entities.User;
import com.dk.mensajero.Interfaces.GetConversationsCallback;
import com.dk.mensajero.Interfaces.GetUserCallback;
import com.dk.mensajero.Interfaces.UpdateProfileCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by quimey on 10/05/15.
 */
public class Service {

    private String BASE_URL = "http://192.168.0.27:8080/";
    //private String BASE_URL = "http://192.168.1.102:8080/";

    private String USER_URL = "usuario/";
    private String COVERSATION_URL = "conversacion/";
    private String USER_COVERSATIONS_URL = "usuarioConversacion/";

    ProgressDialog progressDialog;
    private Context context;
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
    private String KEY_USER_ID = "idUsuario";
    private String KEY_USER_CONVERSATIONS_PARAM = "idsConversaciones";
    private String KEY_USER_CONVERSATIONS = "conversaciones";
    private String KEY_USER_LOCATION = "Localizacion";

    private String KEY_PAYLOAD = "payload";
    private String KEY_SUCCESS = "success";
    private String KEY_USER_PASSWORD = "Password";
    private String KEY_TOKEN_SESION = "Token";


    public Service(Context context){
        this.context = context;
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

    public void updateUserProfileInBackground(User user, UpdateProfileCallback profileCallback){
        progressDialog.show();
        new UpdateUserProfileAsyncTask(user, profileCallback).execute();
    }

    public class UpdateUserProfileAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        User user;
        UpdateProfileCallback profileCallback;

        public UpdateUserProfileAsyncTask(User user, UpdateProfileCallback profileCallback){
            this.user = user;
            this.profileCallback = profileCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = BASE_URL + USER_URL;
            RestClient client = new RestClient(url);

            client.addParam(KEY_USER_NAME, user.getName());
            client.addParam(KEY_USER_PHONE, user.getPhone());
            client.addParam(KEY_USER_PICTURE, user.getProfilePicture());
            client.addParam(KEY_USER_STATE, String.valueOf(user.getState()) );
            client.addParam(KEY_USER_LOCATION, "nada");
            //TODO: Agregar Localizacion.

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

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            progressDialog.dismiss();
            boolean result = false;
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            profileCallback.done(result);
            super.onPostExecute(jsonObject);
        }
    }

    public void storeNewUserInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new StoreNewUserAsyncTask(user, userCallback).execute();

    }

    public void fetchUserDataInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new fetchUserDataAsyncTask(user,userCallback).execute();
    }

    public void fetchConversationsDataInBackground(User user, GetConversationsCallback conversationsCallback){
        new fetchConversationsDataAsyncTask(user,conversationsCallback).execute();
    }

    public Context getContext() {
        return context;
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

            client.addParam(KEY_USER_PHONE, user.getPhone());
            client.addParam(KEY_USER_NAME, user.getName());
            client.addParam(KEY_USER_PASSWORD, user.getPassword());

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
            userCallback.done(null);
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
            User returnedUser = null;
            try {
                JSONObject jObject = new JSONObject(response);
                if (jObject.length() == 0){
                    returnedUser = null;
                } else {
                    String data = jObject.getString(KEY_PAYLOAD);
                    JSONObject jData = new JSONObject(data);
                    String name = jData.getString(KEY_USER_NAME);
                    String password = jData.getString(KEY_USER_PASSWORD);
                    String tokenSesion = jData.getString(KEY_TOKEN_SESION);
                    returnedUser = new User(user.getPhone(), name, password, tokenSesion);
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

    public class fetchConversationsDataAsyncTask extends AsyncTask<Void, Void, ArrayList<Conversation>> {

        User user;
        GetConversationsCallback conversationsCallback;

        public fetchConversationsDataAsyncTask(User user, GetConversationsCallback callback) {
            this.user = user;
            this.conversationsCallback = callback;
        }

        @Override
        protected ArrayList<Conversation> doInBackground(Void... params) {

            String url = BASE_URL + USER_COVERSATIONS_URL + user.getPhone();
            RestClient client = new RestClient(url);

            ArrayList<String> conversationIds = Conversation.getConversationIds(getContext());
            JSONArray jsArray = new JSONArray(conversationIds);

            client.addParam(KEY_USER_CONVERSATIONS_PARAM, jsArray.toString());

            try {
                client.execute(RestClient.RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            ArrayList<Conversation> conversations = new ArrayList<Conversation>();
            try {
                JSONObject jObject = new JSONObject(response);
                if (jObject.length() == 0){
                    return conversations;
                } else {
                    String payload = jObject.getString(KEY_PAYLOAD);
                    JSONObject result = new JSONObject(payload);
                    JSONArray conversationsJson = result.getJSONArray("conversaciones");
                    for (int i = 0; i < conversationsJson.length(); i++) {
                        JSONObject convJson = (JSONObject)conversationsJson.get(i);
                        String id = convJson.getString("id");
                        String lastMessage = convJson.getString("ultimoMensaje");
                        String userName = convJson.getString("usuarioNombre");
                        String userId = convJson.getString("usuarioTelefono");

                        Conversation conv = new Conversation();
                        conv.setConversationId(id);
                        conv.setContactName(userName);
                        conv.setContactPhone(userId);
                        conv.setLastMessage(lastMessage);

                        conversations.add(conv);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return conversations;
        }

        @Override
        protected void onPostExecute(ArrayList<Conversation> conversations) {
            conversationsCallback.done(conversations);
            super.onPostExecute(conversations);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("Debug", "onCancelled");
        }
    }

    public void showFailConnectionServerMessage(Context context){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage("No se pudo conectar con el servidor");
        dialogBuilder.setPositiveButton("OK",null);
        dialogBuilder.show();
    }

}
