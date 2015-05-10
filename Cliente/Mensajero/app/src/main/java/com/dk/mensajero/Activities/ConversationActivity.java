package com.dk.mensajero.Activities;

import android.content.Context;
import android.database.DataSetObserver;
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
import com.dk.mensajero.R;

public class ConversationActivity extends ActionBarActivity {

    private ListView conversationList;
    private EditText chatText;
    private Button send_button;
    private boolean position = false;
    private ChatAdapter chatAdapter;
    private Context conversationCtx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        this.conversationList = (ListView) findViewById(R.id.chat_list_view);
        this.chatText = (EditText) findViewById(R.id.chatTxt);
        this.send_button = (Button) findViewById(R.id.send_button);
        this.chatAdapter = new ChatAdapter(conversationCtx, R.layout.single_message_layout);
        this.conversationList.setAdapter(chatAdapter);
        this.conversationList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        this.registerDataSetObserver();
        this.setOnClickListener();

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
                    //TODO: Enviar el mensaje al servidor. Con el id del mensaje que se recibe, guardar en la db
                    Message message = new Message();
                    message.setBody(chatText.getText().toString());
                    chatAdapter.add(new ConversationDataProvider(position, message));
                    position = !position;
                    chatText.setText("");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversation, menu);
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
}
