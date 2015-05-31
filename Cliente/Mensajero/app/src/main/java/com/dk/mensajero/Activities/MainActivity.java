package com.dk.mensajero.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dk.mensajero.Service.GPSTracker;
import com.dk.mensajero.R;


public class MainActivity extends ActionBarActivity {

    private static Button button_chat;
    private static Button button_show_location;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onChatButtonListener();
        onGSPButtonListener();
    }


    public void onGSPButtonListener(){
       button_show_location = (Button) findViewById(R.id.button_GPS);
       button_show_location.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               gps = new GPSTracker(MainActivity.this);

               if (gps.canGetLocation()){
                   double latitude = gps.getLatitude();
                   double longitude = gps.getLongitude();

                   Toast.makeText(getApplicationContext(), "Su localizacion es -\nLat: " + latitude + " Long: "
                           + longitude, Toast.LENGTH_LONG ).show();
               } else {
                   gps.showSettignsAlert();
               }
           }
       });

    }


    public void onChatButtonListener(){
        button_chat = (Button)findViewById(R.id.button_chat);
        button_chat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.dk.mensajero.ChatActivity");
                        startActivity(intent);

                    }
                }
        );

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


    public void showConversations(View view) {
        Intent intent = new Intent(this, ConversationsListActivity.class);
        startActivity(intent);
    }

    public void showContacts(View view) {
        Intent intent = new Intent(this, ContactsListActivity.class);
        startActivity(intent);
    }

    public void showSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
