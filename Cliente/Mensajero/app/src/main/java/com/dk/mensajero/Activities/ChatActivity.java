package com.dk.mensajero.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.dk.mensajero.Adapters.ChatAdapter;
import com.dk.mensajero.Conversations.ConversationDataProvider;
import com.dk.mensajero.DB.DbHelper;
import com.dk.mensajero.Entities.Conversation;
import com.dk.mensajero.Entities.Message;
import com.dk.mensajero.Entities.User;
import com.dk.mensajero.Interfaces.GetIdCallback;
import com.dk.mensajero.Interfaces.GetMessageCallback;
import com.dk.mensajero.Interfaces.GetUserCallback;
import com.dk.mensajero.R;
import com.dk.mensajero.Service.Service;
import com.dk.mensajero.Utilities.Utilities;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends ActionBarActivity {

    private ListView conversationList;
    private EditText chatText;
    private Button send_button;
    private boolean position;
    private ChatAdapter chatAdapter;
    private Context conversationCtx = this;
    private String receiverUserPhone = "";
    private String transmitterUserPhone = "";
    private String conversationId = "";
    private User transmitterUser;
    private User receiverUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.getReceiverUserPhoneFromIntent();
        this.conversationList = (ListView) findViewById(R.id.chat_list_view);
        this.chatText = (EditText) findViewById(R.id.chatTxt);
        this.send_button = (Button) findViewById(R.id.send_button);
        this.chatAdapter = new ChatAdapter(conversationCtx, R.layout.single_message_layout);
        this.conversationList.setAdapter(chatAdapter);
        this.conversationList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        this.getTransmitterUserPhone();
        this.getConversationId();
        this.registerDataSetObserver();
        this.setOnClickListener();
        this.getSaveMessageList();
    }

    private void customizeActionBar() {

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.activity_chat_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text_action_bar);
        mTitleTextView.setText(receiverUser.getName());

        if (!receiverUser.getProfilePicture().equals("default")) {
            ImageView profilePicture = (ImageView) mCustomView.findViewById(R.id.profile_picture_action_bar);
            Bitmap bitmapPicture = Utilities.stringToBitmap(receiverUser.getProfilePicture());
            profilePicture.setImageDrawable(Utilities.createRoundImage(bitmapPicture));
        }

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

    }

    private void getTransmitterUserPhone() {
        this.transmitterUser = User.getUser(this);
        this.transmitterUserPhone = transmitterUser.getPhone();
    }

    private void getConversationId() {
        Service serviceRequest = new Service(this);
        serviceRequest.fetchNewConversationIdAsyncTask(transmitterUserPhone, receiverUserPhone, new GetIdCallback() {
            @Override
            public void done(String returnedId) {
                conversationId = returnedId;
                lookForNewMessage();
            }
        });

    }

    private void getSaveMessageList() {

        DbHelper helper = new DbHelper(this);
        ArrayList<Message> savedMessagesList;
        savedMessagesList = helper.getMessagesByConversationId(this.conversationId);

        /*this.chatAdapter = new ChatAdapter(conversationCtx, R.layout.single_message_layout);
        this.registerDataSetObserver();
        this.conversationList.setAdapter(chatAdapter);
        this.conversationList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);*/

        //Muestro los mensajes en la pantalla
        for (Message m : savedMessagesList) {
            position = !(m.getTransmitterId().equals(transmitterUser.getUserId()));
            this.chatAdapter.add(new ConversationDataProvider(position, m));
        }
    }

    private void getReceiverUserPhoneFromIntent() {

        Intent intentExtras = getIntent();
        Bundle phoneBundle;
        if (intentExtras.hasExtra("contactPhone")){
            phoneBundle = intentExtras.getExtras();
            this.receiverUserPhone = phoneBundle.getString("contactPhone");
        }

        this.fetchReceiverUserFromServer();

    }


    private void fetchReceiverUserFromServer(){

        User rUser = new User(this.receiverUserPhone, "0000");

        Service serviceRequest = new Service(this);
        serviceRequest.fetchUserDataInBackground(rUser, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                setReceiverUser(returnedUser);
            }
        });

    }


    private void setReceiverUser(User returnedUser) {
        this.receiverUser = returnedUser;
        this.customizeActionBar();
    }

    public void registerDataSetObserver(){
        this.chatAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                conversationList.setSelection(chatAdapter.getCount() - 1);
            }
        });

    }

    public void setOnClickListener(){
        this.send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendMessage = getMessageToSend(chatText.getText().toString());
                if (!sendMessage.equals("")) {
                    String date = getDate();
                    Message message = new Message(transmitterUserPhone, receiverUserPhone, sendMessage, date);
                    sendMessageToServer(message);
                }
            }
        });
    }


    private void sendMessageToServer(Message message){
        Service serviceRequest = new Service(this);
        serviceRequest.sendNewMessageInBackground(message, new GetMessageCallback() {
            @Override
            public void done(ArrayList<Message> messageList) {
                if (messageList != null) {
                    for (Message msg : messageList) {
                        position = false;
                        //TODO: comento esto porque sino se duplican los mensajes. Se insertaba aca y despues en el get de mensajes
                       //msg.setConversationId(conversationId);
                       //saveMessage(msg);
                    }
                }
            }
        });

    }

    private String getMessageToSend(String textMessage) {

        String formatMessage, returnedMessage;

        if (textMessage.startsWith("\n") || textMessage.endsWith("\n")){
            formatMessage = (textMessage.replaceAll("^\n+","")).replaceAll("\n+$","");
        } else {
            formatMessage = textMessage;
        }

        if (formatMessage.startsWith(" ")) {
            returnedMessage = formatMessage.trim();
        } else {
            returnedMessage = formatMessage;
        }

        if (!(returnedMessage.isEmpty())) {
           Message message = new Message();
           message.setBody(returnedMessage);
           chatAdapter.add(new ConversationDataProvider(false, message));
           chatText.setText("");
        }

        return returnedMessage;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(c.getTime());
    }

    private void lookForNewMessage() {

        this.getSaveMessageList();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                String lastMsgId = Conversation.getLastMessageIdByConversationId(ChatActivity.this, conversationId);
                Message msg = new Message(conversationId, lastMsgId);
                getMessageFromService(msg);
                handler.postDelayed(this, 1000 );
            }
        }, 1000);
    }

    private void getMessageFromService(Message msg){

        Service serviceRequest = new Service(this);
        serviceRequest.fetchNewMessageInBackground(msg, new GetMessageCallback() {
            @Override
            public void done(ArrayList<Message> list) {
                if (list != null) {
                    DbHelper helper = new DbHelper(ChatActivity.this);
                    ArrayList<Message> currentMessages = helper.getMessagesByConversationId(ChatActivity.this.conversationId);

                    for (Message m : list) {
                        /*position = !(m.getTransmitterId().equals(transmitterUser.getUserId()));
                        chatAdapter.add(new ConversationDataProvider(position, m));*/

                        //HACK: para evitar mensajes duplicados en la db
                        boolean found = false;
                        for (Message currentMessage : currentMessages) {
                            if(currentMessage.getMessageId().equals(m.getMessageId())){
                                found = true;
                                break;
                            }
                        }

                        if(!found) {
                            saveMessage(m);
                        }
                    }
                }
                getSaveMessageList();
            }
        });


    }

    public void saveMessage(Message msg){
        msg.save(this);
    }

}
