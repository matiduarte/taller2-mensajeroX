package com.dk.mensajero.Entities;

import android.content.Context;

import com.dk.mensajero.DB.DbHelper;

import java.util.ArrayList;


public class Conversation {

    private long id = 0;
    private String conversationId = "";
    private String contactPhone = "";
    private String contactName = "";
    private String contactProfilePicture = "default";
    private ArrayList<Message> messages = new ArrayList<Message>();
    private String lastMessage = "";

    public Conversation(){
    }

    public Conversation(long id, String conversationId, String contactPhone, String contactName){
        this.id = id;
        this.conversationId = conversationId;
        this.contactPhone = contactPhone;
        this.contactName = contactName;
    }

    //Properties

    public long getId(){
        return this.id;
    }

    public String getConversationId(){
        return this.conversationId;
    }

    public String getContactPhone(){
        return this.contactPhone;
    }

    public String getContactName(){
        return this.contactName;
    }

    public String getContactProfilePicture() {return this.contactProfilePicture; }

    public void setId(long id){
        this.id = id;
    }

    public void setConversationId(String conversationId){
        this.conversationId = conversationId;
    }

    public void setContactPhone(String contactPhone){
        this.contactPhone = contactPhone;
    }

    public void setContactName(String contactName){
        this.contactName = contactName;
    }

    public void setContactProfilePicture(String profilePicture) {this.contactProfilePicture = profilePicture;}

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }


    //Methods

    public void save(Context context) {
        DbHelper helper = new DbHelper(context);
        helper.insertConversation(this);
    }

    public static ArrayList<Conversation> getConversations(Context context){
        DbHelper helper = new DbHelper(context);
        return helper.getConversations();
    }

    public static ArrayList<Conversation> getConversationsWithMessages(Context context){
        DbHelper helper = new DbHelper(context);
        ArrayList<Conversation> conversations = helper.getConversations();

        ArrayList<Conversation> conversationsResult = new ArrayList<Conversation>();

        for (int i = 0; i < conversations.size(); i++) {
            Conversation c = conversations.get(i);
            ArrayList<Message> messages = helper.getMessagesByConversationId(c.getConversationId());
            if(messages.size() > 0) {
                Message lastMessage = messages.get(messages.size() - 1);
                c.setMessages(messages);
                c.setLastMessage(lastMessage.getBody());
            }
            conversationsResult.add(c);
        }

        return conversationsResult;
    }

    public static String getLastMessageIdByConversationId(Context context, String conversationId){
        DbHelper helper = new DbHelper(context);
       return  helper.getLastMessageIdByConversationId(conversationId);
    }


    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public static ArrayList<String> getConversationIds(Context context) {
        DbHelper helper = new DbHelper(context);
        ArrayList<Conversation> conversations = helper.getConversations();

        ArrayList<String> ids = new ArrayList<String>();

        for (int i = 0; i < conversations.size(); i++) {
            ids.add(conversations.get(i).getConversationId());
        }

        return ids;
    }
}
