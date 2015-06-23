package com.dk.mensajero.Service;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.dk.mensajero.Entities.Conversation;
import com.dk.mensajero.Entities.Message;
import com.dk.mensajero.Entities.User;
import com.dk.mensajero.Interfaces.GetContactsCallback;
import com.dk.mensajero.Interfaces.GetConversationsCallback;
import com.dk.mensajero.Interfaces.GetIdCallback;
import com.dk.mensajero.Interfaces.GetMessageCallback;
import com.dk.mensajero.Interfaces.GetUserCallback;
import com.dk.mensajero.Interfaces.UpdateProfileCallback;
import com.dk.mensajero.Utilities.IpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by quimey on 10/05/15.
 */
public class Service {


    //private String BASE_URL = "http://192.168.0.20:8080/";
    //public static String BASE_URL = "http://192.168.1.102:8080/";
    private static String BASE_URL;


    private String USER_URL = "usuario/";
    private String COVERSATION_URL = "conversacion/";
    private String USER_COVERSATIONS_URL = "usuarioConversacion/";
    private String CONTACTS_URL = "contactos/";
    private String ID_URL = "id/";
    private String BROADCAST_URL = "difusion/";
    private String CHECKIN_URL = "checkIn";

    ProgressDialog progressDialog;
    private Context context;

    //KEYS
    private String KEY_ID_LAST_MESSAGE = "idUltimoMensaje";
    private String KEY_PHONE_USER = "IdUsuarioEmisor";
    private String KEY_PHONE_USER_RECEIVER = "IdUsuarioReceptor";
    private String KEY_TRANSMITTER = "TelefonoEmisor";
    private String KEY_RECEIVER = "TelefonoReceptor";
    private String KEY_MESSAGE_BODY = "Cuerpo";
    private String KEY_MESSAGE_DATE = "Fecha";
    private String KEY_USER_NAME = "Nombre";
    private String KEY_MESSAGE_ID = "Id";
    private String KEY_USER_PICTURE = "FotoDePerfil";
    private String KEY_USER_STATE = "EstadoDeConexion";
    private String KEY_USER_PHONE = "Telefono";
    private String KEY_USER_ID = "idUsuario";
    private String KEY_USER_CONVERSATIONS_PARAM = "idsConversaciones";
    private String KEY_USER_LOCATION = "Localizacion";
    private String KEY_CONTACTS_PARAM = "ContactosTelefono";

    private String KEY_PAYLOAD = "payload";
    private String KEY_SUCCESS = "success";
    private String KEY_USER_PASSWORD = "Password";
    private String KEY_TOKEN_SESION = "Token";

    private static ArrayList<AsyncTask> currentActiveServices = new ArrayList<AsyncTask>();


    public Service(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Por favor espere...");
    }

