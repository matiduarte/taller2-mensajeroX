package com.dk.mensajero.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dk.mensajero.Entities.User;
import com.dk.mensajero.R;

public class AuthenticationActivity extends ActionBarActivity implements View.OnClickListener {

    Button bLogin;
    EditText etPhoneNumber, etPassword;
    TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        registerLink = (TextView) findViewById(R.id.tvRegisterLink);
        bLogin.setOnClickListener(this);
        registerLink.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogin:

                String phone = etPhoneNumber.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User(phone, password);

                authenticate(user);

                break;
            case R.id.tvRegisterLink:

                startActivity(new Intent(this, UserLoggerActivity.class));

                break;
        }
    }

    private void authenticate(User user){
        //TODO: BUSCAR LA INFO DEl SERVIDOR
        //showErrorMessage();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AuthenticationActivity.this);
        dialogBuilder.setMessage("Datos incorrectos");
        dialogBuilder.setPositiveButton("Ok",null);
        dialogBuilder.show();
    }
}


