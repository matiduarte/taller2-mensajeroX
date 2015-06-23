package com.dk.mensajero.Activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.dk.mensajero.R;

public class TabLayoutActivity extends TabActivity {

    private TabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        mTabHost = getTabHost();
        TabHost.TabSpec spec;

        //Contac tab
        spec = mTabHost.newTabSpec("contactos")
                .setIndicator("Contactos")
                .setContent(new Intent(this, ContactsListActivity.class));
        mTabHost.addTab(spec);

        //Chats tab
        spec = mTabHost.newTabSpec("chats")
                .setIndicator("Chats")
                .setContent(new Intent(this, ConversationsListActivity.class));
        mTabHost.addTab(spec);

        mTabHost.setCurrentTab(1);
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




}
