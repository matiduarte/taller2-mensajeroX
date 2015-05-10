package com.dk.mensajero.Conversations;

import com.dk.mensajero.Entities.Message;

/**
 * Created by matias on 05/05/15.
 */
public class ConversationDataProvider {
    public boolean position;
    public String message;

    public ConversationDataProvider(boolean position, Message message) {
        this.position = position;
        this.message = message.getBody();
    }
}
