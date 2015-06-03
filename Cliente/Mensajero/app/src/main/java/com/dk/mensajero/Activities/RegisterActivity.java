package com.dk.mensajero.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dk.mensajero.Entities.User;
import com.dk.mensajero.Interfaces.GetUserCallback;
import com.dk.mensajero.R;
import com.dk.mensajero.Service.Service;

public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {

    Button bRegister;
    EditText etName, etPassword, etPasswordConfirmation;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.getUserPhone();

        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirmation = (EditText) findViewById(R.id.etPasswordConfirmation);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bRegister:
                String name = etName.getText().toString();
                String password = etPassword.getText().toString();
                String passwordConfirmation = etPasswordConfirmation.getText().toString();
                if (name.equals("") || password.equals("")){
                    showEmptyFieldMessage();
                } else {
                    if (!password.equals(passwordConfirmation)) {
                        showWrongPasswordMessage();
                    } else {
                        User user = new User(phoneNumber, name, password, "0");
                        registerUser(user);
                    }
                }
                break;
        }
    }

    private void registerUser(User user) {
        final Service serviceRequest = new Service(this);
        serviceRequest.storeNewUserInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser, boolean success) {
                if (!success){
                    serviceRequest.showFailConnectionServerMessage(RegisterActivity.this);
                } else {
                    startActivity(new Intent(RegisterActivity.this, AuthenticationActivity.class));
                }
            }
        });
    }

    private void getUserPhone(){

        Intent intentExtras = getIntent();
        Bundle phoneBundle;
        if (intentExtras.hasExtra("phone")){
            phoneBundle = intentExtras.getExtras();
            this.phoneNumber = phoneBundle.getString("phone");
        }
    }
    private void showWrongPasswordMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
        dialogBuilder.setMessage("La contraseña no coincide");
        dialogBuilder.setPositiveButton("Reintentar",null);
        dialogBuilder.show();
    }

    private void showEmptyFieldMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
        dialogBuilder.setMessage("No puede haber campos vacios");
        dialogBuilder.setPositiveButton("Reintentar",null);
        dialogBuilder.show();
    }

}
