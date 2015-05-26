package com.dk.mensajero.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dk.mensajero.Adapters.ChatAdapter;
import com.dk.mensajero.Conversations.ConversationDataProvider;
import com.dk.mensajero.Entities.Message;
import com.dk.mensajero.Interfaces.GetMessageCallback;
import com.dk.mensajero.R;
import com.dk.mensajero.Service.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends ActionBarActivity {

    private ListView conversationList;
    private EditText chatText;
    private Button send_button;
    private boolean position = false;
    private ChatAdapter chatAdapter;
    private Context conversationCtx = this;
    private String contactPhone = "";
    private String userPhone = "";
    private List<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.conversationList = (ListView) findViewById(R.id.chat_list_view);
        this.chatText = (EditText) findViewById(R.id.chatTxt);
        this.send_button = (Button) findViewById(R.id.send_button);
        this.chatAdapter = new ChatAdapter(conversationCtx, R.layout.single_message_layout);
        this.conversationList.setAdapter(chatAdapter);
        this.conversationList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        this.lookForNewMessage();

        getContactPhoneFromIntent();

        this.registerDataSetObserver();
        this.setOnClickListener();


    }

    private void getContactPhoneFromIntent() {
        Intent intentExtras = getIntent();
        Bundle phoneBundle;
        if (intentExtras.hasExtra("contactPhone")){
            phoneBundle = intentExtras.getExtras();
            this.contactPhone = phoneBundle.getString("contactPhone");
        }
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
                    //TODO: MODIFICAR CUANDO SE PUEDA OBTENER EL TELEFONO DEL USUARIO EMISOR
                    Message message = new Message("1", contactPhone, sendMessage, date);
                    sendMessageToServer(message);
                    messageList.add(message);
                }
            }
        });
    }


    private void sendMessageToServer(Message message){
        Service serviceRequest = new Service(this);
        serviceRequest.sendNewMessageInBackground(message, new GetMessageCallback() {
            @Override
            public void done(ArrayList<Message> messageList) {
                position = false;
            }
        });
        message.save(this);
    }

    private String getMessageToSend(String textMessage) {

        String formatMessage, returnedMessage = "";

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
           chatAdapter.add(new ConversationDataProvider(position, message));
           //position = !position;
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
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                getMessageFromService();
                handler.postDelayed(this, 1000 * 2);
            }
        }, 1000 * 2);
    }

    private void getMessageFromService(){

        int lastMessageId = messageList.size();
        Service serviceRequest = new Service(this);
        serviceRequest.fetchNewMessageInBackground(messageList.get(lastMessageId), new GetMessageCallback() {
            @Override
            public void done(ArrayList<Message> list) {
                for (Message m : list) {
                    messageList.add(m);
                    saveMessage(m);
                }
                position = true;
            }
        });
    }

    public void saveMessage(Message msg){
        msg.save(this);
    }

}
