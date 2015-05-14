package com.dk.mensajero.Entities;

import android.content.Context;

import com.dk.mensajero.DB.DbHelper;

import java.util.ArrayList;


public class Conversation {

    private long id = 0;
    private String conversationId = "";
    private String contactId = "";
    private String contactName = "";
    private ArrayList<Message> messages = new ArrayList<Message>();
    private String lastMessage = "";

    public Conversation(){
    }

    public Conversation(long id, String conversationId, String contactId, String contactName){
        this.id = id;
        this.conversationId = conversationId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    //Properties

    public long getId(){
        return this.id;
    }

    public String getConversationId(){
        return this.conversationId;
    }

    public String getContactId(){
        return this.contactId;
    }

    public String getContactName(){
        return this.contactName;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setConversationId(String conversationId){
        this.conversationId = conversationId;
    }
    public void setContactId(String contactId){
        this.contactId = contactId;
    }

    public void setContactName(String contactName){
        this.contactName = contactName;
    }

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
            Message lastMessage = messages.get(messages.size() - 1);

            c.setMessages(messages);
            c.setLastMessage(lastMessage.getBody());
            conversationsResult.add(c);
        }

        return conversationsResult;
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
