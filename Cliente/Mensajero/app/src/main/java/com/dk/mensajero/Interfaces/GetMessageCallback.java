package com.dk.mensajero.Interfaces;

import com.dk.mensajero.Entities.Message;

/**
 * Created by matias on 26/05/15.
 */
public interface GetMessageCallback {

    public abstract void done(Message returnedMessage);
}
