package com.dk.mensajero.Entities;

import android.content.Context;

import com.dk.mensajero.DB.DbHelper;


public class Message {

    private long id = 0;
    private String conversationId = "";
    private String messageId = "";
    private String body = "";
    private String date = "";
    private String userPhoneTransmitter = "";
    private String userPhoneReceiver = "";
    private String transmitterId = "";

    public Message(){
    }

    public Message(String userPhoneTransmitter, String userPhoneReceiver, String body, String date){
        this.userPhoneTransmitter = userPhoneTransmitter;
        this.userPhoneReceiver = userPhoneReceiver;
        this.body = body;
        this.date = date;

    }

    public Message(String conversationId, String messageId){
        this.conversationId = conversationId;
        this.messageId = messageId;
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

    public String getUserPhoneReceiver() {
        return userPhoneReceiver;
    }

    public String getUserPhoneTransmitter() {
        return userPhoneTransmitter;
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

    public void setUserPhoneTransmitter(String userPhoneTransmitter) {
        this.userPhoneTransmitter = userPhoneTransmitter;
    }

    public void setUserPhoneReceiver(String userPhoneReceiver) {
        this.userPhoneReceiver = userPhoneReceiver;
    }

    public void setTransmitterId(String transmitterId) {
        this.transmitterId = transmitterId;
    }

    public String getTransmitterId() {

        return transmitterId;
    }
//Methods

    public void save(Context context) {
        DbHelper helper = new DbHelper(context);
        helper.insertMessage(this);
    }
}
