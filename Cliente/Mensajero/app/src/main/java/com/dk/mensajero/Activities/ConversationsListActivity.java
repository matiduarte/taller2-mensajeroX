package com.dk.mensajero.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dk.mensajero.Adapters.ConversationAdapter;
import com.dk.mensajero.Entities.Conversation;
import com.dk.mensajero.R;

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
    }

    public void getConversations(){
        ArrayList<Conversation> conversations = new ArrayList<Conversation>();
        Conversation c = new Conversation(1,"idConv","idContact","Nombre Apellido");
        conversations.add(c);
        conversations.add(c);
        conversations.add(c);
        conversations.add(c);
        //TODO: obtener conversaciones desde la base de datos

        final ConversationAdapter adapter = new ConversationAdapter(this,
                android.R.layout.simple_list_item_1, conversations);

        final ListView listview = (ListView) findViewById(R.id.list_conversations);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                //TODO: reemplazar la activada por la de la conversacion

                /*Intent myIntent = new Intent(getApplicationContext(), GameInfoActivity.class);
                Game gameSelected = (Game) listview.getItemAtPosition(position);

                myIntent.putExtra("gameSelectedId",gameSelected.getGameId());
                myIntent.putExtra("saveInfo",1);
                startActivity(myIntent);*/
            }
        });
    }
}
