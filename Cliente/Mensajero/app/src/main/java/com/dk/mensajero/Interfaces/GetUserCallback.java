package com.dk.mensajero.Interfaces;

import com.dk.mensajero.Entities.User;

/**
 * Created by matias on 13/05/15.
 */
public interface GetUserCallback {

    public abstract void done(User returnedUser, boolean check1, boolean check2 );

}