    public void cancelCurrentServices(){
        for (int i = 0; i < currentActiveServices.size(); i++) {
            currentActiveServices.get(i).cancel(true);
        }

        currentActiveServices = new ArrayList<AsyncTask>();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    static public <T> void executeAsyncTask(AsyncTask<T, ?, ?> task, T... params) {
        currentActiveServices.add(task);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }

    public void updateUserProfileInBackground(User user, UpdateProfileCallback profileCallback){
        progressDialog.show();
        executeAsyncTask(new UpdateUserProfileAsyncTask(user, profileCallback));
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
            String url = getBaseUrl(context) + USER_URL;
            RestClient client = new RestClient(url);

            client.addParam(KEY_USER_NAME, user.getName());
            client.addParam(KEY_USER_PHONE, user.getPhone());
            client.addParam(KEY_USER_PICTURE, user.getProfilePicture());
            client.addParam(KEY_USER_STATE, String.valueOf(user.isConnected()) );
            client.addParam(KEY_USER_LOCATION, user.getLocation());
            client.addParam(KEY_USER_PASSWORD, user.getPassword());

            try {
                client.execute(RestClient.RequestMethod.PUT);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            JSONObject jObject = new JSONObject();
            if (response != null) {
                try {
                    jObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

        @Override
        protected void onPreExecute(){

        }
    }

    public void storeNewUserInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        executeAsyncTask(new StoreNewUserAsyncTask(user, userCallback));

    }

    public void sendNewMessageInBackground(Message message, GetMessageCallback messageCallback){
        executeAsyncTask(new SendNewMessageAsyncTask(message, messageCallback));

    }

    public void fetchNewMessageInBackground(Message message, GetMessageCallback messageCallback){
        executeAsyncTask(new FetchNewMessageAsyncTask(message, messageCallback));
    }

    public void fetchNewConversationIdAsyncTask(String transmitterId, String receiverId, GetIdCallback idCallback){
        executeAsyncTask(new FetchNewConversationIdAsyncTask(transmitterId, receiverId, idCallback));
    }

    public void fetchUserDataInBackground(User user, GetUserCallback userCallback, boolean showProgressDialog){
        if (showProgressDialog)
            progressDialog.show();
        executeAsyncTask(new fetchUserDataAsyncTask(user, userCallback));
    }

    public void fetchConversationsDataInBackground(User user, GetConversationsCallback conversationsCallback){
        executeAsyncTask(new fetchConversationsDataAsyncTask(user, conversationsCallback));
    }

    public void fetchContactsDataInBackground(ArrayList<String> phoneNumbers, GetContactsCallback userCallback){
        //progressDialog.show();
        executeAsyncTask(new fetchContactsDataAsyncTask(phoneNumbers, userCallback));
    }

    public void sendNewBroadcastInBackground(Message message, ArrayList<String> phoneNumbers,GetMessageCallback messageCallback){
        progressDialog.show();
        executeAsyncTask(new SendNewBroadcastAsyncTask(message, phoneNumbers,messageCallback));

    }


    public Context getContext() {
        return context;
    }


    public class SendNewMessageAsyncTask extends AsyncTask<Void, Void, Message>{

        Message message;
        GetMessageCallback messageCallback;

        public SendNewMessageAsyncTask(Message message, GetMessageCallback messageCallback) {
            this.message = message;
            this.messageCallback = messageCallback;
        }

        @Override
        protected Message doInBackground(Void... params) {

            String url = getBaseUrl(context) + COVERSATION_URL;
            RestClient client = new RestClient(url);

            client.addParam(KEY_PHONE_USER, message.getUserPhoneTransmitter());
            client.addParam(KEY_PHONE_USER_RECEIVER, message.getUserPhoneReceiver());
            client.addParam(KEY_MESSAGE_BODY, message.getBody());
            client.addParam(KEY_MESSAGE_DATE, message.getDate());

            try {
                client.execute(RestClient.RequestMethod.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            Message returnedMsg = null;
            if (response != null) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    if (jObject.length() == 0) {
                        returnedMsg = null;
                    } else {
                        String idMsg = jObject.getString(KEY_PAYLOAD);
                        returnedMsg = new Message(message.getUserPhoneTransmitter(), message.getUserPhoneReceiver(), message.getBody(), message.getDate());
                        returnedMsg.setMessageId(idMsg);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("ENTRE SendNewMessageAsyncTask");
            }
            return returnedMsg;
        }

        @Override
        protected void onPostExecute(Message returnedMsg) {
            ArrayList<Message> list = new ArrayList<>();
            list.add(returnedMsg);
            messageCallback.done(list);
            super.onPostExecute(returnedMsg);
        }
    }

    public class FetchNewConversationIdAsyncTask extends AsyncTask<Void, Void, String>{

        String transmitterId;
        String receiverId;
        GetIdCallback idCallback;

        public FetchNewConversationIdAsyncTask(String transmitterId, String receiverId, GetIdCallback idCallback) {
            this.transmitterId = transmitterId;
            this.receiverId = receiverId;
            this.idCallback = idCallback;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = getBaseUrl(context) + COVERSATION_URL + ID_URL;
            RestClient client = new RestClient(url);

            client.addParam(KEY_TRANSMITTER, transmitterId);
            client.addParam(KEY_RECEIVER, receiverId);

            try {
                client.execute(RestClient.RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            String conversationId = null;
            if (response != null) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    if (jObject.length() == 0) {
                        conversationId = null;
                    } else {
                        conversationId = jObject.getString(KEY_PAYLOAD);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("ENTRE FetchNewConversationIdAsyncTask");
            }
            return conversationId;

        }

        @Override
        protected void onPostExecute(String returnedId) {
            idCallback.done(returnedId);
            super.onPostExecute(returnedId);
        }
    }


    public class FetchNewMessageAsyncTask extends AsyncTask<Void, Void, ArrayList<Message>>{

        Message message;
        GetMessageCallback messageCallback;

        public FetchNewMessageAsyncTask(Message message, GetMessageCallback messageCallback) {
            this.message = message;
            this.messageCallback = messageCallback;
        }

        @Override
        protected ArrayList<Message> doInBackground(Void... params) {

            String url = getBaseUrl(context) + COVERSATION_URL + message.getConversationId();
            RestClient client = new RestClient(url);

            client.addParam(KEY_ID_LAST_MESSAGE, message.getMessageId());

            try {
                client.execute(RestClient.RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            ArrayList<Message> messageList = null;
            if (response != null) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    if (jObject.length() == 0) {
                        messageList = null;
                    } else {
                        messageList = new ArrayList<>();
                        String payload = jObject.getString(KEY_PAYLOAD);
                        JSONObject jData = new JSONObject(payload);
                        JSONArray messagesJsonArray = jData.getJSONArray("mensajes");
                        for (int i = 0; i < messagesJsonArray.length(); i++) {

                            String msgString = (String) messagesJsonArray.get(i);
                            JSONObject msgJson = new JSONObject(msgString);

                            String body = msgJson.getString(KEY_MESSAGE_BODY);
                            String date = msgJson.getString(KEY_MESSAGE_DATE);
                            String msgId = msgJson.getString(KEY_MESSAGE_ID);
                            String transmitterId = msgJson.getString(KEY_PHONE_USER);

                            Message returnedMsg = new Message();
                            returnedMsg.setBody(body);
                            returnedMsg.setMessageId(msgId);
                            returnedMsg.setDate(date);
                            returnedMsg.setTransmitterId(transmitterId);
                            returnedMsg.setConversationId(message.getConversationId());

                            messageList.add(returnedMsg);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("ENTRE FetchNewMessageAsyncTask");
            }

            return messageList;
        }

        @Override
        protected void onPostExecute(ArrayList<Message> listMessage) {
            messageCallback.done(listMessage);
            super.onPostExecute(listMessage);
        }

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

            String url = getBaseUrl(context) + USER_URL;
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
            JSONObject jObject = null;
            if (response != null){
                try {
                    jObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("ENTRE StoreNewUserAsyncTask");
            }

            return jObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            progressDialog.dismiss();
            boolean response = jsonObject != null;
            boolean existingUser = false;
            if (response) {
                try {
                    existingUser = !jsonObject.getBoolean(KEY_SUCCESS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            userCallback.done(null, response, existingUser);
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

            String url = getBaseUrl(context) + USER_URL + user.getPhone();
            RestClient client = new RestClient(url);

            try {
                client.execute(RestClient.RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            User returnedUser = null;
            if (response != null) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    if (jObject.length() == 0) {
                        returnedUser = null;
                    } else {
                        Boolean success = jObject.getBoolean(KEY_SUCCESS);
                        if (success) {
                            String data = jObject.getString(KEY_PAYLOAD);
                            JSONObject jData = new JSONObject(data);
                            String name = jData.getString(KEY_USER_NAME);
                            String password = jData.getString(KEY_USER_PASSWORD);
                            String tokenSesion = jData.getString(KEY_TOKEN_SESION);
                            String state = jData.getString(KEY_USER_STATE);
                            String picture = jData.getString(KEY_USER_PICTURE);
                            returnedUser = new User(user.getPhone(), name, password, tokenSesion);
                            returnedUser.setProfilePicture(picture);
                            returnedUser.setConnected(Boolean.valueOf(state));
                            String userId = jData.getString(KEY_USER_ID);
                            returnedUser.setUserId(userId);
                            returnedUser.setExist(true);
                        } else {
                            returnedUser = new User();
                            returnedUser.setExist(false);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("ENTRE fetchUserDataAsyncTask");
            }

            return returnedUser;
        }


        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            boolean response = returnedUser != null ? true : false;
            boolean findUser = false;
            if (response)
                findUser = returnedUser.isExist();
            userCallback.done(returnedUser, response, findUser);
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

            String url = getBaseUrl(context) + USER_COVERSATIONS_URL + user.getPhone();
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
            ArrayList<Conversation> conversations = new ArrayList<>();
            if (response != null) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    if (jObject.length() == 0) {
                        return conversations;
                    } else {
                        String payload = jObject.getString(KEY_PAYLOAD);
                        JSONObject result = new JSONObject(payload);
                        JSONArray conversationsJson = result.getJSONArray("conversaciones");
                        for (int i = 0; i < conversationsJson.length(); i++) {
                            JSONObject convJson = (JSONObject) conversationsJson.get(i);
                            String id = convJson.getString("id");
                            String lastMessage = convJson.getString("ultimoMensaje");
                            String userName = convJson.getString("usuarioNombre");
                            String userId = convJson.getString("usuarioTelefono");
                            String userPicture = convJson.getString("usuarioFotoDePerfil");

                            Conversation conv = new Conversation();
                            conv.setConversationId(id);
                            conv.setContactName(userName);
                            conv.setContactPhone(userId);
                            conv.setLastMessage(lastMessage);
                            conv.setContactProfilePicture(userPicture);

                            conversations.add(conv);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("ENTRE fetchConversationsDataAsyncTask");
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

    public class fetchContactsDataAsyncTask extends AsyncTask<Void, Void, ArrayList<User>> {

        ArrayList<String> phoneNumbers;
        GetContactsCallback contactsCallback;

        public fetchContactsDataAsyncTask(ArrayList<String> phoneNumbers, GetContactsCallback callback) {
            this.phoneNumbers = phoneNumbers;
            this.contactsCallback = callback;
        }

        @Override
        protected ArrayList<User> doInBackground(Void... params) {

            String url = getBaseUrl(context) + CONTACTS_URL;
            RestClient client = new RestClient(url);

            JSONArray jsArray = new JSONArray(this.phoneNumbers);

            client.addParam(KEY_CONTACTS_PARAM, jsArray.toString());

            try {
                client.execute(RestClient.RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            ArrayList<User> users = new ArrayList<>();
            if (response != null) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    if (jObject.length() == 0) {
                        return users;
                    } else {
                        String payload = jObject.getString(KEY_PAYLOAD);
                        JSONObject result = new JSONObject(payload);

                        JSONArray contactsJson = result.getJSONArray("contactos");
                        for (int i = 0; i < contactsJson.length(); i++) {
                            JSONObject contactJson = (JSONObject) contactsJson.get(i);

                            String name = contactJson.getString("Nombre");
                            String phone = contactJson.getString("Telefono");
                            String profilePicture = contactJson.getString("FotoDePerfil");

                            User user = new User();
                            user.setName(name);
                            user.setPhone(phone);
                            user.setProfilePicture(profilePicture);

                            users.add(user);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            System.out.println("ENTRE fetchContactsDataAsyncTask");
            }

            return users;
        }

        @Override
        protected void onPostExecute(ArrayList<User> users) {
            progressDialog.dismiss();
            contactsCallback.done(users);
            super.onPostExecute(users);
        }

        @Override
        protected void onCancelled() {
            progressDialog.dismiss();
            super.onCancelled();
            Log.i("Debug", "onCancelled");
        }
    }

    public class SendNewBroadcastAsyncTask extends AsyncTask<Void, Void, Message>{

        Message message;
        GetMessageCallback messageCallback;
        ArrayList<String> phoneNumbers;

        public SendNewBroadcastAsyncTask(Message message, ArrayList<String> phoneNumbers,GetMessageCallback messageCallback) {
            this.message = message;
            this.phoneNumbers = phoneNumbers;
            this.messageCallback = messageCallback;
        }

        @Override
        protected Message doInBackground(Void... params) {

            String url = getBaseUrl(context) + BROADCAST_URL;
            RestClient client = new RestClient(url);

            client.addParam(KEY_PHONE_USER, message.getUserPhoneTransmitter());
            client.addParam(KEY_MESSAGE_BODY, message.getBody());
            client.addParam(KEY_MESSAGE_DATE, message.getDate());

            JSONArray jsArray = new JSONArray(this.phoneNumbers);
            client.addParam(KEY_CONTACTS_PARAM, jsArray.toString());

            try {
                client.execute(RestClient.RequestMethod.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            Message returnedMsg = null;
            if (response != null) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    if (jObject.length() == 0) {
                        returnedMsg = null;
                    } else {
                        String idMsg = jObject.getString(KEY_PAYLOAD);
                        returnedMsg = new Message(message.getUserPhoneTransmitter(), message.getUserPhoneReceiver(), message.getBody(), message.getDate());
                        returnedMsg.setMessageId(idMsg);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("ENTRE SendBroadcastMessageAsyncTask");
            }
            return returnedMsg;
        }

        @Override
        protected void onPostExecute(Message returnedMsg) {
            ArrayList<Message> list = new ArrayList<>();
            list.add(returnedMsg);
            messageCallback.done(list);
            progressDialog.dismiss();
            super.onPostExecute(returnedMsg);
        }
    }

    public void showFailConnectionServerMessage(Context context){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage("No se pudo conectar con el servidor");
        dialogBuilder.setPositiveButton("REINTENTAR",null);
        dialogBuilder.show();
    }

    public static void setIp(String ipAddress){
        BASE_URL = ipAddress;
    }

    public static String getBaseUrl(Context context){
        if(BASE_URL != null){
            return BASE_URL;
        }

        IpHandler ipHandler = IpHandler.getIpHandler(context);
        if(!ipHandler.getIpPort().equals("")){
            BASE_URL = ipHandler.getIpPort();
            return BASE_URL;
        }
        return "";
    }

}
