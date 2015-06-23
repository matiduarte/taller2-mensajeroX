package com.dk.mensajero.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.dk.mensajero.Entities.User;
import com.dk.mensajero.Interfaces.CheckInCallback;
import com.dk.mensajero.R;
import com.dk.mensajero.Service.Service;


public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        /*android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    public void showInformation(View view){
        Intent intent = new Intent(this, InformationActivity.class);
        startActivity(intent);
    }

    public void showProfileConfiguration(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void doCheckIn(View view){
        Service serviceRequest = new Service(this);
        serviceRequest.checkInUserInBackgroud(new CheckInCallback() {
            @Override
            public void done(String location, boolean success) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                if(success){
                    User user = User.getUser(SettingsActivity.this);
                    user.setLocation(location);
                    user.save(SettingsActivity.this);
                    dialogBuilder.setMessage("Se encuentra en: "+location);
                    dialogBuilder.setPositiveButton("ACEPTAR",null);
                    dialogBuilder.show();
                }else {
                    dialogBuilder.setMessage("No se pudo calcular su ubicación. ");
                    dialogBuilder.setPositiveButton("ACEPTAR", null);
                    dialogBuilder.show();
                }
            }
        });
    }

    public void logout(View view){
        User user = User.getUser(this);
        user.setIsLogged(0);
        user.save(this);
        Intent intent  = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}
