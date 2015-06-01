package com.dk.mensajero.Interfaces;

import com.dk.mensajero.Entities.User;

import java.util.ArrayList;

/**
 * Created by matias on 13/05/15.
 */
public interface GetContactsCallback {

    public abstract void done(ArrayList<User> contacts);

}
