package com.dk.mensajero.Utilities;

import android.content.Context;

import com.dk.mensajero.DB.DbHelper;


/**
 * Created by juanma on 01/06/15.
 */
public class IpHandler {

    private String ipPort = "";

    public IpHandler(){
    }

    public IpHandler(String ip, String port){
        this.ipPort = "http://" + ip + ":" + port + "/";
    }

    public void save(Context context){
        DbHelper helper = new DbHelper(context);
        helper.insertIpHandler(this);
    }

    public String getIpPort() {
        return this.ipPort;
    }

    public static IpHandler getIpHandler(Context context){
        DbHelper helper = new DbHelper(context);
        return helper.getIpHandler();
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }
}
