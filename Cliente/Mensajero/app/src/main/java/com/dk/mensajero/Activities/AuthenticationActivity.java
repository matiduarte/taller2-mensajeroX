package com.dk.mensajero.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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


/**
 * Pantalla para loguearse en el sistema, es la primera en aparecer cuando se inicia la aplicacion.
 * Posee dos campos: nombre y contrasena.
 * Posee ademas un boton para registrar un usuario nuevo.
 */
public class AuthenticationActivity extends ActionBarActivity implements View.OnClickListener {

    /**
     * Boton que permite iniciar sesion.
     */
    Button bLogin;
    /**
     * Campos que contienen el nombre y la contrasena ingresados.
     */
    EditText etPhoneNumber, etPassword;
    /**
     * Boton para registrar un usuario nuevo.
     */
    TextView registerLink;

    /**
     * Se encarga de inicializar todos los atributos de la pantalla.
     * @param savedInstanceState
     */
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

    /**
     * Crea un boton flotante para ingresar ip y puerto
     */
    public void ipPortFloatingButton(){
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_ipport);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthenticationActivity.this, IpPortActivity.class));
            }
        });


    }

    /**
     * Permite detectar que boton es presionado por el usuario.
     * @param v vista que identifica a los botones de la pantalla.
     */
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

    /**
     * Se encarga de verificar si los datos del usuario ingresados son correctos.
     * Para ello verifica en el servidor si el usuario existe.
     * @param user objeto usuario con sus atributos.
     */
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
        }, true, true);


    }

    /**
     * Almacena en la base de datos la informacion del usuario logueado.
     * @param returnedUser entidad usuario que proviene del servidor.
     */
    private void logUserIn(User returnedUser) {
        returnedUser.setIsLogged(1);
        returnedUser.save(this);
        startActivity(new Intent(this, TabLayoutActivity.class));
    }

    /**
     * Muestra un mensaje cuando el usuario ingresado no se encuentra registrado en el sistema.
     */
    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AuthenticationActivity.this);
        dialogBuilder.setMessage("El usuario no se encuentra registrado");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    /**
     * Muestra un mensaje cuando se deja algun campo vacio.
     */
    private void showEmptyFieldMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AuthenticationActivity.this);
        dialogBuilder.setMessage("No puede haber campos vacios");
        dialogBuilder.setPositiveButton("Reintentar",null);
        dialogBuilder.show();
    }

    /**
     * Muestra un mensaje cuando la contrasena ingresada es incorrecta.
     */
    private void showWrongPasswordMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AuthenticationActivity.this);
        dialogBuilder.setMessage("Contraseña incorrecta");
        dialogBuilder.setPositiveButton("Reintentar",null);
        dialogBuilder.show();
    }

}


