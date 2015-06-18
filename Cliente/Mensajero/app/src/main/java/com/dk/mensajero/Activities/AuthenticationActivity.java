package com.dk.mensajero.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dk.mensajero.Entities.User;
import com.dk.mensajero.Interfaces.GetUserCallback;
import com.dk.mensajero.R;
import com.dk.mensajero.Service.Service;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

public class AuthenticationActivity extends ActionBarActivity implements View.OnClickListener {

    Button bLogin;
    EditText etPhoneNumber, etPassword;
    TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User userLogged = User.getUser(this);
        //Si hay un usuario logueado, redirijo a la pantalla principal
        if(userLogged.getId() > 0){
            startActivity(new Intent(this, TabLayoutActivity.class));
        }

        setContentView(R.layout.activity_authentication);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        registerLink = (TextView) findViewById(R.id.tvRegisterLink);
        bLogin.setOnClickListener(this);
        registerLink.setOnClickListener(this);


        this.ipPortFloatingButton();

    }

    public void ipPortFloatingButton(){
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_ip);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIpPopUp();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogin:
                String phone = etPhoneNumber.getText().toString();
                String password = etPassword.getText().toString();

                if (phone.equals("") || password.equals("")){
                    showEmptyFieldMessage();
                } else {
                    User user = new User(phone, password);
                    authenticate(user);
                }

                break;
            case R.id.tvRegisterLink:

                startActivity(new Intent(this, PhoneNumberRegisterActivity.class));

                break;
        }
    }

    private void authenticate(User user){

        final String insertedPassword = user.getPassword();

        final Service serviceRequest = new Service(this);
        serviceRequest.fetchUserDataInBackground(user, new GetUserCallback() {

            @Override
            public void done(User returnedUser, boolean success, boolean findUser) {
                if (!success) {
                    serviceRequest.showFailConnectionServerMessage(AuthenticationActivity.this);
                } else {
                    if (!findUser) {
                        showErrorMessage();
                    } else {
                        if (insertedPassword.equals(returnedUser.getPassword())) {
                            logUserIn(returnedUser);
                        } else {
                            showWrongPasswordMessage();
                        }
                    }
                }
            }
        });


    }

    private void logUserIn(User returnedUser) {
        returnedUser.setIsLogged(1);
        returnedUser.save(this);
        startActivity(new Intent(this, TabLayoutActivity.class));
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AuthenticationActivity.this);
        dialogBuilder.setMessage("El usuario no se encuentra registrado");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void showEmptyFieldMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AuthenticationActivity.this);
        dialogBuilder.setMessage("No puede haber campos vacios");
        dialogBuilder.setPositiveButton("Reintentar",null);
        dialogBuilder.show();
    }

    private void showWrongPasswordMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AuthenticationActivity.this);
        dialogBuilder.setMessage("Contraseña incorrecta");
        dialogBuilder.setPositiveButton("Reintentar",null);
        dialogBuilder.show();
    }

    private void showIpPopUp() {
        LayoutInflater layoutInflater = LayoutInflater.from(AuthenticationActivity.this);
        View promptView = layoutInflater.inflate(R.layout.ip_address_pop_up, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AuthenticationActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.etIp);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}


