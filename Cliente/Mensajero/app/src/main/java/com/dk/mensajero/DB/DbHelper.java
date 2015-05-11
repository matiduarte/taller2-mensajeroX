package com.dk.mensajero.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dk.mensajero.Entities.Conversation;
import com.dk.mensajero.Entities.Message;
import com.dk.mensajero.Entities.User;

import java.util.ArrayList;

/**
 * Created by quimey on 17/01/15.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String FLOAT_TYPE = " FLOAT";
    private static final String LONG_TYPE = " LONG";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DbHelperContract.UserEntry.TABLE_NAME + " (" +
                    DbHelperContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    DbHelperContract.UserEntry.USER_ID + TEXT_TYPE + COMMA_SEP +
                    DbHelperContract.UserEntry.PHONE + TEXT_TYPE + COMMA_SEP +
                    DbHelperContract.UserEntry.PROFILE_PICTURE+ TEXT_TYPE + COMMA_SEP +
                    DbHelperContract.UserEntry.NAME + TEXT_TYPE +
            " );"
            +
            "CREATE TABLE " + DbHelperContract.ConversationEntry.TABLE_NAME + " (" +
            DbHelperContract.ConversationEntry._ID + " INTEGER PRIMARY KEY," +
            DbHelperContract.ConversationEntry.CONVERSATION_ID + TEXT_TYPE + COMMA_SEP +
            DbHelperContract.ConversationEntry.CONTACT_ID + TEXT_TYPE +
            " );"

            +
            "CREATE TABLE " + DbHelperContract.MessageEntry.TABLE_NAME + " (" +
            DbHelperContract.MessageEntry._ID + " INTEGER PRIMARY KEY," +
            DbHelperContract.MessageEntry.CONVERSATION_ID + TEXT_TYPE + COMMA_SEP +
            DbHelperContract.MessageEntry.MESSAGE_ID + TEXT_TYPE + COMMA_SEP +
            DbHelperContract.MessageEntry.BODY + TEXT_TYPE + COMMA_SEP +
            DbHelperContract.MessageEntry.DATE + TEXT_TYPE +
            " );";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbHelperContract.UserEntry.TABLE_NAME + ";"
            +
            "DROP TABLE IF EXISTS " + DbHelperContract.ConversationEntry.TABLE_NAME + ";"
            +
            "DROP TABLE IF EXISTS " + DbHelperContract.MessageEntry.TABLE_NAME + ";";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "MensajeroX.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // onUpgrade(db, oldVersion, newVersion);
    }


    public long insertUser(User user){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbHelperContract.UserEntry.USER_ID, user.getUserId());
        values.put(DbHelperContract.UserEntry.PHONE, user.getPhone());
        values.put(DbHelperContract.UserEntry.PROFILE_PICTURE, user.getProfilePicture());
        values.put(DbHelperContract.UserEntry.NAME, user.getName());

    // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                DbHelperContract.UserEntry.TABLE_NAME,
                DbHelperContract.UserEntry._ID,
                values);

        return newRowId;
    }

    public void updateUser(User user){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbHelperContract.UserEntry.USER_ID, user.getUserId());
        values.put(DbHelperContract.UserEntry.PHONE, user.getPhone());
        values.put(DbHelperContract.UserEntry.PROFILE_PICTURE, user.getProfilePicture());
        values.put(DbHelperContract.UserEntry.NAME, user.getName());

        // Define 'where' part of query.
        String selection = DbHelperContract.UserEntry._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(user.getId()) };

        db.update(DbHelperContract.UserEntry.TABLE_NAME, values, selection, selectionArgs);

    }

    public User getUser(){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DbHelperContract.UserEntry._ID,
                DbHelperContract.UserEntry.USER_ID,
                DbHelperContract.UserEntry.PHONE,
                DbHelperContract.UserEntry.PROFILE_PICTURE,
                DbHelperContract.UserEntry.NAME,
        };

        //String selection = DbHelperContract.GameEntry.IS_WISH + " = ?";
        //String[] selectionArgs = { String.valueOf("0") };

        Cursor c = db.query(
                DbHelperContract.UserEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        ArrayList<User> users = new ArrayList<User>();

        if (c != null && c.moveToFirst()) {

            while (c.isAfterLast() == false) {
                long id = c.getLong(c.getColumnIndex(DbHelperContract.UserEntry._ID));
                String userId = c.getString(c.getColumnIndex(DbHelperContract.UserEntry.USER_ID));
                String phone = c.getString(c.getColumnIndex(DbHelperContract.UserEntry.PHONE));
                String profilePicture = c.getString(c.getColumnIndex(DbHelperContract.UserEntry.PROFILE_PICTURE));
                String name = c.getString(c.getColumnIndex(DbHelperContract.UserEntry.NAME));

                User user = new User(id, userId, phone, profilePicture, name);

                users.add(user);
                c.moveToNext();
                break;
            }
        }

        return users.get(0);
    }

    public long insertConversation(Conversation conversation) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbHelperContract.ConversationEntry.CONVERSATION_ID, conversation.getConversationId());
        values.put(DbHelperContract.ConversationEntry.CONTACT_ID, conversation.getContactId());
        values.put(DbHelperContract.ConversationEntry.CONTACT_NAME, conversation.getContactName());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                DbHelperContract.ConversationEntry.TABLE_NAME,
                DbHelperContract.ConversationEntry._ID,
                values);

        return newRowId;
    }

    public long insertMessage(Message message) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbHelperContract.MessageEntry.CONVERSATION_ID, message.getConversationId());
        values.put(DbHelperContract.MessageEntry.MESSAGE_ID, message.getMessageId());
        values.put(DbHelperContract.MessageEntry.BODY, message.getBody());
        values.put(DbHelperContract.MessageEntry.DATE, message.getDate());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                DbHelperContract.MessageEntry.TABLE_NAME,
                DbHelperContract.MessageEntry._ID,
                values);

        return newRowId;
    }
}
