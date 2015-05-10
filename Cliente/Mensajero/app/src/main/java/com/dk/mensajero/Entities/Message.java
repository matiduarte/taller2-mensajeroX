package com.dk.mensajero.Entities;

import android.content.Context;

import com.dk.mensajero.DB.DbHelper;


public class Message {

    private long id = 0;
    private String conversationId = "";
    private String messageId = "";
    private String body = "";
    private String date = "";

    public Message(){
    }

    public Message(long id, String conversationId, String messageId, String body, String date){
        this.id = id;
        this.conversationId = conversationId;
        this.messageId = messageId;
        this.body = body;
        this.date = date;
    }

    //Properties

    public long getId(){
        return this.id;
    }

    public String getConversationId(){
        return this.conversationId;
    }

    public String getMessageId(){
        return this.messageId;
    }

    public String getBody(){
        return this.body;
    }

    public String getDate(){
        return this.date;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setConversationId(String conversationId){
        this.conversationId = conversationId;
    }

    public void setMessageId(String messageId){
        this.messageId = messageId;
    }

    public void setBody(String body){
        this.body = body;
    }

    public void setDate(String date){
        this.date = date;
    }


    //Methods

    public void save(Context context) {
        DbHelper helper = new DbHelper(context);
        helper.insertMessage(this);
    }
}
