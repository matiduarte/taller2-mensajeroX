package com.dk.mensajero.Interfaces;

import com.dk.mensajero.Entities.Message;

import java.util.ArrayList;

/**
 * Created by matias on 26/05/15.
 */
public interface GetMessageCallback {

    public abstract void done(ArrayList<Message> messageList);
}
