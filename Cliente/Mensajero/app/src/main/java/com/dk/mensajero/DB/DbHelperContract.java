package com.dk.mensajero.DB;

import android.provider.BaseColumns;

public final class DbHelperContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DbHelperContract() {}

    /* Inner class that defines the table contents */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String USER_ID = "userId";
        public static final String PHONE = "phone";
        public static final String PROFILE_PICTURE = "profilePicture";
        public static final String NAME = "name";
        public static final String PASSWORD = "password";
    }

    public static abstract class ConversationEntry implements BaseColumns {
        public static final String TABLE_NAME = "Conversation";
        public static final String CONVERSATION_ID = "conversationId";
        public static final String CONTACT_ID = "contactId";
        public static final String CONTACT_NAME = "contactName";
    }

    public static abstract class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "Message";
        public static final String CONVERSATION_ID = "conversationId";
        public static final String MESSAGE_ID = "messageId";
        public static final String BODY = "body";
        public static final String DATE = "date";
    }
}