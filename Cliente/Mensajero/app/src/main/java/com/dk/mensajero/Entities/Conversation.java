package com.dk.mensajero.Entities;

import android.content.Context;

import com.dk.mensajero.DB.DbHelper;


public class Conversation {

    private long id = 0;
    private String conversationId = "";
    private String contactId = "";
    private String contactName = "";
    //private String contactPhoto = "";

    public Conversation(){
    }

    public Conversation(long id, String conversationId, String contactId, String contactName){
        this.id = id;
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

    public void setContactName(String contactName){
        this.contactName = contactName;
    }



    //Methods

    public void save(Context context) {
        DbHelper helper = new DbHelper(context);
        helper.insertConversation(this);
    }
}
