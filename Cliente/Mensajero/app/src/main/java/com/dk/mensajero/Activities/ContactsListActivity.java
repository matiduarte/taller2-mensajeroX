package com.dk.mensajero.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dk.mensajero.Adapters.ContactAdapter;
import com.dk.mensajero.Entities.User;
import com.dk.mensajero.Interfaces.GetContactsCallback;
import com.dk.mensajero.R;
import com.dk.mensajero.Service.Service;

import java.util.ArrayList;


public class ContactsListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();
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
        // as you specify a parent activity in AndroigdManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        getContactsFromService();
    }

    public void showContacts(ArrayList<User> contacts){
        final ContactAdapter adapter = new ContactAdapter(this,
                android.R.layout.simple_list_item_1, contacts);

        final ListView listview = (ListView) findViewById(R.id.list_contacts);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent myIntent = new Intent(getApplicationContext(), ChatActivity.class);
                User user = (User) listview.getItemAtPosition(position);

                myIntent.putExtra("contactPhone", user.getPhone());
                startActivity(myIntent);
            }
        });
    }

    public ArrayList<String> getContactsNumbers(){
        ArrayList<String> numbers = new ArrayList<String>();
        Cursor cursor = null;
        try {
            cursor = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            int nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int phoneNumberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            cursor.moveToFirst();
            do {
                String name = cursor.getString(nameIdx);
                String phoneNumber = cursor.getString(phoneNumberIdx);
                numbers.add(phoneNumber);
            } while (cursor.moveToNext());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return numbers;
    }

    private void getContactsFromService(){
        Service serviceRequest = new Service(this);

        ArrayList<String> numbersInDevice = this.getContactsNumbers();

        serviceRequest.fetchContactsDataInBackground(numbersInDevice, new GetContactsCallback()
        {

            @Override
            public void done(ArrayList<User> contacts) {
                showContacts(contacts);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
