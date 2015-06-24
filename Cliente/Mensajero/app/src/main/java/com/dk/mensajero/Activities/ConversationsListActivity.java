package com.dk.mensajero.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import com.dk.mensajero.Adapters.ConversationAdapter;
import com.dk.mensajero.Entities.Conversation;
import com.dk.mensajero.Entities.User;
import com.dk.mensajero.Interfaces.CheckInCallback;
import com.dk.mensajero.Interfaces.GetConversationsCallback;
import com.dk.mensajero.R;
import com.dk.mensajero.Service.GPSTracker;
import com.dk.mensajero.Service.Service;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;


public class ConversationsListActivity extends ActionBarActivity {

    Handler conversationsHandler = new Handler();
    String userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations_list);
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();
        initView();
        settingsFloatingButton();
        checkInFloatingButton();
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
        // as you specify a parent activity in AndroigdManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        conversationsHandler = new Handler();
        getConversations();
        conversationsHandler.postDelayed(new Runnable() {
            public void run() {
                getConversationsFromService();
                conversationsHandler.postDelayed(this, 1000 * 2); //cada dos segundos
            }
        }, 1000 * 2);
    }

    public void getConversations() {

        ArrayList<Conversation> conversations = Conversation.getConversationsWithMessages(this);

        final ConversationAdapter adapter = new ConversationAdapter(this,
                android.R.layout.simple_list_item_1, conversations);

        final ListView listview = (ListView) findViewById(R.id.list_conversations);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                //TODO: reemplazar la activada por la de la conversacion

                Intent myIntent = new Intent(getApplicationContext(), ChatActivity.class);
                Conversation conversation = (Conversation) listview.getItemAtPosition(position);

                myIntent.putExtra("contactPhone", conversation.getContactPhone());
                startActivity(myIntent);
            }
        });
    }


    private void getConversationsFromService() {
        Service serviceRequest = new Service(this);

        serviceRequest.fetchConversationsDataInBackground(User.getUser(this), new GetConversationsCallback() {
            @Override
            public void done(ArrayList<Conversation> conversations) {
                ArrayList<Conversation> currentConversations = Conversation.getConversations(ConversationsListActivity.this);
                //Inserto las nuevas conversaciones
                for (int i = 0; i < conversations.size(); i++) {
                    //Chequeo que la conversation no haya sido agregada
                    boolean found = false;
                    for (Conversation currentConversation : currentConversations) {
                        if (currentConversation.getConversationId().equals(conversations.get(i).getConversationId())) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        saveConversation(conversations.get(i));
                    }
                }

                //Cargo de nuevo las conversaciones
                getConversations();
            }
        });
    }

    private void saveConversation(Conversation c) {
        c.save(this);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void onPause() {
        Service service = new Service(this);
        service.cancelCurrentServices();
        conversationsHandler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    public void onResume() {
        initView();
        super.onResume();
    }

    public void settingsFloatingButton(){
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.icon_settings_fb);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConversationsListActivity.this, SettingsActivity.class));
            }
        });


    }

    public void checkInFloatingButton(){
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.icon_checkin);


        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon).setPosition(6)
                .build();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GPSTracker gps = new GPSTracker(ConversationsListActivity.this);
                Service serviceRequest = new Service(ConversationsListActivity.this);
                serviceRequest.checkInUserInBackgroud(new CheckInCallback() {
                    @Override
                    public void done(String location, boolean success) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ConversationsListActivity.this);
                        if (success) {
                            User user = User.getUser(ConversationsListActivity.this);
                            user.setLocation(location);
                            user.save(ConversationsListActivity.this);
                            dialogBuilder.setMessage("Se encuentra en: " + location);
                            dialogBuilder.setPositiveButton("ACEPTAR", null);
                            dialogBuilder.show();
                        } else {
                            dialogBuilder.setMessage("No se pudo calcular su ubicación. Error de conexión. ");
                            dialogBuilder.setPositiveButton("ACEPTAR", null);
                            dialogBuilder.show();
                        }
                    }
                },gps.getLatitude(),gps.getLongitude());

            }
        });


    }


}
