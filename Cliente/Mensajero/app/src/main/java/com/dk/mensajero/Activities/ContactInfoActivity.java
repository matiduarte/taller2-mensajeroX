package com.dk.mensajero.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.dk.mensajero.Entities.ParcelableUser;
import com.dk.mensajero.R;
import com.dk.mensajero.Utilities.Utilities;

public class ContactInfoActivity extends ActionBarActivity {

    TextView tvName, tvState, tvLocation;
    ImageView contactPicture;
    ParcelableUser contactUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        tvName = (TextView) findViewById(R.id.tvName);
        tvState = (TextView) findViewById(R.id.tvState);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        getContactInformation();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_info, menu);
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

    public void getContactInformation(){

        Intent intent = getIntent();
        Bundle b;
        if (intent.hasExtra("contactUserInfo")){
            b = intent.getExtras();
            contactUser = b.getParcelable("contactUserInfo");
            tvName.setText(contactUser.getName());
            //TODO: FALTA ESTADO Y LOCALIZACION
            tvState.setText("Estado: Conectado");
            tvLocation.setText("Ultima localizacion: Ibiza, España");
            if (!contactUser.getProfilePicture().equals("default")) {
                ImageView profilePicture = (ImageView) findViewById(R.id.contact_picture);
                Bitmap bitmapPicture = Utilities.stringToBitmap(contactUser.getProfilePicture());
                profilePicture.setImageDrawable(Utilities.createRoundImage(bitmapPicture));
            }
        }
    }
}
