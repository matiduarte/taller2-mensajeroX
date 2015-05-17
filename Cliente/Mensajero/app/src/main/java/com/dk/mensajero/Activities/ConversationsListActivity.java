package com.dk.mensajero.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dk.mensajero.Adapters.ConversationAdapter;
import com.dk.mensajero.Entities.Conversation;
import com.dk.mensajero.Entities.User;
import com.dk.mensajero.Interfaces.GetConversationsCallback;
import com.dk.mensajero.R;
import com.dk.mensajero.Service.Service;

import java.util.ArrayList;


public class ConversationsListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations_list);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void initView() {
        getConversations();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                getConversationsFromService();
                handler.postDelayed(this, 1000*2); //cada dos segundos
            }
        },  1000*2);
    }

    public void getConversations(){

        ArrayList<Conversation> conversations = Conversation.getConversationsWithMessages(this);

        final ConversationAdapter adapter = new ConversationAdapter(this,
                android.R.layout.simple_list_item_1, conversations);

        final ListView listview = (ListView) findViewById(R.id.list_conversations);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                //TODO: reemplazar la activada por la de la conversacion

                Intent myIntent = new Intent(getApplicationContext(), ConversationActivity.class);
                Conversation conversation = (Conversation) listview.getItemAtPosition(position);

                myIntent.putExtra("contactPhone", conversation.getContactPhone());
                startActivity(myIntent);
            }
        });
    }


    private void getConversationsFromService(){
        Service serviceRequest = new Service(this);

        serviceRequest.fetchConversationsDataInBackground(User.getUser(this), new GetConversationsCallback() {
            @Override
            public void done(ArrayList<Conversation> conversations) {
                //Inserto las nuevas conversaciones
                for (int i = 0; i < conversations.size(); i++) {
                    saveConversation(conversations.get(i));
                }

                //Cargo de nuevo las conversaciones
                getConversations();
            }
        });
    }

    private void saveConversation(Conversation c){
        c.save(this);
    }
}
