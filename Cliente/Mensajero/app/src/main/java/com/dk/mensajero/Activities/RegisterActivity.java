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

/**
 * Pantalla que se encarga de registrar un usuario nuevo.
 */
public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {
    /**
     * Boton para finalizar el registro.
     */
    Button bRegister;
    /**
     * Campos editables donde se ingresan nombre, contrasena y confirmacion de contrasena.
     */
    EditText etName, etPassword, etPasswordConfirmation;
    /**
     * numero de telefono del usuario.
     */
    String phoneNumber;

    /**
     * Inicializa todos los atributos de la pantalla.
     * @param savedInstanceState
     */
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

    /**
     * Se encarga de detectar cuando el usuario presiona el boton de finalizaciom de registro.
     * Ademas verifica que las contrasenas ingresadas coincidan.
     * @param v vista que permite identificar el boton presionado por el usuario.
     */
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

    /**
     * Almacena los datos del usuario ingresados en el servidor.
     *
     * @param user entidad usuario con los datos del registro.
     */
    private void registerUser(User user) {
        final Service serviceRequest = new Service(this);
        serviceRequest.storeNewUserInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser, boolean successfulConnection, boolean existingUser) {
                if (!successfulConnection){
                    serviceRequest.showFailConnectionServerMessage(RegisterActivity.this);
                } else {
                    if (existingUser) {
                        showExistingUserMessage();
                    } else {
                        startActivity(new Intent(RegisterActivity.this, AuthenticationActivity.class));
                    }
                }
            }
        });
    }

    /**
     * Obtiene el telefono del usuario ingresado desde otroa pantalla.
     */
    private void getUserPhone(){

        Intent intentExtras = getIntent();
        Bundle phoneBundle;
        if (intentExtras.hasExtra("phone")){
            phoneBundle = intentExtras.getExtras();
            this.phoneNumber = phoneBundle.getString("phone");
        }
    }

    /**
     * Muestra un mensaje de error si las contrasenas no coinciden.
     */
    private void showWrongPasswordMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
        dialogBuilder.setMessage("La contraseña no coincide");
        dialogBuilder.setPositiveButton("Reintentar",null);
        dialogBuilder.show();
    }

    /**
     * Muestra un mensaje de error si se dejan campos libres.
     */
    private void showEmptyFieldMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
        dialogBuilder.setMessage("No puede haber campos vacios");
        dialogBuilder.setPositiveButton("Reintentar",null);
        dialogBuilder.show();
    }

    /**
     * Muestra un mensaje de erro en caso de que se intente registrar un usuario existente.
     */
    private void showExistingUserMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
        dialogBuilder.setMessage("El usuario ya se encuentra registrado");
        dialogBuilder.setPositiveButton("Reintentar",null);
        dialogBuilder.show();
    }

}
